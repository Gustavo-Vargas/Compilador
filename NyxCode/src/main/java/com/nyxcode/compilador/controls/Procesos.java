/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nyxcode.compilador.controls;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author DocSe
 */
public class Procesos {
    
    JFrame v;

    public Procesos(JFrame v) {
        this.v = v;
    }
    
    public void openFile(){
        JOptionPane.showMessageDialog(v, "Entro a openFile");
    }
    
}
