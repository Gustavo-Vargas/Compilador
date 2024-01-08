
package com.nyxcode.compilador.controls;

/**
 *
 * @author DocSe
 */
public class Errores {

    private String mensaje;

    public String errores(int error, int renglon) {
        switch (error) {

            case 3:
                mensaje = ("Error 3: Se se esperaba un \"Proced\"  : "
                        + renglon + "\n");
                break;

            case 10:
                mensaje = ("Error 10: Se se esperaba un \"then\"  : "
                        + renglon + "\n");
                break;

            case 11:
                mensaje = ("Error 11: Se se esperaba un \"do\" "
                        + ": " + renglon + " \n");
                break;

            case 15:
                mensaje = ("Error 15: Se se esperaba un \"end\"  "
                        + ": " + renglon + " \n");
                break;

            case 30:
                mensaje = ("Error 30: Se se esperaba un \"=\"  "
                        + ": " + renglon + " \n");
                break;

            case 40:
                mensaje = ("Error 40: Se se esperaba un \".\"  "
                        + ": " + renglon + " \n");
                break;

            case 42:
                mensaje = ("Error 42: Se se esperaba un \";\"  "
                        + ": " + renglon + " \n");
                break;

            case 44:
                mensaje = ("Error 44: Se se esperaba un \")\"  "
                        + ": " + renglon + " \n");
                break;

            case 50:

                mensaje = ("Error 50: Se se esperaba un \"identificador\"  "
                        + ": " + renglon + " \n");
                break;

            case 60:
                mensaje = ("Error 60: Se se esperaba un \"número\"  "
                        + ": " + renglon + " \n");
                break;

            case 70:
                mensaje = ("Error 70: \"Inicio de porposicion\"  : " + renglon + " \n");
                break;

            case 71:
                mensaje = ("Error 71: Se se esperaba un \"to\" o"
                        + " \"dto\"  : " + renglon + " \n");
                break;

            case 72:
                mensaje = ("Error 72: Se se esperaba un \"==\", "
                        + "\"!=\", \"<\", \">\", \"<=\" o \">=\",  "
                        + ": " + renglon + " \n");
                break;

            case 73:
                mensaje = ("Error 73: Se se esperaba un "
                        + "\"identificador\" 0 un \"número\"  "
                        + ": " + renglon + " \n");
                break;
                
            case 74:
                mensaje = ("Error 74: Se se esperaba un \"(\","
                        + " \"identificador\" o un \"número\"  "
                        + ": " + renglon + " \n");
                break;

            case 75:
                mensaje = ("Error 75: Se se esperaba un \"begin\", "
                        + "\"id\", \"write\", \"read\", \"call\", \"if\", "
                        + "\"while\" o \"for\"  : " + renglon + " \n");
                break;

        }
        return mensaje;
    }

}
