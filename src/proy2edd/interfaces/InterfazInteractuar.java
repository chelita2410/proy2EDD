/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proy2edd.interfaces;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.view.ViewerListener;
import proy2edd.Arbol;
import proy2edd.Nodo;

/**
 *
 * @author chela
 */
public class InterfazInteractuar extends javax.swing.JFrame {
    private Arbol arbol;
    private JTextArea areaInformacion;
    private Viewer viewer;
    private ViewerPipe viewerPipe;
    private boolean loop = true;

    /**
     * Creates new form InterfazInteractuar
     */
    public InterfazInteractuar() {
        setTitle("Visor de Arboles Genealogicos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        inicializarInterfaz();
        initComponents();
    }
    
    private void inicializarInterfaz() {
        System.setProperty("org.graphstream.ui", "swing");
        JPanel panel = new JPanel();
        JButton cargarButton = new JButton("Cargar Arbol Genealogico");
        areaInformacion = new JTextArea(10, 40);
        areaInformacion.setEditable(false);
        arbol = new Arbol(areaInformacion); //inicializa el arbol
        
        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    arbol.cargarDesdeJSON(filePath);
                    construirGrafo();
                }
            }
        });
        
        panel.add(cargarButton);
        panel.add(new JScrollPane(areaInformacion));
        add(panel, BorderLayout.SOUTH);
         
        
        //VERIFICAR ESTO DESPUES
        viewer = arbol.getGrafo().display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
       // viewer.enableAutoLayout();
       // View view = viewer.addDefaultView(false);
       // Component view = viewer.getDefaultView();
       // add(view, BorderLayout.CENTER);
       viewer = arbol.getGrafo().display();
       viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
       viewer.getGraphicGraph().setAttribute("ui.stylesheet", "node { fill-color: red; }");
       System.out.println("InterfazInteractuar es instancia de ViewerListener:" + (this instanceof ViewerListener));
        viewerPipe = viewer.newViewerPipe();
        viewerPipe.addViewerListener(new ViewerListener() {
            @Override
            public void viewClosed(String viewName) {
                loop  = false;
            }
            @Override
            public void buttonPushed(String id) {
                Nodo nodo = arbol.buscarNodoPorNombre(id);
                if (nodo != null) {
                    areaInformacion.setText(nodo.mostrarInformacion());
                }
            }
            @Override
            public void buttonReleased(String id) {
                
            }

            @Override
            public void mouseOver(String string) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseLeft(String string) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
       // viewerPipe.addViewerListener((ViewerListener) this);
        viewerPipe.addSink(viewer.getGraphicGraph());
        
        
        //procesa los eventos del clic
        new Thread(() -> {
            while (loop) {
                viewerPipe.pump();
            }
        }).start();

    }
    
    private void construirGrafo() {
        arbol.construirGrafo();
        viewer.getGraphicGraph().clear();
        
        for (Nodo nodo : arbol.getRaiz().getHijos()) {
            agregarNodoYConexiones(nodo);
        }
        
    }
    
    private void agregarNodoYConexiones(Nodo nodo) {
        viewer.getGraphicGraph().addNode(nodo.getNombreCompleto()).setAttribute("ui.label", nodo.getNombreCompleto());
        if (nodo.getPadre() != null) {
            viewer.getGraphicGraph().addEdge(nodo.getPadre().getNombreCompleto() + "-" + nodo.getNombreCompleto(), nodo.getPadre().getNombreCompleto(), nodo.getNombreCompleto(), true);
        }
        for (Nodo hijo : nodo.getHijos()) {
            agregarNodoYConexiones(hijo);
        } 
    }
    
    public void viewClosed(String viewName) {
        loop = false;
    }
    
    public void buttonPushed(String id) {
        Nodo nodo = arbol.buscarNodoPorNombre(id);
        if (nodo != null) {
            areaInformacion.setText(nodo.mostrarInformacion());
        }
    }
    
    public void buttonReleased(String id) {
        
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        fileChooser = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        cargarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        jLabel1.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        jLabel1.setText("Registro de Linajes");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, -1, -1));

        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });
        getContentPane().add(fileChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 580, 360));

        cargarButton.setText("Cargar Arbol");
        cargarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarButtonActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(cargarButton);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 400, 110, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileChooserActionPerformed

    private void cargarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargarButtonActionPerformed

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
    private javax.swing.JButton cargarButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

   // public void setDefaultCloseOperation(int EXIT_ON_CLOSE) {
   //     throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   // }
}
