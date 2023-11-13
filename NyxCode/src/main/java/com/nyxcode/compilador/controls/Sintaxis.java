/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Cuadruplos;
import com.nyxcode.compilador.objetos.Identificador;
import com.nyxcode.compilador.objetos.Lexema;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author DocSe
 */
public class Sintaxis {

    Errores msjError = new Errores();
    private Lexema lex;
    private int indice;

    // Listas
    ArrayList<Identificador> lstIdent;
    ArrayList<Cuadruplos> lstCuadruplos;
    private List<String> lstErrores = new ArrayList<>();
    private List<Lexema> lstLexema;

    // Pilas
    private Stack<Character> operadores = new Stack();
    private Stack<Character> operandos = new Stack();

//    <Programa>::= <Bloque> .
    public List<String> porgrama(List<Lexema> lstLex, JTextArea txt_Errores) {
        lstErrores.clear();
        indice = 0;
        lstLexema = lstLex;
        lex = lstLexema.get(indice);
        bloque();
        if (lex.getToken() != 40) {
            lstErrores.add(msjError.errores(40, lex.getLinea()));
        } else {
            System.out.println("Programa Correcto");
        }
        mostrarLista(lstErrores, txt_Errores);
        return lstErrores;
    }

//<Bloque>::= <BlqConst> <BlqVar> <BlqProc> <Proposicion>
    private void bloque() {
        blqConst();
        blqVar();
        blqProc();
        proposicion();
    }

//	<BlqConst>::= null
//	<BlqConst>::= const <CilcloConst> ; 
    private void blqConst() {
        if (lex.getToken() != 1) {
            return;
        }
        lex = nextToken();
        cicloConst();
        if (lex.getToken() != 42) {
            lstErrores.add(msjError.errores(42, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

//	<BlqVar>::= ε
//      <BlqVar> ::= var <CicloVar> ;
    private void blqVar() {
        if (lex.getToken() != 2) {
            return;
        }
        lex = nextToken();
        cicloVar();
        if (lex.getToken() != 42) {
            lstErrores.add(msjError.errores(42, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

//    <BlqProc>::= ε
//    <BlqProc> ::=  proced (3)  id (50) ;(42) <Bloque> ; <BlqProc>
    private void blqProc() {
        if (lex.getToken() != 3) {
            return;
        }
        lex = nextToken();
        if (lex.getToken() != 50) {
            lstErrores.add(msjError.errores(50, lex.getLinea()));
            return;
        }
        lex = nextToken();
        if (lex.getToken() != 42) {
            lstErrores.add(msjError.errores(42, lex.getLinea()));
            return;
        }
        bloque();
        if (lex.getToken() != 42) {
            lstErrores.add(msjError.errores(42, lex.getLinea()));
            return;
        }
        lex = nextToken();
        blqProc();
    }

//<CilcloConst>::= id(50) =(30) num(60) <AuxConst>
    private void cicloConst() {
        if (lex.getToken() != 50) {
            lstErrores.add(msjError.errores(50, lex.getLinea()));;
            return;
        }
        lex = nextToken();
        if (lex.getToken() != 30) {
            lstErrores.add(msjError.errores(30, lex.getLinea()));
            return;
        }
        lex = nextToken();
        if (lex.getToken() != 60) {
            lstErrores.add(msjError.errores(60, lex.getLinea()));
            return;
        }
        auxConst();
    }

//	<AuxConst>::= ε
//	<AuxConst>::= ,(541) <CilcloConst>
    private void auxConst() {
        if (lex.getToken() != 41) {
            return;
        }
        lex = nextToken();
        cicloConst();
    }

//	<CicloVar>::= id <AuxVar>
    private void cicloVar() {
        if (lex.getToken() != 50) {
            lstErrores.add(msjError.errores(50, lex.getLinea()));
            return;
        }
        lex = nextToken();
        auxVar();
    }

//    <AuxVar>::= , <CicloVar>
//    <AuxVar>::= ε 
    private void auxVar() {
        if (lex.getToken() != 41) {
            return;
        }
        lex = nextToken();
        cicloVar();
    }

    private void proposicion() {
        switch (lex.getToken()) {
            //<proposicion>::= BEGIN(4) <CicloProp> END(15)
            case 4:
                // duda si es cicloProp o BlqProc
                cicloProp();
                if (lex.getToken() != 15) {
                    lstErrores.add(msjError.errores(15, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                break;

            //<proposicion>::= call (7) id (50)
            case 7:
                lex = nextToken();
                if (lex.getToken() != 50) {
                    lstErrores.add(msjError.errores(50, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                break;

            //<proposicion>::= id(50) =(30) <expre>
            case 50:
                lex = nextToken();
                if (lex.getToken() != 30) {
                    lstErrores.add(msjError.errores(30, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                expre();
                break;

            //<proposicion>::= if(8) <condicion> then(10) <proposicion>
            case 8:
                lex = nextToken();
                condicion();
                if (lex.getToken() != 10) {
                    lstErrores.add(msjError.errores(10, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                proposicion();
                break;
            //<proposicion>::=  WHILE(9) <condicion> Do(11) <proposicion>
            case 9:
                lex = nextToken();
                condicion();
                if (lex.getToken() != 11) {
                    lstErrores.add(msjError.errores(11, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                proposicion();
                break;

            //<proposicion>::= for(12) id(50) =(30) <expre> <downto> <expre> Do(10) <proposicion>
            case 12:
                lex = nextToken();
                if (lex.getToken() != 50) {
                    lstErrores.add(msjError.errores(50, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                if (lex.getToken() != 30) {
                    lstErrores.add(msjError.errores(30, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                expre();
                downto();
                expre();
                if (lex.getToken() != 11) {
                    lstErrores.add(msjError.errores(11, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                proposicion();
                break;

            //<proposicion>::= read(6) id(50)
            case 6:
                lex = nextToken();
                if (lex.getToken() != 50) {
                    lstErrores.add(msjError.errores(50, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                break;

            //<proposicion>::= write(13) <idNum>
            case 13:
                lex = nextToken();
                idNum();
                break;

            default:
                lstErrores.add(msjError.errores(70, lex.getLinea()));
            //Errores.mostrar(0xFFF); // Error "Inicio de proposicion"    public void metodo(int .....)
        }
    }

//	
// < CicloProp > ::= <proposicion> <AuxProp>
    private void cicloProp() {
        proposicion();
        auxProp();
    }

//       <AuxProp>::=  ε     
//      <AuxProp>::=  ; <CicloProp>
    private void auxProp() {
        if (lex.getToken() != 42) {
            return;
        }
        lex = nextToken();
        cicloProp();
    }

//      <downto>::= to  
//      <downto>::= dto 
    private void downto() {
        if (lex.getToken() != 13 || lex.getToken() != 14) {
            lstErrores.add(msjError.errores(71, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

//      <idNum>::= id
//	<idNum>::= num  
    private void idNum() {
        if (lex.getToken() != 50 || lex.getToken() != 60) {
            lstErrores.add(msjError.errores(74, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

//      <expre>::= <termino> <AuxExpre>
    private void expre() {
        termino();
        auxExpre();
    }

//	<AuxExpre>::= <expreOpera> <expre>
//	<AuxExpre>::= ε
    private void auxExpre() {
        expreOpera();
        expre();
    }

//      <expreOpera> ::= + 
//	<expreOpera>::= -
    private void expreOpera() {
        if (lex.getToken() != 20 || lex.getToken() != 21) {
            return;
        }
        lex = nextToken();
    }

//
//<termino>::= <factor> <auxTermino>
    private void termino() {
        factor();
        auxTermino();
    }

//	<auxTermino>::= <terminoOpera> <termino>
//	<auxTermino>::= ε
    private void auxTermino() {
        terminoOpera();
        termino();
    }

//      <terminoOpera>::= *
//	<terminoOpera>::= /
    private void terminoOpera() {
        if (lex.getToken() != 22 || lex.getToken() != 23) {
            return;
        }
        lex = nextToken();

    }

//<factor>::= ( <expre> )
//<factor>::= id
//<factor>::= num
    private void factor() {
        if (lex.getToken() != 43 || lex.getToken() != 50 || lex.getToken() != 60) {
            lstErrores.add(msjError.errores(74, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

//   <condicion>::= <expre> <operadores> <expre>
    private void condicion() {
        expre();
        operadores();
        expre();
    }

    //	<operadores>::= == (31)
//	<operadores>::= != (32)
//	<operadores>::= <  (33)
//	<operadores>::= >  (35)
//	<operadores>::= <= (34)
//	<operadores>::= >= (36)
    private void operadores() {
        if (lex.getToken() != 31 || lex.getToken() != 32
                || lex.getToken() != 33 || lex.getToken() != 35
                || lex.getToken() != 34 || lex.getToken() != 36) {
            lstErrores.add(msjError.errores(72, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

    private Lexema nextToken() {
        if (indice < lstLexema.size()) {
            indice++;
            Lexema lexema = lstLexema.get(indice);
            return lexema;
        }
        return null;
    }

    private void mostrarLista(List<String> lstmensajes, JTextArea txtArea) {
        txtArea.setText("");

        for (String mensaje : lstmensajes) {
            txtArea.append(mensaje);
        }
    }

}
