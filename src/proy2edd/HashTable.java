/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

/**
 *
 * @author chela
 */
public class HashTable {
    private Nodo [] tabla;
    private int capacidad;
    private int tamaño;
    
    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new Nodo[capacidad];
        this.tamaño = 0;
    }
    
    private int hash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (hash * 31 + clave.charAt(i)) % capacidad;
        }
        return hash;
    }
    
    public void agregar(String clave, Nodo nodo) {
        if (tamaño >= capacidad * 0.7) {
            redimensionar();
        }
        int indice = hash(clave);
        while (tabla[indice] != null) {
            if (tabla[indice].getNombreCompleto().equals(clave) || (tabla[indice].getMote() != null && tabla[indice].getMote().equals(clave))) {
                return; //no agregar duplicados
            }
            indice = (indice + 1) % capacidad; //resolucion de colision por desplazamiento lineal
        }
        tabla[indice] = nodo;
        tamaño++;
    }
    
    public Nodo buscar(String clave) {
        int indice = hash(clave);
        int intentos = 0;
        while (tabla[indice] != null && intentos < capacidad) {
            if (tabla[indice].getNombreCompleto().equals(clave) || (tabla[indice].getMote() != null && tabla[indice].getMote().equals(clave))) {
                return tabla[indice];
            }
            indice = (indice + 1) % capacidad;
            intentos++;
        }
        return null;
    }
    
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
}
