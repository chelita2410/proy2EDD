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
 * Representa un nodo en un &aacute;rbol geneal&oacute;gico. Cada nodo contiene informaci&oacute;n
 * sobre un individuo, incluyendo su nombre, mote, t&iacute;tulo, padre e hijos.
 */
public class Nodo {
    private String nombreCompleto;
    private String mote;
    private String titulo;
    private String padreNombre;
    private String fate;
    private Nodo padre;
    private Nodo[] hijos;
    private int numHijos;
    
    /**
     * Constructor para inicializar un nodo con informaci&oacute;n b&aacute;sica.
     *
     * @param nombreCompleto El nombre completo del individuo.
     * @param mote El mote o apodo del individuo (puede ser null).
     * @param titulo El t&iacute;tulo del individuo (puede ser null).
     * @param padreNombre El nombre completo del padre (puede ser null).
     * @param fate El destino del individuo.
     */
    public Nodo(String nombreCompleto, String mote, String titulo, String padreNombre, String fate) {
        this.nombreCompleto = nombreCompleto;
        this.mote = mote;
        this.titulo = titulo;
        this.padreNombre = padreNombre;
        this.fate = fate;
        this.hijos = new Nodo[10]; //tamaño inicial, ajustable seg&uacute;n las necesidades
        this.numHijos = 0;
    }
    
    /**
     * Obtiene el nombre completo del nodo.
     *
     * @return El nombre completo.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    /**
     * Obtiene el destino del individuo del nodo.
     *
     * @return El destino.
     */
    public String getFate() {
        return fate;
    }
    
     /**
     * Establece el destino del individuo del nodo.
     *
     * @param fate El destino.
     */
    public void setFate(String fate) {
        this.fate = fate;
    }

    /**
     * Establece el mote del nodo.
     *
     * @param mote El mote o apodo.
     */
    public void setMote(String mote) {
        this.mote = mote;
    }
    
    /**
     * Establece el t&iacute;tulo del nodo.
     *
     * @param titulo El t&iacute;tulo.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Obtiene el mote del nodo.
     *
     * @return El mote o apodo.
     */
    public String getMote() {
        return mote;
    }
    
    /**
     * Obtiene el t&iacute;tulo del nodo.
     *
     * @return El t&iacute;tulo.
     */
    public String getTitulo() {
        return titulo;
    }
    
    /**
     * Obtiene el nombre del padre del nodo.
     *
     * @return El nombre del padre.
     */
    public String getPadreNombre() {
        return padreNombre;
    }
    
    /**
     * Obtiene el nodo que representa al padre.
     *
     * @return El nodo padre.
     */
    public Nodo getPadre() {
        return padre;
    }
    
    /**
     * Obtiene el arreglo de nodos hijos.
     *
     * @return Un arreglo con los nodos hijos.
     */
    public Nodo[] getHijos() {
        return hijos;
    }
    
    /**
     * Obtiene el n&uacute;mero de hijos actuales del nodo.
     *
     * @return El n&uacute;mero de hijos.
     */
    public int getNumHijos() {
        return numHijos;
    }
    
    /**
     * Establece el nodo padre.
     *
     * @param padre El nodo que representa al padre.
     */
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    
     /**
     * Establece el nombre del padre.
     *
     * @param padreNombre El nombre del padre.
     */
    public void setPadreNombre(String padreNombre) {
        this.padreNombre = padreNombre;
    }
    
    /**
     * Agrega un hijo al nodo. Si el arreglo de hijos est&aacute; lleno, lo expande autom&aacute;ticamente.
     *
     * @param hijo El nodo hijo que se desea agregar.
     */
    public void agregarHijo(Nodo hijo) {
        if (numHijos == hijos.length) {
            expandirHijos();
        }
        hijos[numHijos++] = hijo;
    }
    
    /**
     * Expande el tamaño del arreglo de hijos al doble de su capacidad actual.
     */
    private void expandirHijos() {
        Nodo[] nuevosHijos = new Nodo[hijos.length * 2];
        for (int i = 0; i < hijos.length; i++) {
            nuevosHijos[i] = hijos[i];
        }
        hijos = nuevosHijos;
    }
    
     /**
     * Muestra la informaci&oacute;n del nodo, incluyendo su nombre, mote, t&iacute;tulo,
     * padre e hijos.
     *
     * @return Una cadena con la informaci&oacute;n del nodo.
     */
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
        if (numHijos > 0) {
        info.append("Hijos: ");
        for (int i = 0; i < numHijos; i++) {
            info.append(hijos[i].getNombreCompleto()).append("\n");
            if (i < numHijos - 1) {
                info.append(", ");
            }
        }
    } else {
        info.append("Hijos: Ninguno");
    }
        if (fate != null) {
            info.append("Destino: ").append(fate).append("\n");
        }
    return info.toString();    
    }
}
