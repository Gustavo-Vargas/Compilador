/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nyxcode.compilador.controls;

import java.util.ArrayList;

/**
 *
 * @author DocSe
 */
public class Lexema {

    private String valor;
    private int token;
    private short linea;

    private ArrayList<String> reservadas;

    public Lexema(String valor, short linea) {
        this.valor = valor;
        this.linea = linea;
        reservadas.add("const");
        reservadas.add("var");
        reservadas.add("proced");
        token = selectToken(valor);
    }

    private int selectToken(String palabra) {
        int t = 0;

        if (reservadas.contains(palabra.toLowerCase())) {
            return reservadas.indexOf(palabra.toLowerCase());
        }
        try {
            Integer.parseInt(palabra);
            return 100;

        } catch (NumberFormatException e) {

        }
        return t;

    }

}
