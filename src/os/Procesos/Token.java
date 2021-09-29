/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.Procesos;
/**
 *
 * @author Oscar
 */
public class Token {

    private final TiposToken tipoToken;
    private final String lexema;
    private final int numFila;
    private final int numColumna;

    public Token(TiposToken tipoToken, String lexema, int numFila, int numColumna) {
        this.tipoToken = tipoToken;
        this.lexema = lexema;
        this.numFila = numFila;
        this.numColumna = numColumna;
    }

    public TiposToken getTipoToken() {
        return tipoToken;
    }

    public String getLexema() {
        return lexema;
    }

    public int getNumFila() {
        return numFila;
    }

    public int getNumColumna() {
        return numColumna;
    }

    
}
