/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JTextArea;
import org.json.simple.JSONArray;
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
    private HashTable tablaMotes;

    public Arbol(JTextArea areaInformacion) {
        this.areaInformacion = areaInformacion;
        System.out.println("Is areaInformacion null? " + (this.areaInformacion == null));
        this.raiz = null;
        this.tabla = new HashTable(100); //capacidad inicial de la hashtable
        this.tablaMotes = new HashTable(100); //capacidad inicial de la hashtable

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
//    public void cargarDesdeJSON(String json) {
//        try {
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) parser.parse(json);
//
//            // Itera por las claves del objeto JSON
//            Set<String> keys = jsonObject.keySet();
//
//            // Usar un iterador para obtener la primera clave
//            Iterator<String> iterator = keys.iterator();
//            if (iterator.hasNext()) {
//                String firstKey = iterator.next();
//
//                // Verifica si la clave contiene la palabra "House"
//                if (firstKey.contains("House")) {
//                    JSONObject house = (JSONObject) jsonObject.get(firstKey);
//                    if (house == null) {
//                        areaInformacion.append("No se encontró la casa en el JSON.\n");
//                        return;
//                    }
//                    // areaInformacion.append("Cargando árbol para la casa: " + house + "\n");
//
//                    for (Object key : house.keySet()) {
//                        JSONObject characterData = (JSONObject) house.get(key);
//                        processCharacter(key.toString(), characterData);
//                    }
//
//                }
//            }
//
//        } catch (ParseException e) {
//            areaInformacion.append("Error al parsear el JSON: " + e.getMessage() + "\n");
//        }
//
////    }
    public void cargarDesdeJSON(String json) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);

            // Itera por las claves del objeto JSON
            Set<String> keys = jsonObject.keySet();

            // Usar un iterador para obtener la primera clave
            Iterator<String> iterator = keys.iterator();
            if (iterator.hasNext()) {
                String firstKey = iterator.next();

                // Verifica si la clave contiene la palabra "House"
                if (firstKey.contains("House")) {
                    Object houseData = jsonObject.get(firstKey);

                    // Verifica si es un JSONArray
                    if (houseData instanceof org.json.simple.JSONArray) {
                        org.json.simple.JSONArray houseArray = (org.json.simple.JSONArray) houseData;

                        areaInformacion.append("Cargando información para la casa: " + firstKey + "\n");

                        // Itera sobre el arreglo de personajes
                        for (Object obj : houseArray) {
                            if (obj instanceof JSONObject) {
                                JSONObject characterEntry = (JSONObject) obj;

                                // Obtén el nombre del personaje (clave del objeto)
                                for (Object characterNameObj : characterEntry.keySet()) {
                                    String characterName = characterNameObj.toString();

                                    // Obtén los datos del personaje
                                    Object characterDataObj = characterEntry.get(characterName);
                                    if (characterDataObj instanceof org.json.simple.JSONArray) {
                                        org.json.simple.JSONArray characterData = (org.json.simple.JSONArray) characterDataObj;

                                        // Llama a processCharacter con el nombre y datos del personaje
                                        processCharacter(characterName, characterData);
                                    }
                                }
                            }
                        }
                    } else {
                        areaInformacion.append("Formato desconocido para la casa en el JSON.\n");
                    }
                }
            }

        } catch (org.json.simple.parser.ParseException e) {
            areaInformacion.append("Error al parsear el JSON: " + e.getMessage() + "\n");
        } catch (ClassCastException e) {
            areaInformacion.append("Error de tipo en el JSON: " + e.getMessage() + "\n");
        }
    }

