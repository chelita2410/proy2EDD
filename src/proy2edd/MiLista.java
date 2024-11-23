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
 * Clase que implementa una lista din&aacute;mica personalizada para almacenar objetos de tipo {@link Nodo}.
 * Se utiliza un arreglo interno para manejar los elementos, con redimensionamiento autom&aacute;tico al alcanzar la capacidad m&aacute;xima.
 */
public class MiLista {
    private Nodo[] nodos;
    private int size;
    
    /**
     * Constructor que inicializa la lista con una capacidad inicial de 10 elementos.
     */
    public MiLista() {
        this.nodos = new Nodo[10]; //capacidad inicial
        this.size = 0;
    }
    
     /**
     * Agrega un nodo al final de la lista.
     * Si la lista alcanza su capacidad m&aacute;xima, se redimensiona autom&aacute;ticamente.
     *
     * @param nodo El nodo a agregar a la lista.
     */
    public void add(Nodo nodo) {
        if (size == nodos.length) {
            resize();
        }
        nodos[size++] = nodo;
    }
    
    /**
     * Obtiene el nodo en la posici&oacute;n especificada.
     *
     * @param index El &iacute;ndice del nodo que se desea obtener (basado en 0).
     * @return El nodo en la posici&oacute;n especificada, o {@code null} si el &iacute;ndice es inv&aacute;lido.
     */
    public Nodo get(int index) {
        if (index >= 0 && index < size) {
            return nodos[index];
        }
        return null; 
    }
    
     /**
     * Obtiene el n&uacute;mero actual de elementos en la lista.
     *
     * @return El tamaño de la lista.
     */
    public int size() {
        return size;
    }
    
    /**
     * Aumenta la capacidad de la lista al doble de su tamaño actual para admitir m&aacute;s elementos.
     * Este m&eacute;todo se invoca autom&aacute;ticamente cuando la capacidad m&aacute;xima es alcanzada.
     */
    private void resize() {
        Nodo[] newNodos = new Nodo[nodos.length * 2];
        for (int i = 0; i < nodos.length; i++) {
            newNodos[i] = nodos[i];
        }
        nodos = newNodos;
    }
    
}
