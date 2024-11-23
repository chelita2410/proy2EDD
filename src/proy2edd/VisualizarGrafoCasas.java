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
public class VisualizarGrafoCasas {

    private Graph grafo;
    private Viewer viewer;
    private ViewerPipe viewerPipe;

    public VisualizarGrafoCasas() {
        this.grafo = new SingleGraph("Árbol Genealógico");

        grafo.setAttribute("org.graphstream.ui", "swing");
        this.grafo.setAttribute("ui.stylesheet", "node { fill-color: lightblue; }");

        grafo.setAttribute("ui.stylesheet", "node { fill-color: grey; size: 25px; }");
    }

    public void construirGrafo(Arbol arbol) {

        MiLista nodos = arbol.getTodosLosNodos();
        for (int i = 0; i < 10; i++) {
            Nodo nodo = nodos.get(i);
            if(nodo != null){
            System.out.println(nodo.getNombreCompleto());}
        }
        for (int i = 0; i < nodos.size(); i++) {
            Nodo nodo = nodos.get(i);

            if (grafo.getNode(nodo.getNombreCompleto()) == null) {
                grafo.addNode(nodo.getNombreCompleto()).setAttribute("ui.label", nodo.getNombreCompleto());
            }

            if (nodo.getPadre() != null) {
                String padre = nodo.getPadre().getNombreCompleto();
                String hijo = nodo.getNombreCompleto();
                if (grafo.getEdge(padre + "-" + hijo) == null && grafo.getEdge(hijo + "-" + padre) == null) {
                    grafo.addEdge(padre + "-" + hijo, padre, hijo);
                }
            }
        }
    }

    public ViewPanel getGraphView() {
        viewer = grafo.display(false);
        viewer.enableAutoLayout();
        return (ViewPanel) viewer.addDefaultView(false);
    }

    public void addViewerListener(ViewerListener listener) {
        viewerPipe = viewer.newViewerPipe();
        viewerPipe.addViewerListener(listener);
        viewerPipe.addSink(grafo);

        new Thread(() -> {
            while (true) {
                viewerPipe.pump();
            }
        }).start();
    }

}
