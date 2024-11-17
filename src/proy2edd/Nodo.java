/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

/**
 *
 * @author chela
 */
public class Nodo {
    private String nombreCompleto;
    private String mote;
    private String titulo;
    private String padreNombre;
    private Nodo padre;
    private Nodo[] hijos;
    private int numHijos;
    
    public Nodo(String nombreCompleto, String mote, String titulo, String padreNombre) {
        this.nombreCompleto = nombreCompleto;
        this.mote = mote;
        this.titulo = titulo;
        this.padreNombre = padreNombre;
        this.hijos = new Nodo[10]; //tamaño inicial, ajustable según las necesidades
        this.numHijos = 0;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }

   /** public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setMote(String mote) {
        this.mote = mote;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setHijos(Nodo[] hijos) {
        this.hijos = hijos;
    }

    public void setNumHijos(int numHijos) {
        this.numHijos = numHijos;
    } */
    
    
    public void setMote(String mote) {
        this.mote = mote;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getMote() {
        return mote;
    }
    
    
    public String getTitulo() {
        return titulo;
    }
    
    public String getPadreNombre() {
        return padreNombre;
    }
    
    public Nodo getPadre() {
        return padre;
    }
    
    public Nodo[] getHijos() {
        return hijos;
    }
    
    public int getNumHijos() {
        return numHijos;
    }
    
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    
    public void agregarHijo(Nodo hijo) {
        if (numHijos == hijos.length) {
            expandirHijos();
        }
        hijos[numHijos++] = hijo;
    }
    
    private void expandirHijos() {
        Nodo[] nuevosHijos = new Nodo[hijos.length * 2];
        for (int i = 0; i < hijos.length; i++) {
            nuevosHijos[i] = hijos[i];
        }
        hijos = nuevosHijos;
    }
    
    public String mostrarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Nombre: ").append(nombreCompleto).append("\n");
        if (mote != null) {
            info.append("Mote: ").append(mote).append("\n");
        }
        if (titulo != null) {
            info.append("Título: ").append(titulo).append("\n");
        }
        if (padre != null) {
            info.append("Padre: ").append(padre.getNombreCompleto()).append("\n");
        }
        info.append("Hijos: ");
        for (int i = 0; i < numHijos; i++) {
            info.append(hijos[i].getNombreCompleto());
            if (i < numHijos - 1) {
                info.append(", ");
            }
        }
        return info.toString();
    }
}
