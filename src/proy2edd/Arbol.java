/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JTextArea;
import org.json.simple.JSONArray;
import proy2edd.Nodo;
import proy2edd.HashTable;
import proy2edd.SimpleSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author chela
 */
/**
 * Clase que representa un &aacute;rbol geneal&oacute;gico, permitiendo cargar datos desde un archivo JSON,
 * realizar b&uacute;squedas por nombre o mote, y gestionar los nodos jer&aacute;rquicos del &aacute;rbol.
 */
public class Arbol {

    private Nodo raiz;
    private JTextArea areaInformacion;
    private HashTable tabla;
    private HashTable tablaMotes;

    /**
     * Constructor que inicializa el &aacute;rbol geneal&oacute;gico.
     *
     * @param areaInformacion &Aacute;rea de texto donde se mostrar&aacute;n mensajes informativos.
     */
    public Arbol(JTextArea areaInformacion) {
        this.areaInformacion = areaInformacion;
        System.out.println("Is areaInformacion null? " + (this.areaInformacion == null));
        this.raiz = null;
        this.tabla = new HashTable(100); //capacidad inicial de la hashtable
        this.tablaMotes = new HashTable(100); //capacidad inicial de la hashtable

    }

    /**
     * Devuelve la ra&iacute;z del &aacute;rbol.
     *
     * @return Nodo ra&iacute;z del &aacute;rbol.
     */
    public Nodo getRaiz() {
        return raiz;
    }
    
