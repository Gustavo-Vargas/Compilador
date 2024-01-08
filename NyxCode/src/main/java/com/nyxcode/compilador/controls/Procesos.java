package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Lexema;
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
//        JFileChooser seleccionar = new JFileChooser();
//        seleccionar.setCurrentDirectory(new File(".\\")); // Establece el directorio actual
//        int result = seleccionar.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
            //File archivo = seleccionar.getSelectedFile();
            File archivo = new File(".\\Ejemplo.pl0");
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
//        }

        return lineas;
    }

    private void showLineas() {
        for (String linea : lineas) {
            v.getTxtCode().append(linea + "\n");
        }

    }

    public void clean() {
        v.getTxtCode().setText("");
        v.getTxtOutput().setText("");
        lineas.clear();
        lstLexemas.clear();
    }

}
