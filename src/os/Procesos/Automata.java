/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.Procesos;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextArea;
/**
 *
 * @author Oscar
 */
public class Automata {
    private String codigoFuente;
    private JTextArea taCodigoFuente;
    private int posicion = 0;
    private final ArrayList<Token> tokens = new ArrayList<>();
    private Set<String> tokenSinRepetir = new HashSet<>();
    private final int[][] Transiciones = new int[8][6];
    private int estadoActual = 0;
    private int noFilaEstado = 1;
    private int noColumnaEstado = 0;
    private int noFilaTransicion = 1;
    private int noColumnaTransicion = 0;
    private boolean reiniciar = false;
    char[] signosAgrupacion = {'(', ')', '[', ']', '{', '}'}; 
    char[] operadores = {'+', '-', '*', '/', '%'}; 
    char[] signosPuntuacion = {'.', ',', ';', ':'}; 
    {
        Transiciones[0][0] = 1; Transiciones[0][1] = 2; Transiciones[0][2] =-1; Transiciones[0][3] = 5; Transiciones[0][4] = 6; Transiciones[0][5] = 7;                               
        Transiciones[1][0] = 1; Transiciones[1][1] = 1; Transiciones[1][2] =-1; Transiciones[1][3] =-1; Transiciones[1][4] =-1; Transiciones[1][5] =-1; 
        Transiciones[2][0] =-1; Transiciones[2][1] = 2; Transiciones[2][2] = 3; Transiciones[2][3] =-1; Transiciones[2][4] =-1; Transiciones[2][5] = -1;                              
        Transiciones[3][0] =-1; Transiciones[3][1] = 4; Transiciones[3][2] =-1; Transiciones[3][3] =-1; Transiciones[3][4] =-1; Transiciones[3][5] = -1;                                
        Transiciones[4][0] =-1; Transiciones[4][1] = 4; Transiciones[4][2] =-1; Transiciones[4][3] =-1; Transiciones[4][4] =-1; Transiciones[4][5] = -1;                               
        Transiciones[5][0] =-1; Transiciones[5][1] =-1; Transiciones[5][2] =-1; Transiciones[5][3] =-1; Transiciones[5][4] =-1; Transiciones[5][5] =-1;                                
        Transiciones[6][0] =-1; Transiciones[6][1] =-1; Transiciones[6][2] =-1; Transiciones[6][3] =-1; Transiciones[6][4] =-1; Transiciones[6][5] =-1;                                
        Transiciones[7][0] =-1; Transiciones[7][1] =-1; Transiciones[7][2] =-1; Transiciones[7][3] =-1; Transiciones[7][4] =-1; Transiciones[7][5] =-1;
    }
     private void getTokenSinRepetir() {
        for (Token t : tokens) {
            if (!t.getTipoToken().equals(TiposToken.ERROR)) {
                tokenSinRepetir.add(t.getLexema());
            }
        }
    }
     
    private boolean pertenece(char[] caracteres, char caracter) {
        boolean pertenece = false;
        for (char c : caracteres) {
            if (c == caracter) {
                pertenece = true;
                break;
            }
        }
        return pertenece;
    }
    private int obtenerEstadoSiguiente(int estadoActual, char caracterTransicion) {
        int estadoSiguiente = -1;
        try {
            if (estadoActual >= 0 && estadoActual <= 7) {  //S0-S7 
                estadoSiguiente = Transiciones[estadoActual][obtenerTipoCaracter(caracterTransicion)];
            }
        } catch (IndexOutOfBoundsException ex) {
        }
        return estadoSiguiente;
    }
    
    public Automata(JTextArea taCodigoFuente) {
        this.taCodigoFuente = taCodigoFuente;
        this.codigoFuente = taCodigoFuente.getText();
    }
    
    public void analizar(JTextArea taTransiciones) {
        taTransiciones.setText("");
        while (posicion < codigoFuente.length()) {
            obtenerToken(taTransiciones);
        }
    }

    public String getSingPluralr(int contador) {
        String palabra = " veces.";
        if (contador == 1) {
            palabra = " vez.";
        }
        return palabra;
    }
    