 /**
     * Carga datos desde un archivo JSON y construye el &aacute;rbol geneal&oacute;gico.
     *
     * @param json Cadena JSON que contiene los datos a cargar.
     */
    public void cargarDesdeJSON(String json) {
    try {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);

        SimpleSet keys = new SimpleSet();
        for (Object key : jsonObject.keySet()) {
            keys.add((String) key);
        }

        String[] keyArray = keys.toArray();
        if (keyArray.length > 0) {
            String firstKey = keyArray[0];

            if (firstKey.contains("House")) {
                Object houseData = jsonObject.get(firstKey);

                if (houseData instanceof org.json.simple.JSONArray) {
                    org.json.simple.JSONArray houseArray = (org.json.simple.JSONArray) houseData;

                    areaInformacion.append("Cargando información para la casa: " + firstKey + "\n");

                    for (Object obj : houseArray) {
                        if (obj instanceof JSONObject) {
                            JSONObject characterEntry = (JSONObject) obj;

                            for (Object characterNameObj : characterEntry.keySet()) {
                                String characterName = characterNameObj.toString();

                                Object characterDataObj = characterEntry.get(characterName);
                                if (characterDataObj instanceof org.json.simple.JSONArray) {
                                    org.json.simple.JSONArray characterData = (org.json.simple.JSONArray) characterDataObj;

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
    } catch (ParseException e) {
        areaInformacion.append("Error al parsear el JSON: " + e.getMessage() + "\n");
    } catch (ClassCastException e) {
        areaInformacion.append("Error de tipo en el JSON: " + e.getMessage() + "\n");
    }
}

    

//        try {
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) parser.parse(json);
//
//            Set<String> keys = jsonObject.keySet();
//
//            Iterator<String> iterator = keys.iterator();
//            if (iterator.hasNext()) {
//                String firstKey = iterator.next();
//
//                if (firstKey.contains("House")) {
//                    Object houseData = jsonObject.get(firstKey);
//
//                    if (houseData instanceof org.json.simple.JSONArray) {
//                        org.json.simple.JSONArray houseArray = (org.json.simple.JSONArray) houseData;
//
//                        areaInformacion.append("Cargando información para la casa: " + firstKey + "\n");
//
//                        for (Object obj : houseArray) {
//                            if (obj instanceof JSONObject) {
//                                JSONObject characterEntry = (JSONObject) obj;
//
//                                for (Object characterNameObj : characterEntry.keySet()) {
//                                    String characterName = characterNameObj.toString();
//
//                                    Object characterDataObj = characterEntry.get(characterName);
//                                    if (characterDataObj instanceof org.json.simple.JSONArray) {
//                                        org.json.simple.JSONArray characterData = (org.json.simple.JSONArray) characterDataObj;
//
//                                        processCharacter(characterName, characterData);
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        areaInformacion.append("Formato desconocido para la casa en el JSON.\n");
//                    }
//                }
//            }
//
//        } catch (org.json.simple.parser.ParseException e) {
//            areaInformacion.append("Error al parsear el JSON: " + e.getMessage() + "\n");
//        } catch (ClassCastException e) {
//            areaInformacion.append("Error de tipo en el JSON: " + e.getMessage() + "\n");
//        }
//    }

    /**
     * Procesa los datos de un personaje y los agrega al &aacute;rbol.
     *
     * @param characterName Nombre del personaje.
     * @param characterData Datos asociados al personaje en formato JSON.
     */
    private void processCharacter(String characterName, JSONArray characterData) {
        Nodo nodo = new Nodo(characterName, null, null, null, null);

        for (Object obj : characterData) {
            if (obj instanceof JSONObject) {
                JSONObject characterAttributes = (JSONObject) obj;

                for (Object keyObj : characterAttributes.keySet()) {
                    String key = (String) keyObj;
                    Object value = characterAttributes.get(key);

                    if (key.equals("Known throughout as")) {
                        nodo.setMote((String) value);
                    } else if (key.equals("Held title")) {
                        nodo.setTitulo((String) value);
                    } else if (key.equals("Born to")) {
                        nodo.setPadreNombre((String) value);
                    } else if (key.equals("Father to")) {
                         if (value instanceof org.json.simple.JSONArray) {
                             org.json.simple.JSONArray childrenArray = (org.json.simple.JSONArray) value;
                             for (Object child : childrenArray) {
                                 if (child instanceof String) {
                                     
                                     nodo.agregarHijo(new Nodo(((String) child).trim(), null, null, null, nodo.getNombreCompleto()));
                                 }
                             }
                         } else {
                            
                             if (value instanceof String) {
                                 String[] children = ((String) value).replace("[", "").replace("]", "").split(",");
                                 for (String child : children) {
                                     nodo.agregarHijo(new Nodo(child.trim(), null, null, null, nodo.getNombreCompleto()));
            }
        }
    }
} else if (key.equals ("Fate")) {
    nodo.setFate((String) value);
                        
}

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
              tablaMotes.agregarMote(nodo.getMote(), nodo);

        }
    }

    /**
     * Agrega un nodo al &aacute;rbol asoci&aacute;ndolo con su padre si existe.
     *
     * @param nodo Nodo a agregar.
     */
    private void agregarNodoAlArbol(Nodo nodo) {
        Nodo padre = buscarNodoPorNombre(nodo.getPadreNombre());
        if (padre != null) {
            padre.agregarHijo(nodo);
            nodo.setPadre(padre);
        }
    }

     /**
     * Busca un nodo por su nombre completo.
     *
     * @param nombre Nombre completo del nodo a buscar.
     * @return Nodo encontrado o {@code null} si no existe.
     */
    public Nodo buscarNodoPorNombre(String nombre) {
        return tabla.buscar(nombre);
    }

    /**
     * Busca un nodo por su mote.
     *
     * @param mote Mote del nodo a buscar.
     * @return Nodo encontrado o {@code null} si no existe.
     */
    public Nodo buscarNodoPorMote(String mote) {
        return tablaMotes.buscarMote(mote);
    }

    /**
     * Devuelve una lista de todos los nodos del &aacute;rbol.
     *
     * @return Lista de todos los nodos en el &aacute;rbol.
     */
    public MiLista getTodosLosNodos() {
        MiLista nodos = new MiLista();
        collectAllNodes(raiz, nodos);
        return nodos;
    }

    /**
     * Recolecta todos los nodos a partir de un nodo inicial y los agrega a una lista.
     *
     * @param nodo  Nodo inicial.
     * @param nodos Lista donde se agregar&aacute;n los nodos recolectados.
     * @return Lista actualizada con los nodos recolectados.
     */
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
