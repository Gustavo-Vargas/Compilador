package com.nyxcode.compilador.objetos;

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

    public Cuadruplos() {
    }

    public Cuadruplos(String operador, String operando_1, String operando_2, String resultado) {
        this.operador = operador;
        this.operando_1 = operando_1;
        this.operando_2 = operando_2;
        this.resultado = resultado;
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

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getOperador() {
        return operador;
    }

}
