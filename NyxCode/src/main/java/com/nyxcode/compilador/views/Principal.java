/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.nyxcode.compilador.views;

import com.nyxcode.compilador.controls.Lexema;
import com.nyxcode.compilador.controls.Procesos;
import com.nyxcode.compilador.controls.Sintaxis;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author DocSe
 */
public class Principal extends javax.swing.JFrame {

    Procesos p;
    Sintaxis s = new Sintaxis();
    private List<Lexema> lstLexema;
    private List<String> lstErrores;
    
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        p = new Procesos(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtCode = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuOpen = new javax.swing.JMenuItem();
        mnuClean = new javax.swing.JMenuItem();
        mnuClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuLexico = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtCode.setColumns(20);
        txtCode.setRows(5);
        jScrollPane1.setViewportView(txtCode);

        txtOutput.setColumns(20);
        txtOutput.setRows(5);
        jScrollPane2.setViewportView(txtOutput);

        jMenu1.setText("Archivo");

        mnuOpen.setText("Abrir");
        mnuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpenActionPerformed(evt);
            }
        });
        jMenu1.add(mnuOpen);

        mnuClean.setText("Limpiar");
        mnuClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCleanActionPerformed(evt);
            }
        });
        jMenu1.add(mnuClean);

        mnuClose.setText("Salir");
        mnuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCloseActionPerformed(evt);
            }
        });
        jMenu1.add(mnuClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Compilador");

        mnuLexico.setText("Lexico");
        mnuLexico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLexicoActionPerformed(evt);
            }
        });
        jMenu2.add(mnuLexico);

        jMenuItem2.setText("Sintactico");
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Semantico");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpenActionPerformed
        ArrayList lineas = p.openFile();
    }//GEN-LAST:event_mnuOpenActionPerformed

    private void mnuCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_mnuCloseActionPerformed

    private void mnuCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCleanActionPerformed
        p.clean();
    }//GEN-LAST:event_mnuCleanActionPerformed

    private void mnuLexicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLexicoActionPerformed
        lstLexema = p.procesaLineas();
        lstErrores = s.porgrama(lstLexema, getTxtOutput());
    }//GEN-LAST:event_mnuLexicoActionPerformed

    public JTextArea getTxtCode() {
        return txtCode;
    }

    public JTextArea getTxtOutput() {
        return txtOutput;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem mnuClean;
    private javax.swing.JMenuItem mnuClose;
    private javax.swing.JMenuItem mnuLexico;
    private javax.swing.JMenuItem mnuOpen;
    private javax.swing.JTextArea txtCode;
    private javax.swing.JTextArea txtOutput;
    // End of variables declaration//GEN-END:variables
}
