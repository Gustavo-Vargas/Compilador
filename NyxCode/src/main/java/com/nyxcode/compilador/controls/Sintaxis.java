package com.nyxcode.compilador.controls;

import com.nyxcode.compilador.objetos.Cuadruplos;
import com.nyxcode.compilador.objetos.Identificador;
import com.nyxcode.compilador.objetos.Lexema;
import com.nyxcode.compilador.views.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DocSe
 */
public class Sintaxis {

    // Variables o Attributos
    Principal v;
//    Cuadruplos cuadru = new Cuadruplos();
    Semantico semant = new Semantico();
    Errores msjError = new Errores();
    private Lexema lex;
    private int indice;
    private Identificador ident;

    // Listas
    ArrayList<Identificador> lstIdent = new ArrayList<>();
    ArrayList<Cuadruplos> lstCuadru = semant.getLista();
    ArrayList<Integer> lstEtq = semant.getEtq();
    ArrayList<String> lstNumR = semant.getNumR();

    private List<Lexema> lstLexema;

    public Sintaxis(Principal v) {
        this.v = v;
    }

//    <Programa>::= <Bloque> .
    public void porgrama(List<Lexema> lstLex) {
        
        semant.limpiar();

        indice = 0;
        lstLexema = lstLex;
        lex = lstLexema.get(indice);
        bloque();
        if (lex.getToken() != 40) {
            v.getTxtOutput().append(msjError.errores(40, lex.getLinea()));
        } else {
            semant.addRet();
            System.out.println("Programa Correcto");

            System.out.println("--------------------------------------");
            int num = 0;
            for (Cuadruplos cua : lstCuadru) {
                //System.out.println(lex.getValor());
                System.out.println(++num + " ( " + cua.getOperador()
                        + ", " + cua.getOperando_1()
                        + ", " + cua.getOperando_2()
                        + ", " + cua.getResultado()
                        + " )");
            }

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
        if (lex.getToken() != 1) {
            return;
        }
        lex = nextToken();
        cicloConst();
        if (lex.getToken() != 42) {
            v.getTxtOutput().append(msjError.errores(42, lex.getLinea()));
            return;
        }
        lex = nextToken();
    }

    //<CilcloConst>::= id(50) =(30) num(60) <AuxConst>
    private void cicloConst() {
        if (lex.getToken() != 50) {
            v.getTxtOutput().append(msjError.errores(50, lex.getLinea()));
            return;
        }

        // Crea identificador sin atributo valor
        ident = new Identificador(lex.getValor(), lex.getToken(), lex.getLinea(), 'c');
        lex = nextToken();
        if (lex.getToken() != 30) {
            v.getTxtOutput().append(msjError.errores(30, lex.getLinea()));
            return;
        }
        lex = nextToken();
        if (lex.getToken() != 60) {
            v.getTxtOutput().append(msjError.errores(60, lex.getLinea()));
            return;
        }

        // Agrega atributo valor y ingesa a la lista de identificadores
        ident.setValor(lex.getValor());
        lstIdent.add(ident);

        lex = nextToken();

        auxConst();
    }

//	<AuxConst>::= ε
//	<AuxConst>::= ,(41) <CilcloConst>
    private void auxConst() {
        if (lex.getToken() != 41) {
            return;
        }
        lex = nextToken();
        cicloConst();
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
            v.getTxtOutput().append(msjError.errores(42, lex.getLinea()));
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
            v.getTxtOutput().append(msjError.errores(50, lex.getLinea()));
            return;
        }

        // Crea identificador sin atributo valor y agrega a la lsita
        ident = new Identificador(lex.getValor(), lex.getToken(), lex.getLinea(), 'p');
        lstIdent.add(ident);

        lex = nextToken();
        if (lex.getToken() != 42) {
            v.getTxtOutput().append(msjError.errores(42, lex.getLinea()));
            return;
        }
        bloque();
        if (lex.getToken() != 42) {
            v.getTxtOutput().append(msjError.errores(42, lex.getLinea()));
            return;
        }
        lex = nextToken();
        blqProc();
    }

//	<CicloVar>::= id <AuxVar>
    private void cicloVar() {
        if (lex.getToken() != 50) {
            v.getTxtOutput().append(msjError.errores(50, lex.getLinea()));
            return;
        }

        // Crea identificador sin atributo valor y agrega a la lsita
        ident = new Identificador(lex.getValor(), lex.getToken(), lex.getLinea(), 'v');
        lstIdent.add(ident);
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
            case 4 -> {
                lex = nextToken();
                cicloProp();
                if (lex.getToken() != 15) {
                    v.getTxtOutput().append(msjError.errores(15, lex.getLinea()));
                    return;
                }
                lex = nextToken();
            }

            //<proposicion>::= id(50) =(30) <expre>
            case 50 -> {
                /*      id se mete a pila operandos         */
                semant.addOperando(lex.getValor());

                lex = nextToken();
                if (lex.getToken() != 30) {
                    v.getTxtOutput().append(msjError.errores(30, lex.getLinea()));
                    return;
                }
                /*       pregunta el tope de pila (jerar) para operadores   */
                semant.pilaOperadores(lex.getValor());

                lex = nextToken();
                expre(false);

                /*       mientras no sea pila vacia genera Cuadruplos   */
                semant.noPilaVacia(false);

            }

            //<proposicion>::= write(5) <idNum>
            case 5 -> {
                lex = nextToken();
                idNum();
            }

            //<proposicion>::= read(6) id(50)
            case 6 -> {
                lex = nextToken();
                if (lex.getToken() != 50) {
                    v.getTxtOutput().append(msjError.errores(50, lex.getLinea()));
                    return;
                }

                // hacer cuadruplo read
                semant.addRead(lex.getValor());

                lex = nextToken();
            }

            //<proposicion>::= call (7) id (50)
            case 7 -> {
                lex = nextToken();
                if (lex.getToken() != 50) {
                    v.getTxtOutput().append(msjError.errores(50, lex.getLinea()));
                    return;
                }
                // hacer cuadruplo call
                semant.addRead(lex.getValor());
                lex = nextToken();
            }

            //<proposicion>::= if(8) <condicion> then(10) <proposicion>
            case 8 -> {
                lex = nextToken();
                condicion(false);
                if (lex.getToken() != 10) {
                    v.getTxtOutput().append(msjError.errores(10, lex.getLinea()));
                    return;
                }
                /*       llega fin de la exprecion  */
                semant.noPilaVacia(false);

                lex = nextToken();
                proposicion();
                /*       llega fin de if   */
                semant.sacaIncompleto();
            }

            //<proposicion>::=  WHILE(9) <condicion> Do(11) <proposicion>
            case 9 -> {
                semant.inicioWhile();

                lex = nextToken();
                condicion(false);
                if (lex.getToken() != 11) {
                    v.getTxtOutput().append(msjError.errores(11, lex.getLinea()));
                    return;
                }
                /*       llega fin de while   */
                semant.noPilaVacia(false);

                lex = nextToken();
                proposicion();

                semant.finWhile();
            }

            //<proposicion>::= for(12) id(50) =(30) <expre> <downto> <expre> Do(11) <proposicion>
            case 12 -> {
                lex = nextToken();
                if (lex.getToken() != 50) {
                    v.getTxtOutput().append(msjError.errores(50, lex.getLinea()));
                    return;
                }

                semant.inicioFor(lex.getValor());

                lex = nextToken();
                if (lex.getToken() != 30) {
                    v.getTxtOutput().append(msjError.errores(30, lex.getLinea()));
                    return;
                }

                /*       pregunta el tope de pila (jerar) para operadores   */
                semant.pilaOperadores(lex.getValor());

                lex = nextToken();

                expre(true);
                semant.noPilaVacia(true);
                downto();
                expre(true);
                semant.noPilaVacia(true);

                if (lex.getToken() != 11) {
                    v.getTxtOutput().append(msjError.errores(11, lex.getLinea()));
                    return;
                }
                lex = nextToken();
                proposicion();

                semant.finFor();
            }

            default ->
                v.getTxtOutput().append(msjError.errores(70, lex.getLinea()));
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
        if (!(lex.getToken() == 13 || lex.getToken() == 14)) {
            v.getTxtOutput().append(msjError.errores(71, lex.getLinea()));
            return;
        }
        semant.toDto(lex.getValor());

        lex = nextToken();
    }

//      <idNum>::= id
//	<idNum>::= num  
    private void idNum() {
        if (!(lex.getToken() == 50 || lex.getToken() == 60)) {
            v.getTxtOutput().append(msjError.errores(73, lex.getLinea()));
            return;
        }

        // Hacer cuadruplo write
        semant.addWrite(lex.getValor());

        lex = nextToken();
    }

//      <expre>::= <termino> <AuxExpre>
    private void expre(boolean isFor) {
        termino(isFor);
        auxExpre(isFor);
    }

//      <expreOpera> ::= + 
//	<expreOpera>::= -
    private void auxExpre(boolean isFor) {
        if (!(lex.getToken() == 20 || lex.getToken() == 21)) {
            return;
        }

        /*       pregunta el tope de pila (jerar)   */
        semant.pilaOperadores(lex.getValor());

        lex = nextToken();

        expre(isFor);
    }

//
//<termino>::= <factor> <auxTermino>
    private void termino(boolean isFor) {
        factor(isFor);
        auxTermino(isFor);
    }

//      <auxTermino>::= *
//	<auxTermino>::= /
    private void auxTermino(boolean isFor) {
        if (!(lex.getToken() == 22 || lex.getToken() == 23)) {
            return;
        }

        /*       pregunta el tope de pila (jerar)   */
        semant.pilaOperadores(lex.getValor());

        lex = nextToken();

        termino(isFor);

    }

//<factor>::= ( <expre> )
//<factor>::= id
//<factor>::= num
    private void factor(boolean isFor) {

        if (!(lex.getToken() == 43 || lex.getToken() == 50 || lex.getToken() == 60)) {
            v.getTxtOutput().append(msjError.errores(74, lex.getLinea()));
            return;
        }

        if (lex.getToken() == 43) {
            /*  Si es ( agegar a operadores      */
            semant.addOperador(lex.getValor());

            lex = nextToken();
            expre(isFor);

            if (lex.getToken() != 44) {
                v.getTxtOutput().append(msjError.errores(44, lex.getLinea()));
                return;
            }

            /*  Si es ) genera cuadruplos hasta encontrar un (  */
            semant.parentesis(isFor);

        }

        /*      Si es id o num agegar a operandos  */
        semant.addOperando(lex.getValor());

        lex = nextToken();

    }

//   <condicion>::= <expre> <operadores> <expre>
    private void condicion(boolean isFor) {
        expre(isFor);
        operadores();
        expre(isFor);
    }

//  <operadores>::= == (31)    <operadores>::= <  (33)   <operadores>::= <= (34)
//  <operadores>::= != (32)    <operadores>::= >  (35)   <operadores>::= >= (36)
    private void operadores() {
        if (!(lex.getToken() == 31 || lex.getToken() == 32
                || lex.getToken() == 33 || lex.getToken() == 35
                || lex.getToken() == 34 || lex.getToken() == 36)) {
            v.getTxtOutput().append(msjError.errores(72, lex.getLinea()));
            return;
        }
        
        semant.pilaOperadores(lex.getValor());

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

}
