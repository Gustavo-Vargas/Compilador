package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Lexema;
import com.nyxcode.compilador.views.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DocSe
 */
public class Lexico {

    // Variables o Attributos
    Principal v;
    //ArrayList<String> lineas;
    ArrayList<Lexema> lstLexemas;

    // Constructor o Constructores
    public Lexico(Principal v) {
        this.v = v;
        //lineas = new ArrayList<>();
        lstLexemas = new ArrayList<>();
    }

    // Protected  Se puede usar en el mismo paquete
    public  List<Lexema> procesaLineas(ArrayList<String> lineas) {
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
        showLex();
        return lstLexemas;
    }
    
    private void showLex() {
        for (Lexema lex : lstLexemas) {
            //System.out.println(lex.getValor());
            System.out.println("Valor: " + lex.getValor()
                    + ", Token: " + lex.getToken()/*
                    + ", Linea: " + lex.getLinea()*/);
        }
    }

}
