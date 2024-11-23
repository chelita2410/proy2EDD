/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import proy2edd.Arbol;
import proy2edd.Nodo;

/**
 *
 * @author chela
 */
/**
 * Clase responsable de visualizar un &aacute;rbol geneal&oacute;gico como un grafo.
 * Utiliza la librer&iacute;a GraphStream para construir y mostrar el grafo.
 */
public class VisualizarGrafoCasas {

    private Graph grafo;
    private Viewer viewer;
    private ViewerPipe viewerPipe;

    /**
     * Constructor de la clase. Inicializa el grafo y establece sus atributos de estilo.
     */
    public VisualizarGrafoCasas() {
        this.grafo = new SingleGraph("Árbol Genealógico");
        // Configuración del grafo
        grafo.setAttribute("org.graphstream.ui", "swing");
        grafo.setAttribute("ui.stylesheet", "node { fill-color: grey; size: 25px; }");
    }

    /**
     * Construye el grafo a partir de un &aacute;rbol geneal&oacute;gico.
     * Agrega nodos y relaciones entre padres e hijos al grafo.
     *
     * @param arbol El &aacute;rbol geneal&oacute;gico que se utilizar&aacute; para construir el grafo.
     */
    public void construirGrafo(Arbol arbol) {

        MiLista nodos = arbol.getTodosLosNodos();
        for (int i = 0; i < 10; i++) {
            Nodo nodo = nodos.get(i);
            if(nodo != null){
            System.out.println(nodo.getNombreCompleto());}
        }
        // Agregar nodos y sus conexiones al grafo
        for (int i = 0; i < nodos.size(); i++) {
            Nodo nodo = nodos.get(i);
        // Verifica si el nodo ya existe en el grafo
            if (grafo.getNode(nodo.getNombreCompleto()) == null) {
                grafo.addNode(nodo.getNombreCompleto()).setAttribute("ui.label", nodo.getNombreCompleto());
            }
        // Agregar una arista si el nodo tiene un padre
            if (nodo.getPadre() != null) {
                String padre = nodo.getPadre().getNombreCompleto();
                String hijo = nodo.getNombreCompleto();
                if (grafo.getEdge(padre + "-" + hijo) == null && grafo.getEdge(hijo + "-" + padre) == null) {
                    grafo.addEdge(padre + "-" + hijo, padre, hijo);
                }
            }
        }
    }
/**
     * Devuelve un panel de vista gr&aacute;fica que muestra el grafo.
     * Activa el diseño autom&aacute;tico del grafo.
     *
     * @return Un objeto {@link ViewPanel} que contiene la visualizaci&oacute;n del grafo.
     */
    public ViewPanel getGraphView() {
        viewer = grafo.display(false);
        viewer.enableAutoLayout();
        return (ViewPanel) viewer.addDefaultView(true);
    }
    
/**
     * Agrega un listener de eventos al visor del grafo. Este listener puede capturar
     * eventos como clics en los nodos.
     *
     * @param listener Un objeto que implementa la interfaz {@link ViewerListener}.
     */
    public void addViewerListener(ViewerListener listener) {
        viewerPipe = viewer.newViewerPipe();
        viewerPipe.addViewerListener(listener);
        viewerPipe.addSink(grafo);

        // Hilo para manejar los eventos del visor en tiempo real
        new Thread(() -> {
            while (true) {
                viewerPipe.pump();
            }
        }).start();
    }

}
