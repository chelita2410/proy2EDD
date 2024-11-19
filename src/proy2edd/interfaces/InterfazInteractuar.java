/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proy2edd.interfaces;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.ViewerListener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import proy2edd.Arbol;
import proy2edd.MiLista;
import proy2edd.Nodo;
import proy2edd.VisualizarGrafoCasas;
/**
 *
 * @author chela
 */
public class InterfazInteractuar extends javax.swing.JFrame  {
    private JTextArea areaInformacion;
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonCargar;
    private Arbol arbol;
    private JScrollPane scroll;
   // private JPanel graphPanel;
 //   private VisualizarGrafoCasas visualizeGraph;
   // private Component scroll;

            

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
        
        areaInformacion = new JTextArea(20, 50);
        areaInformacion.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaInformacion);
        add(scrollPane, BorderLayout.EAST);
        campoBusqueda = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        botonCargar = new JButton("Cargar JSON");
        
        arbol = new Arbol(areaInformacion);
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.add(new JLabel("Buscar personaje: "));
        panelSuperior.add(campoBusqueda);
        panelSuperior.add(botonBuscar);
        panelSuperior.add(botonCargar);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane();
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        
        add(panelPrincipal);
        
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarNodo();
            }
        });
        
        botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArbolDesdeJSON();
            }
        });
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        /* JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo JSON");
        JMenuItem loadMenuItem = new JMenuItem("Cargar árbol genealógico del JSON");
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
       
        
        graphPanel = new JPanel(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER); */
        
       /* loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArbolDesdeJSON();
            }
        }); */
        
       //initComponents();
        
    }
    
    private void buscarNodo() {
        String nombre = campoBusqueda.getText().trim();
        if (nombre.isEmpty()) {
            areaInformacion.append("Por favor introduce un nombre o mote para buscar.\n");
            return;
        }
        
        Nodo nodo = arbol.buscarNodoPorNombre(nombre);
        if (nodo != null) {
            areaInformacion.append("\nInformación del nodo:\n");
            areaInformacion.append(nodo.mostrarInformacion() + "\n");
            
        } else {
            areaInformacion.append("Nodo con nombre o mote '" + nombre + "' no encontrado.\n");
        }
    }
    
    private void cargarArbolDesdeJSON() {
        JFileChooser selectorArchivo = new JFileChooser();
        int opcion = selectorArchivo.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            try (FileReader lector = new FileReader(selectorArchivo.getSelectedFile())) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(lector);
                arbol.cargarDesdeJSON(obj.toString());
                areaInformacion.append("Árbol cargado exitosamente.\n");
            } catch (IOException | ParseException e) {
                areaInformacion.append("Error al cargar el archivo JSON: " + e.getMessage() + "\n");
            }
        }
    }
       /* JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            arbol.cargarDesdeJSON(filePath);
            mostrarGrafo();
    }
        } */

  /*  private void mostrarGrafo() {
        visualizeGraph.construirGrafo(arbol);
        graphPanel.removeAll();
        ViewPanel viewPanel = visualizeGraph.getGraphView();
        graphPanel.add(viewPanel, BorderLayout.CENTER);
        visualizeGraph.addViewerListener(this);
        graphPanel.revalidate();
        graphPanel.repaint();
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

   
}
