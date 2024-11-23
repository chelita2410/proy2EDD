/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proy2edd;

import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

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

    // Obt&eacute;n todos los nodos del &aacute;rbol
    MiLista nodos = arbol.getTodosLosNodos();
    
    // Primero, agregar todos los nodos al grafo
    for (int i = 0; i < nodos.size(); i++) {
        Nodo nodo = nodos.get(i);
        if (nodo != null) {
            // Verifica si el nodo ya existe en el grafo
            if (grafo.getNode(nodo.getNombreCompleto()) == null) {
                grafo.addNode(nodo.getNombreCompleto()).setAttribute("ui.label", nodo.getNombreCompleto());
            }
        }
    }

    // Ahora agregar las aristas entre los nodos
    for (int i = 0; i < nodos.size(); i++) {
        Nodo nodo = nodos.get(i);
        if (nodo != null) {
            // Agregar una arista si el nodo tiene un padre
            if (nodo.getPadre() != null) {
                String padre = nodo.getPadre().getNombreCompleto();
                String hijo = nodo.getNombreCompleto();
                // Verifica si ya existe una arista entre el padre y el hijo
                if (grafo.getEdge(padre + "-" + hijo) == null && grafo.getEdge(hijo + "-" + padre) == null) {
                    grafo.addEdge(padre + "-" + hijo, padre, hijo);
                }
            }

            // Agregar las aristas entre el nodo y sus hijos
            // Asegurarse de que la lista de hijos no es null
            if (nodo.getHijos() != null) {
                for (Nodo hijo : nodo.getHijos()) {
                    // Verifica si el hijo es null antes de intentar acceder a sus propiedades
                    if (hijo != null) {
                        if (grafo.getEdge(nodo.getNombreCompleto() + "-" + hijo.getNombreCompleto()) == null) {
                            grafo.addEdge(nodo.getNombreCompleto() + "-" + hijo.getNombreCompleto(), nodo.getNombreCompleto(), hijo.getNombreCompleto());
                        }
                    }
                }
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
