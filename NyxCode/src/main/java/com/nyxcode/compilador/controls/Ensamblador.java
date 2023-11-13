/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    private List<String> lineIdent;
    private List<String> lineCuadru;

    public Ensamblador(Sintaxis sin) {
        this.sin = sin;

        this.lstCuadru = sin.lstCuadruplos;
        this.lstIdent = sin.lstIdent;

        crearCodigo();
        System.out.println("Archivo .asm creado");

    }

    public void crearCodigo() {
        File archivo = new File(".//Prueba.asm");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {

            bw.write(".model small");
            bw.newLine();
            bw.write(".stack 32");
            bw.newLine();
            bw.write(".data");
            bw.newLine();

            identificadores(bw);

            for (String linea : lineIdent) {
                bw.write("\t" + linea);
                bw.newLine(); // Agregar salto de línea
            }

            bw.write(".code");
            bw.newLine();

            bw.write(" " + "main proc far");
            bw.newLine();
            bw.write("  " + "mov ax, @data");
            bw.newLine();
            bw.write("  " + "mov ds, ax");
            bw.newLine();

            codigoIntermedeio(bw);

            for (String linea : lineIdent) {
                bw.write("\t" + linea);
                bw.newLine(); // Agregar salto de línea
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo no se encontró.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo.");
            e.printStackTrace();
        }
    }

    private void identificadores(BufferedWriter bw) {

        for (Identificador ident : lstIdent) {

        }

    }

    private void codigoIntermedeio(BufferedWriter bw) {

        for (Cuadruplos cuadru : lstCuadru) {

        }

    }

}
