
package com.nyxcode.compilador.controls;

import java.util.Stack;

/**
 *
 * @author DocSe
 */
public class Cuadruplos {

    // Privados los atributos
    private String operador;
    private String operando_1;
    private String operando_2;
    private String resultado;
    private static int numR = 0;
    private int tam = 0;

    private Stack<Integer> incompletos = new Stack();
    private Stack<Character> saltos = new Stack();
    private Stack<Character> cicloFor = new Stack();

    public Cuadruplos(String operador, String operando_1, String operando_2, int tam) {
        this.operador = operador;
        this.operando_1 = operando_1;
        this.operando_2 = operando_2;

        //this.resultado = generaR();
        generaCuadruplo();
        this.tam = tam;
        // metodo de generar tipos de cuadruplos
    }

    private void generaCuadruplo() {

        if (operador == "=") {
            resultado = " " + operando_2;
            operando_2 = " ";
        }

        if (operador == "<" || operador == "<=" || operador == ">" 
                || operador == ">=" || operador == "==" || operador == "!=") {
            operador = getComplemento(operador);
            incompletos.add(tam);

        }
        if (operador == "*" || operador == "/" 
                || operador == "+" || operador == "-" ){
            resultado = "_R"+ ++numR;
            
        }
        if(operador == "write" || operador == "read"){
            resultado = operando_1;
            operando_1 = "";
        }
        
        
    }

    private String getComplemento(String operador) {
        switch (operador) {
            case "<":
                return "jge";
            case "<=":
                return "jg";
            case ">":
                return "jle";
            case ">=":
                return "jl";
            case "==":
                return "jne";
            case "!=":
                return "je";

            default:
                throw new AssertionError();
        }
    }

    public String getOperador() {
        return operador;
    }

    public String getOperando_1() {
        return operando_1;
    }

    public String getOperando_2() {
        return operando_2;
    }

    public String getResultado() {
        return resultado;
    }

}
