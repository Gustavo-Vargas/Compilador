
package com.nyxcode.compilador.objetos;

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

    public Map<Integer, String> lstLex;

    public Lexema() {
    }

    public Lexema(String valor, short linea) {
        this.valor = valor;
        this.linea = linea;
        listLexemas();
        token = selectToken(valor);
    }

    private int selectToken(String palabra) {

        // esta validando solamente a la palabra y no a la lista de lexemas
        if (lstLex.containsValue(palabra.toLowerCase())) {
            for (Integer key : lstLex.keySet()) {
                if (palabra.equals(lstLex.get(key).toLowerCase())) {
                    return key;
                }
            }
        }

        try {
            Integer.parseInt(palabra);
            return 60;

        } catch (NumberFormatException e) {
            return 50;
        }

    }

    public void listLexemas() {
        lstLex = new TreeMap<Integer, String>();
        // Palabras Reservadas
        lstLex.put(1, "const");
        lstLex.put(2, "var");
        lstLex.put(3, "proced");
        lstLex.put(4, "begin");
        lstLex.put(5, "write");
        lstLex.put(6, "read");
        lstLex.put(7, "call");
        lstLex.put(8, "if");
        lstLex.put(9, "while");
        lstLex.put(10, "then");
        lstLex.put(11, "do");
        lstLex.put(12, "for");
        lstLex.put(13, "to");
        lstLex.put(14, "dto");
        lstLex.put(15, "end");

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

    public String getValor() {
        return valor;
    }

    public int getToken() {
        return token;
    }

    public short getLinea() {
        return linea;
    }

}
