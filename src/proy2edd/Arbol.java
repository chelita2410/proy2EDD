/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextArea;
import proy2edd.Nodo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
/*
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
    } */
    
    public void cargarDesdeJSON(String json) {
        try { 
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            
            JSONObject house = (JSONObject) jsonObject.get("{\"House");
            if (house == null) {
                areaInformacion.append("No se encontró la casa en el JSON.\n");
                return;
            }
           // areaInformacion.append("Cargando árbol para la casa: " + house + "\n");
            
            for (Object key : house.keySet()) {
                JSONObject characterData = (JSONObject) house.get(key);
                processCharacter(key.toString(), characterData);
            }
        } catch (ParseException e) {
            areaInformacion.append("Error al parsear el JSON: " + e.getMessage() + "\n");
        }
       
}
        
        private void processCharacter(String characterName, JSONObject characterData) {
           // areaInformacion.append("Cargando personaje: " + characterName + "\n");
            Nodo nodo = new Nodo(characterName, null, null, null);
            for (Object keyObj : characterData.keySet()) {
                String key = (String) keyObj;
                Object value = characterData.get(key);
                if (key.equals("{\"Known throughout as\":")) {
                    nodo.setMote((String) value);
                } else if (key.equals("{\"Held title\":")) {
                    nodo.setTitulo((String) value);
                } else if (key.equals("{\"Born to\":")) {
                    nodo.setPadreNombre((String) value);
                } else if (key.equals("{\"Father to\":")) {
                    String[] children = ((String) value).replace("[", "").replace("]", "").split(",");
                    for (String child : children) {
                        nodo.agregarHijo(new Nodo(child.trim(), null, null, nodo.getNombreCompleto()));
                    }
                }
}
                if (raiz == null) {
                    raiz = nodo;
                } else {
                    agregarNodoAlArbol(nodo);
            }
            tabla.agregar(nodo.getNombreCompleto(), nodo);
            if (nodo.getMote() != null) {
                tabla.agregar(nodo.getMote(), nodo);
            }

            //areaInformacion.append("Nodo cargado: " + characterName + "\n");
  
    }
      
        
    private void agregarNodoAlArbol(Nodo nodo) {
        Nodo padre = buscarNodoPorNombre(nodo.getPadreNombre());
        if (padre != null) {
            padre.agregarHijo(nodo);
            nodo.setPadre(padre);
        }
    }
       /* if (raiz == null) {
            raiz = nodo;
        } else {
            Nodo padre = buscarNodoPorNombre(nodo.getPadreNombre());
            if (padre != null) {
                padre.agregarHijo(nodo);
                nodo.setPadre(padre);
            }
        }
      
    } */

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
