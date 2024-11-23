/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

/**
 *
 * @author chela
 */
/**
 * Clase que implementa una tabla hash para almacenar objetos de tipo {@link Nodo}.
 * Utiliza direccionamiento abierto con sondeo lineal para manejar colisiones.
 */
public class HashTable {
    private Nodo [] tabla;
    private int capacidad;
    private int tamaño;
    
    /**
     * Constructor que inicializa la tabla hash con una capacidad especificada.
     *
     * @param capacidad La capacidad inicial de la tabla hash.
     */
    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new Nodo[capacidad];
        this.tamaño = 0;
    }
    
    /**
     * Calcula el &iacute;ndice hash para una clave dada utilizando una funci&oacute;n de dispersi&oacute;n basada en la suma ponderada.
     *
     * @param clave La clave para la que se calcular&aacute; el &iacute;ndice hash.
     * @return El &iacute;ndice hash calculado.
     */
    private int hash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31 + clave.charAt(i)) % capacidad;
        }
        return hash;
    }
    
     /**
     * Agrega un nodo a la tabla hash utilizando el nombre completo como clave.
     * Si la clave ya existe, no realiza ninguna acci&oacute;n.
     * Redimensiona la tabla si se alcanza el 70% de su capacidad.
     *
     * @param clave La clave asociada al nodo.
     * @param nodo  El nodo a agregar a la tabla.
     */
    public void agregar(String clave, Nodo nodo) {
        if (tamaño >= capacidad * 0.7) {
            redimensionar();
        }
        int indice = hash(clave);
        while (tabla[indice] != null) {
            if (tabla[indice].getNombreCompleto().equals(clave)) { 
                return; // No se permite duplicados
            }
            indice = (indice + 1) % capacidad; 
        }
        tabla[indice] = nodo;
        tamaño++;
    }
    
    /**
     * Agrega un nodo a la tabla hash utilizando el mote como clave.
     * Si el mote ya existe, no realiza ninguna acci&oacute;n.
     * Redimensiona la tabla si se alcanza el 70% de su capacidad.
     *
     * @param clave La clave asociada al mote del nodo.
     * @param nodo  El nodo a agregar a la tabla.
     */
    public void agregarMote(String clave, Nodo nodo) {
        if (tamaño >= capacidad * 0.7) {
            redimensionarMote();
        }
        int indice = hash(clave);
        while (tabla[indice] != null) {
            if (tabla[indice].getMote().equals(clave)) { 
                return; 
            }
            indice = (indice + 1) % capacidad; 
        }
        tabla[indice] = nodo;
        tamaño++;
    }
    
    public void agregarTitulo(String clave, Nodo nodo) {
        if (tamaño >= capacidad * 0.7) {
            redimensionarTitulo();
        }
        int indice = hash(clave);
        while (tabla[indice] != null) {
            if (tabla[indice].getTitulo().equals(clave)) { 
                return; 
            }
            indice = (indice + 1) % capacidad; 
        }
        tabla[indice] = nodo;
        tamaño++;
    }
    
    /**
     * Busca un nodo en la tabla hash utilizando el nombre completo como clave.
     *
     * @param clave La clave del nodo a buscar.
     * @return El nodo encontrado o {@code null} si no existe.
     */
    public Nodo buscar(String clave) {
        int indice = hash(clave);
        int intentos = 0;
        while (tabla[indice] != null && intentos < capacidad) {
            if (tabla[indice].getNombreCompleto().equals(clave)) {
                return tabla[indice];
            }
            indice = (indice + 1) % capacidad;
            intentos++;
        }
        return null;
    }
    
     /**
     * Busca un nodo en la tabla hash utilizando el mote como clave.
     *
     * @param clave El mote del nodo a buscar.
     * @return El nodo encontrado o {@code null} si no existe.
     */
    public Nodo buscarMote(String clave) {
        int indice = hash(clave);
        int intentos = 0;
        while (tabla[indice] != null && intentos < capacidad) {
            if (tabla[indice].getMote().equals(clave)) {
                return tabla[indice];
            }
            indice = (indice + 1) % capacidad;
            intentos++;
        }
        return null;
    }
    
    /**
     * Busca un nodo en la tabla hash utilizando el t&iacute;tulo como clave.
     *
     * @param clave El mote del nodo a buscar.
     * @return El nodo encontrado o {@code null} si no existe.
     */
    public Nodo buscarTitulo(String clave) {
        int indice = hash(clave);
        int intentos = 0;
        while (tabla[indice] != null && intentos < capacidad) {
            if (tabla[indice].getTitulo().equals(clave)) {
                return tabla[indice];
            }
            indice = (indice + 1) % capacidad;
            intentos++;
        }
        return null;
    }
     /**
     * Duplica la capacidad de la tabla y redistribuye los nodos existentes utilizando el nombre completo como clave.
     * Este m&eacute;todo se invoca autom&aacute;ticamente cuando la tabla alcanza el 70% de su capacidad.
     */
    private void redimensionar() {
        capacidad *= 2;
        Nodo[] nuevaTabla = new Nodo[capacidad];
        for (Nodo nodo : tabla) {
            if (nodo != null) {
                int indice = hash(nodo.getNombreCompleto());
                while (nuevaTabla[indice] != null) {
                    indice = (indice + 1) % capacidad;
                }
                nuevaTabla[indice] = nodo;
            }
        }
        tabla = nuevaTabla;
    }
    
     /**
     * Duplica la capacidad de la tabla y redistribuye los nodos existentes utilizando el mote como clave.
     * Este m&eacute;todo se invoca autom&aacute;ticamente cuando la tabla alcanza el 70% de su capacidad.
     */
    private void redimensionarMote() {
        capacidad *= 2;
        Nodo[] nuevaTabla = new Nodo[capacidad];
        for (Nodo nodo : tabla) {
            if (nodo != null) {
                int indice = hash(nodo.getMote());
                while (nuevaTabla[indice] != null) {
                    indice = (indice + 1) % capacidad;
                }
                nuevaTabla[indice] = nodo;
            }
        }
        tabla = nuevaTabla;
    }
    
    /**
     * Duplica la capacidad de la tabla y redistribuye los nodos existentes utilizando el t&iacute;tulo como clave.
     * Este m&eacute;todo se invoca autom&aacute;ticamente cuando la tabla alcanza el 70% de su capacidad.
     */
    private void redimensionarTitulo() {
        capacidad *= 2;
        Nodo[] nuevaTabla = new Nodo[capacidad];
        for (Nodo nodo : tabla) {
            if (nodo != null) {
                int indice = hash(nodo.getTitulo());
                while (nuevaTabla[indice] != null) {
                    indice = (indice + 1) % capacidad;
                }
                nuevaTabla[indice] = nodo;
            }
        }
        tabla = nuevaTabla;
    }
}
