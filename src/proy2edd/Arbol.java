/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;
import java.io.BufferedReader;
import java.io.FileReader;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import java.io.IOException;
import javax.swing.JTextArea;
import proy2edd.Nodo;

/**
 *
 * @author chela
 */
public class Arbol {
    private Nodo raiz;
    private JTextArea areaInformacion;
    private HashTable tabla;
    
    public Arbol(JTextArea areaInformacion) {
        this.areaInformacion = areaInformacion;
        System.out.println("Is areaInformacion null? " + (this.areaInformacion == null));
        this.raiz = null;
        this.tabla = new HashTable(100); //capacidad inicial de la hashtable
    }
    
    public Nodo getRaiz() {
        return raiz;
    }

    public void cargarJSON(String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
            }
            areaInformacion.append("Archivo cargado correctamente: " + nombreArchivo + "\n");
        } catch (IOException e) {
            areaInformacion.append("Error al cargar el archivo: " + e.getMessage() + "\n");
        }
        cargarDesdeJSON(contenido.toString());
    }
    
    public void cargarDesdeJSON(String json) {
        String houseNameKey = "House ";
        int houseIndex = json.indexOf(houseNameKey);
        if (houseIndex == -1) {
            areaInformacion.append("No se encontró el nombre de la casa en el JSON.\n");
            return;
        }
        int colonIndex = json.indexOf(":", houseIndex);
        String houseName = json.substring(houseIndex + houseNameKey.length(), colonIndex);
        areaInformacion.append("Cargando árbol para la casa: " + houseName + "\n");
        
        String houseData = json.substring(colonIndex + 1).trim();
        houseData = houseData.substring(1, houseData.length() - 1);
        
        String[] characterEntries = houseData.split("\\},\\{");
        for (String entry : characterEntries) {
            entry = entry.replace("{", "").replace("}", "").trim();
            processCharacter(entry);
        }
}
        
        private void processCharacter(String entry) {
        int nameStart = entry.indexOf("\"");
        int nameEnd = entry.indexOf("\":");
        if (nameStart == -1 || nameEnd == -1) {
            areaInformacion.append("No se encontró el nombre del personaje en el registro.\n");
            return;
        }
        String characterName = entry.substring(nameStart + 1, nameEnd).trim();
        Nodo nodo = new Nodo(characterName, null, null, null);
        
        String[] attributes = entry.substring(nameEnd + 2).split(",");
        for (String attribute : attributes) {
            processAttribute(nodo, attribute.trim());
        }
        if (raiz == null) {
            raiz = nodo;
        } else {
            agregarNodoAlArbol(nodo);
            
        }
        areaInformacion.append("Nodo cargado: " + characterName + "\n");
        /** System.out.println("JSON received:\n" + json);
        String nombreCasa = obtenerValorDe(json, "\"House ", "\":[");
        if (nombreCasa == null) {
            areaInformacion.append("No se pudo cargar el nombre de la casa.\n");
        }
        areaInformacion.append("Cargando árbol para la casa: " + nombreCasa + "\n");
        String[] registros = json.split("\\}, \\{");
        for (String registro : registros) {
            Nodo nodo = procesarRegistro(registro);
            if (nodo == null) {
                continue;
            }
            if (raiz == null) {
                raiz = nodo;
            } else {
                agregarNodoAlArbol(nodo);
            }
        } */
    }
        
        private void processAttribute(Nodo nodo, String attribute) {
            if (attribute.contains(":")) {
                String key = attribute.substring(0, attribute.indexOf(":")).replace("\"", "").trim();
                String value = attribute.substring(attribute.indexOf(":") + 1).replace("\"", "").trim();
                
                switch (key) {
                    case "Known throughout as":
                        nodo.setMote(value);
                        break;
                    case "Held title":
                        nodo.setTitulo(value);
                        break;
                    case "Born to":
                        nodo.setPadreNombre(value);
                        break;
                    case "Father to":
                        value = value.replace("[", "").replace("]", "").trim();
                        if (!value.isEmpty()) {
                            String[] children = value.split(",");
                            for (String child : children) {
                                nodo.agregarHijo(new Nodo(child.trim(), null, null, nodo.getNombreCompleto()));
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    
    private Nodo procesarRegistro(String registro) {
        String nombre = obtenerValorDe(registro, "\"Known throughtout as\":\"", "\"");
        if (nombre == null) {
            areaInformacion.append("Nodo sin nombre encontrado.\n");
            return null;
        }
        String titulo = obtenerValorDe(registro, "\"Held title\":\"", "\"");
        String padreNombre = obtenerValorDe(registro, "\"Born to\":\"", "\"");
        Nodo nodo = new Nodo(nombre, null, titulo, padreNombre);
        
        String hijosStr = obtenerValorDe(registro, "\"Father to\":\"", "\"");
        if (hijosStr != null) {
            String[] hijos = hijosStr.replaceAll("\"", "").split(",");
            for (String hijo : hijos) {
                nodo.agregarHijo(new Nodo(hijo.trim(), null, null, nombre));    
            }   
        }
        areaInformacion.append("Nodo cargado: " + nombre + "\n");
        return nodo;
    }
       /** String nombre = obtenerValorDe(registro, "\"", "\":[");
        Nodo nodo = new Nodo(nombre, obtenerValorDe(registro, "\"Known throughout as\":\"", "\""), obtenerValorDe(registro, "\"Held title\":\"", "\""), obtenerValorDe(registro, "\"Born to\":\"", "\""));
        String hijosStr = obtenerValorDe(registro, "\"Father to\":[", "]");
        if (hijosStr != null) {
            String[] hijos = hijosStr.replaceAll("\"", "").split(",");
            for (String hijo : hijos) {
                nodo.agregarHijo(new Nodo(hijo.trim(), null, null, nombre));  
            } 
        }
        areaInformacion.append("Nodo cargado: " + nombre + "\n");
        return nodo;
    } */
    
    private String obtenerValorDe(String texto, String inicio, String fin) {
        int indiceInicio = texto.indexOf(inicio);
        if (indiceInicio == -1) {
            System.out.println("Key not found: " + inicio);
            return null;
        }
        indiceInicio += inicio.length();
        int indiceFin = texto.indexOf(fin, indiceInicio);
        if (indiceFin == -1) {
            System.out.println("Ending delimiter not found for key: " + inicio);
            return null;
        }
        return texto.substring(indiceInicio, indiceFin);
    }
    
    private void agregarNodoAlArbol(Nodo nodo) {
        if (raiz == null) {
            raiz = nodo;
        } else {
            Nodo padre = buscarNodoPorNombre(nodo.getPadreNombre());
            if (padre != null) {
                padre.agregarHijo(nodo);
                nodo.setPadre(padre);
            }
        }
        tabla.agregar(nodo.getNombreCompleto(), nodo); //agrefa a la hashtable
        if (nodo.getMote() != null) {
            tabla.agregar(nodo.getMote(), nodo); //agrega por mote
        }
    }

    public Nodo buscarNodoPorNombre(String nombre) {
        return tabla.buscar(nombre);
    }
 
    public MiLista getTodosLosNodos() {
        MiLista nodos = new MiLista();
        collectAllNodes(raiz, nodos);
        return nodos;
    }
    
    private void collectAllNodes(Nodo nodo, MiLista nodos) {
        if (nodo != null) {
            nodos.add(nodo);
            for (Nodo hijo : nodo.getHijos()) {
                collectAllNodes(hijo, nodos);
            }
        }
    }
}