//    private void processCharacter(String characterName, JSONArray characterData) {
//        // areaInformacion.append("Cargando personaje: " + characterName + "\n");
//        Nodo nodo = new Nodo(characterName, null, null, null);
//        for (Object keyObj : characterData.keySet()) {
//            String key = (String) keyObj;
//            Object value = characterData.get(key);
//            if (key.equals("{\"Known throughout as\":")) {
//                nodo.setMote((String) value);
//            } else if (key.equals("{\"Held title\":")) {
//                nodo.setTitulo((String) value);
//            } else if (key.equals("{\"Born to\":")) {
//                nodo.setPadreNombre((String) value);
//            } else if (key.equals("{\"Father to\":")) {
//                String[] children = ((String) value).replace("[", "").replace("]", "").split(",");
//                for (String child : children) {
//                    nodo.agregarHijo(new Nodo(child.trim(), null, null, nodo.getNombreCompleto()));
//                }
//            }
//        }
//        if (raiz == null) {
//            raiz = nodo;
//        } else {
//            agregarNodoAlArbol(nodo);
//        }
//        tabla.agregar(nodo.getNombreCompleto(), nodo);
//        if (nodo.getMote() != null) {
//            tabla.agregar(nodo.getMote(), nodo);
//        }
//
//        //areaInformacion.append("Nodo cargado: " + characterName + "\n");
//    }
    private void processCharacter(String characterName, JSONArray characterData) {
        // areaInformacion.append("Cargando personaje: " + characterName + "\n");
        Nodo nodo = new Nodo(characterName, null, null, null);

        // Itera a través del JSONArray de datos del personaje
        for (Object obj : characterData) {
            if (obj instanceof JSONObject) {
                JSONObject characterAttributes = (JSONObject) obj;

                // Itera sobre las claves del JSONObject
                for (Object keyObj : characterAttributes.keySet()) {
                    String key = (String) keyObj;
                    Object value = characterAttributes.get(key);

                    // Verifica los valores de las claves y asigna a las propiedades del nodo
                    if (key.equals("Known throughout as")) {
                        nodo.setMote((String) value);
                    } else if (key.equals("Held title")) {
                        nodo.setTitulo((String) value);
                    } else if (key.equals("Born to")) {
                        nodo.setPadreNombre((String) value);
                    } else if (key.equals("Father to")) {
                        // Manejo especial para "Father to", ya que parece ser un arreglo
                         if (value instanceof org.json.simple.JSONArray) {
                             org.json.simple.JSONArray childrenArray = (org.json.simple.JSONArray) value;
                             for (Object child : childrenArray) {
                                 if (child instanceof String) {
                                     // Agrega cada hijo como un Nodo
                                     nodo.agregarHijo(new Nodo(((String) child).trim(), null, null, nodo.getNombreCompleto()));
                                 }
                             }
                         } else {
                             // En caso de que no sea un JSONArray, manejarlo como un String (caso excepcional)
                             if (value instanceof String) {
                                 String[] children = ((String) value).replace("[", "").replace("]", "").split(",");
                                 for (String child : children) {
                                     nodo.agregarHijo(new Nodo(child.trim(), null, null, nodo.getNombreCompleto()));
            }
        }
    }
}
//                        if (value instanceof String) {
//                            String[] children = ((String) value).replace("[", "").replace("]", "").split(",");
//                            for (String child : children) {
//                                nodo.agregarHijo(new Nodo(child.trim(), null, null, nodo.getNombreCompleto()));
//                            }
//                        }
//                    }
                }
                //
            }
        }

        // Si es el primer nodo, lo asigna como la raíz
        if (raiz == null) {
            raiz = nodo;
        } else {
            agregarNodoAlArbol(nodo);
        }

        // Agrega el nodo al sistema de tablas
        tabla.agregar(nodo.getNombreCompleto(), nodo);
//        tablaMotes.agregarMote(nodo.getMote(), nodo);
        if (nodo.getMote() != null) {
//            tabla.agregar(nodo.getMote(), nodo);
              tablaMotes.agregarMote(nodo.getMote(), nodo);

        }

        // areaInformacion.append("Nodo cargado: " + characterName + "\n");
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

    public Nodo buscarNodoPorMote(String mote) {
        return tablaMotes.buscarMote(mote);
    }

    public MiLista getTodosLosNodos() {
        MiLista nodos = new MiLista();
        collectAllNodes(raiz, nodos);
        return nodos;
    }

    private MiLista collectAllNodes(Nodo nodo, MiLista nodos) {
        if (nodo != null) {
            nodos.add(nodo);
            for (Nodo hijo : nodo.getHijos()) {
                nodos =collectAllNodes(hijo, nodos);
            }
        }
        return nodos;
    }
}
