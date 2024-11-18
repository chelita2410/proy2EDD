/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proy2edd.interfaces;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.ViewerListener;
import proy2edd.Arbol;
import proy2edd.MiLista;
import proy2edd.Nodo;
import proy2edd.VisualizarGrafoCasas;
/**
 *
 * @author chela
 */
public class InterfazInteractuar extends javax.swing.JFrame implements ViewerListener {
    private JTextArea infoTextArea;
    private Arbol arbol;
    private JPanel graphPanel;
    private VisualizarGrafoCasas visualizeGraph;
    //private ViewerPipe viewerPipe;
    
    //private Graph grafo;
   // private Viewer viewer;
   // private boolean loop = true;
            

    /**
     * Creates new form InterfazInteractuar
     */
    public InterfazInteractuar() {
        setTitle("Visor de árboles genealógicos");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int xSize = (int) toolkit.getScreenSize().getWidth();
        int ySize = (int) toolkit.getScreenSize().getHeight();
        this.setSize(xSize, ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        arbol = new Arbol();
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo JSON");
        JMenuItem loadMenuItem = new JMenuItem("Cargar árbol genealógico del JSON");
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        infoTextArea = new JTextArea(10, 30);
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        add(scrollPane, BorderLayout.EAST);
        
        graphPanel = new JPanel(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
        
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArbolDesdeJSON();
            }
        });
        
        //initComponents();
        
    }
    
    private void cargarArbolDesdeJSON() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            arbol.cargarDesdeJSON(filePath);
            mostrarGrafo();
    }
        }

    private void mostrarGrafo() {
        visualizeGraph.construirGrafo(arbol);
        graphPanel.removeAll();
        ViewPanel viewPanel = visualizeGraph.getGraphView();
        graphPanel.add(viewPanel, BorderLayout.CENTER);
        visualizeGraph.addViewerListener(this);
        graphPanel.revalidate();
        graphPanel.repaint();
        
       /** graphPanel.removeAll();
        grafo = new SingleGraph("Árbol Genealógico");
        grafo.setAttribute("ui.stylesheet", "node { fill-color: lightblue; }");
        grafo.setAutoCreate(true);
        grafo.setStrict(false);
        
        MiLista nodos = arbol.getTodosLosNodos();
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
         } */
        
      /**  for (Nodo nodo : arbol.getGrafo()) {
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
        } */
        
      /**  viewer = new Viewer(grafo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        ViewPanel viewPanel = viewPanel = viewer.addDefaultView(false);
        
        graphPanel.add(viewPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
        
        viewerPipe = viewer.newViewerPipe();
        viewerPipe.addViewerListener((ViewerListener) this);
        viewerPipe.addSink(grafo);
        
        new Thread (() -> {
            while (loop) {
                viewerPipe.pump();   
            }
        }).start();  */ 
    } 

    public void buttonPushed(String id) {
        Nodo nodo = arbol.buscarNodoPorNombre(id);
        if (nodo != null) {
            infoTextArea.setText(nodo.mostrarInformacion());
        }
    }

    public void buttonReleased(String id) {
        
    }
 
    public void viewClosed(String viewName) {
        
    }
    
    public void mouseLeft(String eventDetails) {
        
    }
    
    
    
   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 68, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(77, 146, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazInteractuar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazInteractuar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazInteractuar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazInteractuar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazInteractuar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseOver(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