    public void mostrarErrores(JTextArea taErrores) {
        taErrores.setText("");
        for (Token e : tokens) {
            if (e.getTipoToken().equals(TiposToken.ERROR)) {
                taErrores.append("Error: " + e.getLexema() + " en la fila " + e.getNumFila() + " columna: " + e.getNumColumna() + "\n");
            }
        }
    }

    public void imprimirContadorTokens(JTextArea taNumVeces) {
        taNumVeces.setText("");
        getTokenSinRepetir();
        for (String s : tokenSinRepetir) {
            int contador = 0;
            for (int i = 0; i < tokens.size(); i++) {
                if (s.equals(tokens.get(i).getLexema())) {
                    contador++;
                }
            }
            taNumVeces.append(s + "  ----> " + contador + getSingPluralr(contador) + "\n");
        }
    }
   
    public void mostrarTokens(JTextArea taTokens) {
        taTokens.setText("");
        for (Token t : tokens) {
            taTokens.append(t.getLexema() + " ----> " + t.getTipoToken().toString() + " Fila: " + t.getNumFila() + ",Columna:  " + t.getNumColumna() + "\n");
        }
    }

    private TiposToken tipoToken() {
        TiposToken tipoToken;
        switch (estadoActual) {
            case 1:
                tipoToken = TiposToken.IDENTIFICADOR;
                break;
            case 2:
                tipoToken = TiposToken.NUMERO_ENTERO;
                break;
            case 4:
                tipoToken = TiposToken.NUMERO_DECIMAL;
                break;
            case 5:
                tipoToken = TiposToken.SIGNO_AGRUPACION;
                break;
            case 6:
                tipoToken = TiposToken.OPERADOR;
                break;
            case 7:
                tipoToken = TiposToken.SIGNO_PUNTUACION;
                break;
            default:
                tipoToken = TiposToken.ERROR;
        }
        return tipoToken;
    }
    private int obtenerTipoCaracter(char caracter) {
        int tipo = -1;
        if (Character.isLetter(caracter)) {
            tipo = 0;
        } else if (Character.isDigit(caracter)) {
            tipo = 1;
        } else if (estadoActual == 2 && caracter == '.') {
            tipo = 2;
        } else if (pertenece(signosAgrupacion, caracter)) {
            tipo = 3;
        } else if (pertenece(operadores, caracter)) {
            tipo = 4;
        } else if (pertenece(signosPuntuacion, caracter)) {
            tipo = 5;
        }
        return tipo;
    }
     private void obtenerToken(JTextArea taTokens) {
        boolean seguir = true;
        estadoActual = 0;
        String token = "";
        char caracter = ' ';
        while (seguir && posicion < codigoFuente.length()) {
            if (reiniciar) {
                noFilaTransicion++;
                noColumnaTransicion = 0;
            }
            caracter = codigoFuente.charAt(posicion);
            if (Character.isWhitespace(caracter)) {
                seguir = false;
            } else {
                this.reiniciar = false;
                int auxEstadoActual = estadoActual;
                estadoActual = obtenerEstadoSiguiente(estadoActual, caracter);
                token += caracter;
                taTokens.append("Pase del estado S" + auxEstadoActual + " al estado S" + estadoActual + " con: [" + caracter + "]" + "\n");
            }
            if (estadoActual == -1) {
                seguir = false;
            }
            noColumnaTransicion++;
            noColumnaEstado++;
            posicion++;
            if (caracter == '\n') {
                reiniciar = true;
                noFilaEstado++;
                noColumnaEstado = 0;
            }
        }
        if (!token.isBlank() && !tipoToken().equals(TiposToken.ERROR)) {
            Token nuevoToken = new Token(tipoToken(), token.replace(" ", ""), this.noFilaTransicion, this.noColumnaTransicion - 1);
            tokens.add(nuevoToken);
            taTokens.append("------------------------------------------------\n");
        } else if (!token.isBlank() && tipoToken().equals(TiposToken.ERROR)) {
            Token error = new Token(tipoToken(), token.replace(" ", ""), noFilaEstado, noColumnaEstado);
            tokens.add(error);
            taTokens.append("------------------------------------------------\n");
        }
    }
}
