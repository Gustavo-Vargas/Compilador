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
public class Sintaxis {

//    <Programa>::= <Bloque> .
    public void porgrama(ArrayList<Lexema> lex) {
        bloque();
        if (token != 500) {
            Errores.mostrar(500);
        } else {
            System.out.println("Programa Correcto");
        }
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
        if (token != 1) {
            return;
        }
        token = Tokens.nextToken();
        cicloConst();
        if (token != 501) {
            Errores.mostrar(501);
            return;
        }
        token = Tokens.nextToken();
    }

//	<BlqVar>::= ε
//      <BlqVar> ::= var <CicloVar> ;
    private void blqVar() {
        if (token != 2) {
            return;
        }
        token = Tokens.nextToken();
        cicloVar();
        if (token != 501) {
            Errores.mostrar(501);
            return;
        }
        token = Tokens.nextToken();
    }

//    <BlqProc>::= ε
//    <BlqProc> ::=  proced (3)  id (100) ;(501) <Bloque> ; <BlqProc>
    private void blqProc() {
        if (token != 3) {
            return;
        }
        token = Tokens.nextToken();
        if (token != 100) {
            Errores.mostrar(100);
            return;
        }
        token = Tokens.nextToken();
        if (token != 501) {
            Errores.mostrar(501);
            return;
        }
        bloque();
        if (token != 501) {
            Errores.mostrar(501);
            return;
        }
        token = Tokens.nextToken();
        blqProc();
    }

//<CilcloConst>::= id(100) =(200) num(600) <AuxConst>
    private void cicloConst() {
        if (token != 100) {
            Errores.mostrar(100);
            return;
        }
        token = Tokens.nextToken();
        if (token != 200) {
            Errores.mostrar(200);
            return;
        }
        token = Tokens.nextToken();
        if (token != 600) {
            Errores.mostrar(600);
            return;
        }
        auxConst();
    }

//	<AuxConst>::= ε
//	<AuxConst>::= ,(502) <CilcloConst>
    private void auxConst() {
        if (token != 502) {
            return;
        }
        token = Tokens.nextToken();
        cicloConst();
    }

//	<CicloVar>::= id <AuxVar>
    private void cicloVar() {
        if (token != 100) {
            Errores.mostrar(100);
            return;
        }
        token = Tokens.nextToken();
        auxVar();
    }

//    <AuxVar>::= , <CicloVar>
//    <AuxVar>::= ε 
    private void auxVar() {
        if (token != 502) {
            return;
        }
        token = Tokens.nextToken();
        cicloVar();
    }

    public void proposicion() {
        switch (token) {
            //<proposicion>::= BEGIN(4) <CicloProp> END(5)
            case 4:
                // duda si es cicloProp o BlqProc
                cicloProp();
                if (token != 5) {
                    Errores.mostrar(5);
                    return;
                }
                token = Tokens.nextToken();
                break;

            //<proposicion>::= call (6) id (100)
            case 6:
                token = Tokens.nextToken();
                if (token != 100) {
                    Errores.mostrar(100);
                    return;
                }
                token = Tokens.nextToken();
                break;

            //<proposicion>::= id(100) =(200) <expre>
            case 100:
                token = Tokens.nextToken();
                if (token != 200) {
                    Errores.mostrar(200);
                    return;
                }
                token = Tokens.nextToken();
                expre();
                break;

            //<proposicion>::= if(7) <condicion> then(8) <proposicion>
            case 7:
                token = Tokens.nextToken();
                condicion()
                :
                if (token != 8) {
                    Errores.mostrar(8);
                    return;
                }
                token = Tokens.nextToken();
                proposicion();
                break;
            //<proposicion>::=  WHILE(9) <condicion> Do(10) <proposicion>
            case 9:
                token = Tokens.nextToken();
                condicion();
                if (token != 10) {
                    Errores.mostrar(10);
                    return;
                }
                token = Tokens.nextToken();
                proposicion();
                break;

            //<proposicion>::= for(11) id(100) =(200) <expre> <downto> <expre> Do(10) <proposicion>
            case 11:
                token = Tokens.nextToken();
                if (token != 100) {
                    Errores.mostrar(100);
                    return;
                }
                token = Tokens.nextToken();
                if (token != 200) {
                    Errores.mostrar(200);
                    return;
                }
                token = Tokens.nextToken();
                expre();
                downto();
                expre();
                if (token != 10) {
                    Errores.mostrar(10);
                    return;
                }
                token = Tokens.nextToken();
                proposicion();
                break;

            //<proposicion>::= read(12) id(100)
            case 12:
                token = Tokens.nextToken();
                if (token != 100) {
                    Errores.mostrar(100);
                    return;
                }
                token = Tokens.nextToken();
                break;

            //<proposicion>::= write(13) <idNum>
            case 13:
                token = Tokens.nextToken();
                idNum();
                break;

            default:
                Errores.mostrar(0xFFF); // Error "Inicio de proposicion"    public void metodo(int .....)
        }
    }

//	
// < CicloProp > ::= <proposicion> <AuxProp>
    private void CicloProp() {
        proposicion();
        AuxProp();
    }

//       <AuxProp>::=  ε     
//      <AuxProp>::=  ; <CicloProp>
    private void AuxProp() {
        if (token != 501) {
            return;
        }
        token = Tokens.nextToken();
        CicloProp();
    }

//      <downto>::= to  
//      <downto>::= dto 
    private void downto() {
        if (token != 901 || token != 902) {
            Errores.mostrar(901);
            Errores.mostrar(902);
            return;
        }
        token = Tokens.nextToken();
    }

//      <idNum>::= id
//	<idNum>::= num  
    private void idNum() {
        if (token != 100 || token != 600) {
            Errores.mostrar(100);
            Errores.mostrar(600);
            return;
        }
        token = Tokens.nextToken();
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
        if (token != 601 || token != 602) {
            return;
        }
        token = Tokens.nextToken();
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
        if (token != 603 || token != 604) {
            return;
        }
        token = Tokens.nextToken();

    }

//<factor>::= ( <expre> )
//<factor>::= id
//<factor>::= num
    private void factor() {
        if (token != 605 || token != 100 || token != 600) {
            Errores.mostrar(605);
            Errores.mostrar(100);
            Errores.mostrar(600);
            return;
        }
        token = Tokens.nextToken();
    }

//   <condicion>::= <expre> <operadores> <expre>
    private void condcion() {
        expre();
        operadores();
        expre();
    }

    //	<operadores>::= == (606)
//	<operadores>::= != (607)
//	<operadores>::= <  (608)
//	<operadores>::= >  (609)
//	<operadores>::= <= (610)
//	<operadores>::= >= (611)
    private void operadores() {
        if (token != 606 || token != 607 || token != 608
                || token != 609 || token != 610 || token != 611) {
            Errores.mostrar(606);
            Errores.mostrar(607);
            Errores.mostrar(608);
            Errores.mostrar(609);
            Errores.mostrar(610);
            Errores.mostrar(611);
            return;
        }
        token = Tokens.nextToken();
    }

}
