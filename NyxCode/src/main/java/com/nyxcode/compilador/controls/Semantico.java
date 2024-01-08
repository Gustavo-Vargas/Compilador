package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Cuadruplos;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author DocSe
 */
public class Semantico {

    private ArrayList<Cuadruplos> lstCuadruplos = new ArrayList<>();
    private ArrayList<Integer> lstEtq = new ArrayList<>();
    private ArrayList<String> lstNumR = new ArrayList<>();
    private String operando_2 = "";
    private String operando_1 = "";
    private static int numR = 0;

    // Pilas
    private Stack<String> operadores = new Stack();
    private Stack<String> operandos = new Stack();

    private Stack<Integer> incompletos = new Stack();
    private Stack<Integer> ciclos = new Stack();
    private Stack<String> pilaFor = new Stack();

    public void limpiar() {
        lstCuadruplos.clear();
        lstEtq.clear();
        lstNumR.clear();
        numR = 0;
        operadores.clear();
        operandos.clear();
        incompletos.clear();
        ciclos.clear();
        pilaFor.clear();
    }

    public ArrayList<Cuadruplos> getLista() {
        return lstCuadruplos;
    }

    public ArrayList<Integer> getEtq() {
        return lstEtq;
    }

    public ArrayList<String> getNumR() {
        return lstNumR;
    }

    public void addOperador(String valor) {
        operadores.push(valor);
    }

    public void addOperando(String valor) {
        operandos.push(valor);
    }

    private void generaCuadruplo(boolean isFor, String valor) {

        if ("=".equals(valor)) {
            operando_2 = operandos.pop();
            operando_1 = operandos.pop();
            lstCuadruplos.add(new Cuadruplos("=", operando_1,
                    "", operando_2));

            return;
        }

        if ("<".equals(valor) || "<=".equals(valor)
                || ">".equals(valor) || ">=".equals(valor)
                || "==".equals(valor) || "!=".equals(valor)) {
            operando_2 = operandos.pop();
            operando_1 = operandos.pop();
            valor = getComplemento(valor);

            lstCuadruplos.add(new Cuadruplos(valor, operando_1,
                    operando_2, ""));

            incompletos.add(this.lstCuadruplos.size());
            if (isFor) {
                ciclos.push(lstCuadruplos.size());
            }
            return;
        }

        if ("*".equals(valor) || "/".equals(valor)
                || "+".equals(valor) || "-".equals(valor)) {

            String resultado = "_R" + ++numR;
            operando_2 = operandos.pop();
            operando_1 = operandos.pop();
            lstCuadruplos.add(new Cuadruplos(valor, operando_1,
                    operando_2, resultado));

            operandos.push(resultado);
            lstNumR.add(resultado);
            return;
        }

    }

    public void addWrite(String valor) {
        lstCuadruplos.add(new Cuadruplos("write",
                "", "", valor));
    }

    public void addRead(String valor) {
        lstCuadruplos.add(new Cuadruplos("read",
                "", "", valor));
    }

    public void addCall(String valor) {
        lstCuadruplos.add(new Cuadruplos("call",
                "", "", valor));
    }

    public void addRet() {
        lstCuadruplos.add(new Cuadruplos("ret",
                "", "", ""));
    }

    public void toDto(String valor) {
        String signo = "";
        if ("dto".equals(valor)) {
            operadores.push(">");
            signo = pilaFor.pop() + "-";
        }
        if ("to".equals(valor)) {
            operadores.push("<");
            signo = pilaFor.pop() + "+";
        }
        pilaFor.push(signo);
    }

    public void pilaOperadores(String valor) {
        // Tope de pila es mayor o igual a valor
        while (!operadores.isEmpty() && jerarquia(operadores.peek())
                >= jerarquia(valor)) {
            generaCuadruplo(false, operadores.pop());
        }

        /* Agregar = a la pila operador */
        operadores.push(valor);

    }

    public void noPilaVacia(boolean isFor) {
        while (!operadores.isEmpty()) {
            generaCuadruplo(isFor, operadores.pop());
        }
    }

    public void parentesis(boolean isFor) {
        /*  Si es ) genera cuadruplos hasta encontrar un (  */
        while (!operadores.isEmpty()) {
            String operador = operadores.pop();
            if (operador == "(") {
                break;
            }
            // 
            generaCuadruplo(isFor, operador);

        }
    }

    public void sacaIncompleto() {
        int sigCuadr = lstCuadruplos.size() + 1;
        Cuadruplos cuadruplo = lstCuadruplos.get(incompletos.pop() - 1);
        cuadruplo.setResultado("" + sigCuadr);
        lstEtq.add(sigCuadr);
    }

    public void inicioWhile() {
        ciclos.add(lstCuadruplos.size() + 1);
    }

    public void finWhile() {
        lstCuadruplos.add(new Cuadruplos("JMP", "",
                "", "" + ciclos.pop()));
        sacaIncompleto();
    }

    public void inicioFor(String ident) {
        operandos.push(ident);
        operandos.push(ident);
        pilaFor.push(ident);
    }

    public void finFor() {
        int c = ciclos.pop();
        noPilaVacia(true);

        String signo = pilaFor.pop();

        if (signo.charAt(1) == '+') {
            lstCuadruplos.add(new Cuadruplos("inc", "",
                    "", "" + signo.charAt(0)));

        }

        if (signo.charAt(1) == '-') {
            lstCuadruplos.add(new Cuadruplos("dec", "",
                    "", "" + signo.charAt(0)));

        }

        lstCuadruplos.add(new Cuadruplos("JMP", "",
                "", "" + c));
        lstEtq.add(c);
        sacaIncompleto();
    }

    private int jerarquia(String operador) {
        switch (operador) {
            case "(", ")" -> {
                return 0;
            }
            case "=", "<", ">" -> {
                return 1;
            }
            case "+", "-" -> {
                return 2;
            }
            case "*", "/" -> {
                return 3;
            }
            case "^" -> {
                return 4;
            }

            default ->
                throw new AssertionError("Operador no valido " + operador);
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

}
