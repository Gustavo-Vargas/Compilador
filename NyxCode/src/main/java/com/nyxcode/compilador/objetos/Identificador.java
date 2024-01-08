package com.nyxcode.compilador.objetos;

/**
 *
 * @author DocSe
 */
public class Identificador {

    String ident;
    String valor;
    short token;
    int linea;
    char tipo; // variables constante procedimietnos

    public Identificador(String ident, int token, int linea, char tipo) {
        this.ident = ident;
        this.token = (short) token;
        this.linea = linea;
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIdent() {
        return ident;
    }

    public String getValor() {
        return valor;
    }

    public char getTipo() {
        return tipo;
    }

}
