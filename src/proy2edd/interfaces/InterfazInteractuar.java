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
 * Clase principal que representa la interfaz gr&aacute;fica de usuario para interactuar
 * con un &aacute;rbol geneal&oacute;gico. Permite cargar datos desde un archivo JSON,
 * buscar nodos en el &aacute;rbol y visualizar el grafo del &aacute;rbol geneal&oacute;gico.
 * 
 */
public class InterfazInteractuar extends javax.swing.JFrame implements ViewerListener{

    private JTextArea areaInformacion;
    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JButton botonGraficar;
    private JButton botonCargar;
    private Arbol arbol;
    private JScrollPane scroll;
    private JPanel graphPanel;
    private VisualizarGrafoCasas visualizeGraph;

    
    /*Constructor de la clase. Inicializa los componentes de la interfaz gr&aacute;fica,
     * configura los paneles y define las acciones de los botones. */
    public InterfazInteractuar() {
        setTitle("Visor de árboles genealógicos");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int xSize = (int) toolkit.getScreenSize().getWidth();
        int ySize = (int) toolkit.getScreenSize().getHeight();
        this.setSize(xSize, ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuraci&oacute;n del panel principal
        graphPanel = new JPanel(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
        areaInformacion = new JTextArea(20, 50);
        areaInformacion.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaInformacion);
        add(scrollPane, BorderLayout.EAST);
        campoBusqueda = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        botonGraficar = new JButton("Graficar");
        botonCargar = new JButton("Cargar JSON");

        arbol = new Arbol(areaInformacion);

        // Configuraci&oacute;n del panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.add(new JLabel("Buscar personaje: "));
        panelSuperior.add(campoBusqueda);
        panelSuperior.add(botonBuscar);
        panelSuperior.add(botonGraficar);
        panelSuperior.add(botonCargar);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane();
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        this.visualizeGraph = new VisualizarGrafoCasas();
        this.visualizeGraph.construirGrafo(arbol);
        add(panelPrincipal);

        // Acci&oacute;n del bot&oacute;n "Buscar"
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarNodo();
            }
        });
        // Acci&oacute;n del bot&oacute;n "Cargar JSON"
        botonCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArbolDesdeJSON();
            }
        });

        // Acci&oacute;n del bot&oacute;n "Graficar"
        botonGraficar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafo();
            }
        });
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Maneja el evento cuando se cierra una vista del grafo.
     *
     * @param viewName Nombre de la vista cerrada.
     */
    @Override
    public void viewClosed(String viewName) {
        System.out.println("Vista cerrada: " + viewName);
    }

    /**
     * Maneja el evento cuando se presiona un bot&oacute;n en el grafo.
     *
     * @param id Identificador del nodo asociado al bot&oacute;n presionado.
     */
    @Override
    public void buttonPushed(String id) {
        Nodo nodo = arbol.buscarNodoPorNombre(id);
        if (nodo != null) {
            areaInformacion.setText(nodo.mostrarInformacion());
        }
    }

    /**
     * Maneja el evento cuando se libera un bot&oacute;n en el grafo.
     *
     * @param id Identificador del nodo asociado al bot&oacute;n liberado.
     */
    @Override
    public void buttonReleased(String id) {
        System.out.println("Botón liberado: " + id);
    }

    /**
     * Busca un nodo en el &aacute;rbol por su nombre o mote y muestra su informaci&oacute;n en el &aacute;rea de texto.
     */
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
            nodo = arbol.buscarNodoPorMote(nombre);
            if (nodo != null) {
                areaInformacion.append("\nInformación del nodo:\n");
                areaInformacion.append(nodo.mostrarInformacion() + "\n");

            } else {
                areaInformacion.append("Nodo con nombre o mote '" + nombre + "' no encontrado.\n");
            }
        }
        }

    /**
     * Carga un &aacute;rbol geneal&oacute;gico desde un archivo JSON seleccionado por el usuario.
     */
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

    /**
     * Muestra el grafo del &aacute;rbol geneal&oacute;gico en el panel gr&aacute;fico.
     */
   private void mostrarGrafo() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Árbol Genealógico");
        graph.setAttribute("ui.stylesheet", "graph { fill-color: #EEE; }");
        visualizeGraph.construirGrafo(arbol);
        graphPanel.removeAll();
        ViewPanel viewPanel = visualizeGraph.getGraphView();
        graphPanel.add(viewPanel, BorderLayout.CENTER);
        visualizeGraph.addViewerListener(this);
        graphPanel.revalidate();
        graphPanel.repaint();
    } 
    
   /**
     * Maneja el evento cuando el mouse abandona un nodo del grafo.
     *
     * @param eventDetails Detalles del evento del mouse.
     */
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 68, -1, -1));

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
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseOver(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
