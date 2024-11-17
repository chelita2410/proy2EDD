/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

/**
 *
 * @author chela
 */
public class MiLista {
    private Nodo[] nodos;
    private int size;
    
    public MiLista() {
        this.nodos = new Nodo[10]; //capacidad inicial
        this.size = 0;
    }
    
    public void add(Nodo nodo) {
        if (size == nodos.length) {
            resize();
        }
        nodos[size++] = nodo;
    }
    
    public Nodo get(int indice) {
        if (indice >= 0 && indice < size) {
            return nodos[indice];
        }
        return null; 
    }
    
    public int size() {
        return size;
    }
    
    private void resize() {
        Nodo[] newNodos = new Nodo[nodos.length * 2];
        for (int i = 0; i < nodos.length; i++) {
            newNodos[i] = nodos[i];
        }
        nodos = newNodos;
    }
    
}
