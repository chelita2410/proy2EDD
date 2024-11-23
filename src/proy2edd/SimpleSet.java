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
 * Clase que representa un conjunto de cadenas (String). Proporciona m&eacute;todos
 * para agregar elementos, verificar la existencia de un elemento y convertir
 * el conjunto en un arreglo.
 */
public class SimpleSet {
    private String[] elements;
    private int size;

    /**
     * Constructor que inicializa un conjunto vac&iacute;o con capacidad inicial de 10 elementos.
     */
    public SimpleSet() {
        elements = new String[10];
        size = 0;
    }

    /**
     * Agrega un elemento al conjunto si no existe ya.
     *
     * @param element El elemento a agregar al conjunto.
     */
    public void add(String element) {
        if (!contains(element)) {
            if (size == elements.length) {
                resize();
            }
            elements[size++] = element;
        }
    }

     /**
     * Verifica si el conjunto contiene un elemento.
     *
     * @param element El elemento a buscar en el conjunto.
     * @return {@code true} si el conjunto contiene el elemento, {@code false} en caso contrario.
     */
    public boolean contains(String element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Redimensiona el arreglo de elementos para permitir m&acute;s elementos si el conjunto
     * alcanza su capacidad m&acute;xima.
     */
    private void resize() {
        String[] newElements = new String[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

     /**
     * Convierte el conjunto en un arreglo de cadenas.
     *
     * @return Un arreglo de cadenas que contiene todos los elementos del conjunto.
     */
    public String[] toArray() {
        String[] result = new String[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }
}


