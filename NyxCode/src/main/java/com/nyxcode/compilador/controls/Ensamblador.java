package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Cuadruplos;
import com.nyxcode.compilador.objetos.Identificador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DocSe
 */
public class Ensamblador {

    Sintaxis sin;
    private ArrayList<Identificador> lstIdent;
    private ArrayList<Cuadruplos> lstCuadru;
    private List<String> lineIdent = new ArrayList<>();
    private List<String> lineCuadru = new ArrayList<>();

    private ArrayList<Integer> lstEtq;
    private ArrayList<String> lstNumR;

    public Ensamblador(Sintaxis sin) {
        this.sin = sin;

        this.lstCuadru = sin.lstCuadru;
        this.lstIdent = sin.lstIdent;
        this.lstEtq = sin.lstEtq;
        this.lstNumR = sin.lstNumR;

        crearCodigo();
        System.out.println("Archivo .asm creado");
        clear();
    }

    public void crearCodigo() {
        File archivo = new File(".//Prueba.asm");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

            bw.write("""
                     .model small
                     .stack 32
                     .data
                     """);
            bw.newLine();
            identificadores();
            if (!lineIdent.isEmpty()) {
                for (String linea : lineIdent) {
                    bw.write("\t" + linea);
                    bw.newLine(); // Agregar salto de línea
                }
            }
            bw.newLine();
            bw.write("""
                     .code
                      main proc far
                       mov ax, @data
                       mov ds, ax
                     """);

            bw.newLine();
            codigoIntermedeio();
            if (!lineCuadru.isEmpty()) {
                for (String linea : lineCuadru) {

                    if (linea.charAt(0) == 'e') {
                        bw.write(linea);
                    } else {
                        bw.write("\t" + linea);
                    }

                    bw.newLine(); // Agregar salto de línea
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo no se encontró.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo.");
            e.printStackTrace();
        }
    }

    private void identificadores() {

        for (Identificador ident : lstIdent) {

            if (ident.getTipo() == 'c') {
                lineIdent.add(ident.getIdent() + " eq " + ident.getValor());
            }
            if (ident.getTipo() == 'v') {
                lineIdent.add(ident.getIdent() + " dw " + "?");
            }
            if (ident.getTipo() == 'p') {
                lineIdent.add(ident.getIdent() + " -- " + "?");
            }

        }

        for (String numR : lstNumR) {
            lineIdent.add(numR + " dw " + "?");

        }

    }

    private void codigoIntermedeio() {
        int pos = 0;

        for (Cuadruplos cuadru : lstCuadru) {
            pos++;
            if (lstEtq.contains(pos)) {
                lineCuadru.add("etq_ " + pos + ":");
            }

            if ("write".equals(cuadru.getOperador())) {
//                lineCuadru.add("wr " + cuadru.getResultado());

                lineCuadru.add("mov " + "ah, " + " 2");
                lineCuadru.add("mov " + "dl, " + cuadru.getResultado());
                lineCuadru.add("int " + "21h");

            }
            if ("read".equals(cuadru.getOperador())) {
//                lineCuadru.add("rd " + cuadru.getResultado());

                lineCuadru.add("mov " + "ah, " + " 0Ah");
                lineCuadru.add("mov " + "dx, " + cuadru.getResultado());
                lineCuadru.add("int " + "21h");
            }
            if ("call".equals(cuadru.getOperador())) {
                lineCuadru.add("call " + cuadru.getResultado());
            }
            if ("JMP".equals(cuadru.getOperador())) {
                lineCuadru.add("JMP " + "etq_" + cuadru.getResultado());
            }
            isOperador(cuadru);

            if ("inc".equals(cuadru.getOperador())) {
                lineCuadru.add("inc " + cuadru.getResultado());
            }
            if ("dec".equals(cuadru.getOperador())) {
                lineCuadru.add("dec " + cuadru.getResultado());
            }
            if ("ret".equals(cuadru.getOperador())) {
                lineCuadru.add("ret");
            }
        }

    }

    private void isOperador(Cuadruplos cuadru) {
        switch (cuadru.getOperador()) {
            case "=" -> {
                lineCuadru.add("mov " + cuadru.getOperando_1() + ", " + cuadru.getResultado());
            }
            case "*" -> {
                lineCuadru.add("mov " + "ax, " + cuadru.getOperando_1());
                lineCuadru.add("mov " + "bx, " + cuadru.getOperando_2());
                lineCuadru.add("mvl " + "bx");
                lineCuadru.add("mov " + cuadru.getResultado() + ", ax");
            }
            case "/" -> {
                lineCuadru.add("mov " + "ax, " + cuadru.getOperando_1());
                lineCuadru.add("mov " + "bh, " + cuadru.getOperando_2());
                lineCuadru.add("div " + "bh");
                lineCuadru.add("mov " + cuadru.getResultado() + ", al");
            }
            case "+" -> {
                lineCuadru.add("mov " + "ax, " + cuadru.getOperando_1());
                lineCuadru.add("add " + "bx, " + cuadru.getOperando_2());
                lineCuadru.add("mov " + cuadru.getResultado() + ", ax");
            }
            case "-" -> {
                lineCuadru.add("mov " + "ax, " + cuadru.getOperando_1());
                lineCuadru.add("sub " + "ax, " + cuadru.getOperando_2());
                lineCuadru.add("mov " + cuadru.getResultado() + ", ax");
            }

            case "jl", "jle", "jg", "jge", "je", "jne" -> {
                lineCuadru.add("CMP " + cuadru.getOperando_1() + ", " + cuadru.getOperando_2());
                lineCuadru.add(cuadru.getOperador() + " etq_" + cuadru.getResultado());
            }

        }
    }

    private void clear() {
        lineIdent.clear();
        lineCuadru.clear();
        lstIdent.clear();
        lstCuadru.clear();
    }

}
