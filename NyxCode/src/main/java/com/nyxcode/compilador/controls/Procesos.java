/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.views.Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author DocSe
 */
public class Procesos {

    // Variables o Attributos
    Principal v;
    ArrayList<String> lineas;
    ArrayList<String> lexemas;
    

    // Constructor o Constructores
    public Procesos(Principal v) {
        this.v = v;
        lineas = new ArrayList<>();
        lexemas = new ArrayList<>();
    }

    // Metodos
    // Funcion
    public ArrayList<String> openFile() {
        clean();
        String linea = "";
        JFileChooser seleccionar = new JFileChooser();
        seleccionar.setCurrentDirectory(new File(".\\")); // Establece el directorio actual
        int result = seleccionar.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = seleccionar.getSelectedFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                while ((linea = br.readLine()) != null) {
                    lineas.add(linea);
                }
                showLineas();
            } catch (IOException e) { // 
                JOptionPane.showMessageDialog(null,
                        "ERRIR! \n" + e,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return lineas;
    }

    private void showLineas() {
        for (String linea : lineas) {
            v.getTxtCode().append(linea + "\n");
        }
        procesaLineas();
        for (String lexema : lexemas) {
            System.out.println(lexema);
        }
    }

    private void clean() {
        v.getTxtCode().setText("");
        lineas.clear();
    }

    // Protected  Se puede usar en el mismo paquete
    protected void procesaLineas() {
        for (String linea : lineas) {
            forLineas:
            for (int i = 0; i < linea.length(); i++) {
                char c = linea.charAt(i);
                String palabra;

                if (Character.isLetter(c)) {
                    palabra = "";
                    while (Character.isLetterOrDigit(c) || c == '_') {
                        palabra += c;
                        i++;
                        if (i == linea.length()) {
                            lexemas.add(palabra);
                            continue forLineas;
                        }
                        c = linea.charAt(i);
                    }
                    lexemas.add(palabra);
                }
                if (Character.isDigit(c)) {
                    palabra = "";
                    while (Character.isDigit(c)) {
                        palabra += c;
                        i++;
                        if (i == linea.length()) {
                            lexemas.add(palabra);
                            continue forLineas;
                        }
                        c = linea.charAt(i);
                    }
                    lexemas.add(palabra);
                }
                if (c == '<' || c == '>' || c == '=') {
                    palabra = "" + c;
                    palabra += (i < linea.length() && linea.charAt(i + 1) == '=')
                            ? linea.charAt(i + 1) : "";
                    i++;
                    lexemas.add(palabra);
                    continue;
                }

                if (c == '\n' || c == '\t' || c == ' ') {
                    continue;
                }
                palabra = "" + c;
                lexemas.add(palabra);
            }
        }
    }

}
