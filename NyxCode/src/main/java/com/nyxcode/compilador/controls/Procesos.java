
package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Lexema;
import com.nyxcode.compilador.views.Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    ArrayList<Lexema> lstLexemas;

    // Constructor o Constructores
    public Procesos(Principal v) {
        this.v = v;
        lineas = new ArrayList<>();
        lstLexemas = new ArrayList<>();
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
        for (Lexema lex : lstLexemas) {
            //System.out.println(lex.getValor());
            System.out.println("Valor: " + lex.getValor()
                    + ", Token: " + lex.getToken()/*
                    + ", Linea: " + lex.getLinea()*/);
        }
    }

    public void clean() {
        v.getTxtCode().setText("");
        v.getTxtOutput().setText("");
        lineas.clear();
        lstLexemas.clear();
    }

    // Protected  Se puede usar en el mismo paquete
    public /*protected*/ List<Lexema> procesaLineas() {
        short noLineas = 0;
        for (String linea : lineas) {
            noLineas++;
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
                            lstLexemas.add(new Lexema(palabra, noLineas));
                            continue forLineas;
                        }
                        c = linea.charAt(i);
                    }
                    lstLexemas.add(new Lexema(palabra, noLineas));
                }
                if (Character.isDigit(c)) {
                    palabra = "";
                    while (Character.isDigit(c)) {
                        palabra += c;
                        i++;
                        if (i == linea.length()) {
                            lstLexemas.add(new Lexema(palabra, noLineas));
                            continue forLineas;
                        }
                        c = linea.charAt(i);
                    }
                    lstLexemas.add(new Lexema(palabra, noLineas));
                }
                if (c == '<' || c == '>' || c == '=') {
                    palabra = "" + c;
                    palabra += (i < linea.length() && linea.charAt(i + 1) == '=')
                            ? linea.charAt(i + 1) : "";
                    i++;
                    lstLexemas.add(new Lexema(palabra, noLineas));
                    continue;
                }
                if (c == '\n' || c == '\t' || c == ' ') {
                    continue;
                }
                palabra = "" + c;
                lstLexemas.add(new Lexema(palabra, noLineas));
            }
        }
        return lstLexemas;
    }

}
