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

/**
 *
 * @author chela
 */
public class Arbol {
    private Nodo raiz;
   // private Graph grafo;
    private JTextArea areaInformacion;
    private HashTable tabla;
    
    public Arbol() {
      // this.grafo = new SingleGraph("Árbol Genealógico");
      //  grafo.setAttribute("ui.stylesheet", "node { fill-color: grey; size: 25px; }"); //copiado del proy anterior
        this.areaInformacion = areaInformacion;
        this.raiz = null;
        this.tabla = new HashTable(100); //capacidad inicial de la hashtable
    }
    
    public Nodo getRaiz() {
        return raiz;
    }
    /**
    public Graph getGrafo() {
        return grafo;
    } */
    
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
        String nombreCasa = obtenerValorDe(json, "\"House ", "\":[");
        areaInformacion.append("Cargando árbol para la casa: " + nombreCasa + "\n");
        String[] registros = json.split("\\}, \\{");
        for (String registro : registros) {
            Nodo nodo = procesarRegistro(registro);
            if (raiz == null) {
                raiz = nodo;
            } else {
                agregarNodoAlArbol(nodo);
            }
        }
      //  construirGrafo();
    }
    
    private Nodo procesarRegistro(String registro) {
        String nombre = obtenerValorDe(registro, "\"", "\":[");
        Nodo nodo = new Nodo(nombre, obtenerValorDe(registro, "\"Known throughout as\":\"", "\""), obtenerValorDe(registro, "\"Held title\":\"", "\""), obtenerValorDe(registro, "\"Born to\":\"", "\""));
        //nodo.setMote(obtenerValorDe(registro, "\"Known throughout as\":\"", "\""));
        //nodo.setTitulo(obtenerValorDe(registro, "\"Held title\":\"", "\""));
        //nodo.setPadre(obtenerValorDe(registro, "\"Born to\":\"", "\""));
        String hijosStr = obtenerValorDe(registro, "\"Father to\":[", "]");
        if (hijosStr != null) {
            String[] hijos = hijosStr.replaceAll("\"", "").split(",");
            for (String hijo : hijos) {
                nodo.agregarHijo(new Nodo(hijo.trim(), null, null, nombre));  
            } 
        }
        areaInformacion.append("Nodo cargado: " + nombre + "\n");
        return nodo;
    }
    
    private String obtenerValorDe(String texto, String inicio, String fin) {
        int indiceInicio = texto.indexOf(inicio);
        if (indiceInicio == -1) {
            return null;
        }
        indiceInicio += inicio.length();
        int indiceFin = texto.indexOf(fin, indiceInicio);
        if (indiceFin == -1) {
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
    /**
    public void construirGrafo() {
        grafo.clear();
        agregarNodoAlGrafo(raiz);
    }
    
    private void agregarNodoAlGrafo(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        grafo.addNode(nodo.getNombreCompleto()).setAttribute("ui.label", nodo.getNombreCompleto());
        if (nodo.getPadre() != null) {
            String idPadre = nodo.getPadre().getNombreCompleto();
            grafo.addEdge(idPadre + "-" + nodo.getNombreCompleto(), idPadre, nodo.getNombreCompleto(), true);
        }
        for (Nodo hijo : nodo.getHijos()) {
            agregarNodoAlGrafo(hijo);
        }
    } */
    
    public Nodo buscarNodoPorNombre(String nombre) {
        return tabla.buscar(nombre);
    }
    /**
    private Nodo buscarNodoPorNombreRecursivo(Nodo nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getNombreCompleto().equals(nombre)) {
            return nodo;
        }
        for (Nodo hijo : nodo.getHijos()) {
            Nodo encontrado = buscarNodoPorNombreRecursivo(hijo, nombre);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }
    
    //grafo.display(true);
    */
    
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
