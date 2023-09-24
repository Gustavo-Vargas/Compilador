/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nyxcode.compilador.controls;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author DocSe
 */
public class Lexema {

    private String valor;
    private int token;
    private short linea;

    private Map<Integer, String> lstLex;

    public Lexema(String valor, short linea) {
        this.valor = valor;
        this.linea = linea;
        listLexemas();
        token = selectToken(valor);
    }

    private int selectToken(String palabra) {
        int t = 0;

        if (lstLex.containsValue(valor.toLowerCase())) {
            //return lstLex.ge

            //return lstLex.indexOf(palabra.toLowerCase());
        }

        try {
            Integer.parseInt(palabra);
            return 100;

        } catch (NumberFormatException e) {

        }

        return t;

    }

    private void listLexemas() {
        lstLex = new TreeMap<Integer, String>();
        // Palabras Reservadas
        lstLex.put(1, "Const");
        lstLex.put(2, "Var");
        lstLex.put(3, "Proced");
        lstLex.put(4, "Begin");
        lstLex.put(5, "Write");
        lstLex.put(6, "Read");
        lstLex.put(7, "Call");
        lstLex.put(8, "If");
        lstLex.put(9, "While");
        lstLex.put(10, "Then");
        lstLex.put(11, "Do");
        lstLex.put(12, "For");
        lstLex.put(13, "To");
        lstLex.put(14, "Dto");
        lstLex.put(15, "End");

        // Operadores
        lstLex.put(20, "+");
        lstLex.put(21, "-");
        lstLex.put(22, "*");
        lstLex.put(23, "/");

        lstLex.put(30, "=");
        lstLex.put(31, "==");
        lstLex.put(32, "!=");
        lstLex.put(33, "<");
        lstLex.put(34, "<=");
        lstLex.put(35, ">");
        lstLex.put(36, ">=");

        // Separadpres
        lstLex.put(40, ".");
        lstLex.put(41, ",");
        lstLex.put(42, ";");
        lstLex.put(43, "(");
        lstLex.put(44, ")");

        // identificadores y Numeros
        lstLex.put(50, "id");
        lstLex.put(60, "num");

    }

}
