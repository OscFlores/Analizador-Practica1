/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.Procesos;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
/**
 *
 * @author Oscar
 */
public class Buscar {
    private final JTextArea taCodigoFuente;
    private final String palabraClave;
    private final char[] caracteres;
    private int posicion = 0;
    private final Highlighter Resaltar;

    public Buscar(JTextArea taCodigoFuente, String palabraClave) {
        this.taCodigoFuente = taCodigoFuente;
        this.palabraClave = palabraClave;
        caracteres = palabraClave.toCharArray();
        Resaltar = taCodigoFuente.getHighlighter();
        Resaltar.removeAllHighlights();
    }

    public int buscarCoincidencias() throws IndexOutOfBoundsException {
        String codigoFuente = taCodigoFuente.getText();
        char caracter;
        int numCoincidencias = 0;
        while (posicion < codigoFuente.length()) {
            caracter = codigoFuente.charAt(posicion);
            if (caracter == caracteres[0]) {
                int contador = 0;
                for (int i = 0; i < palabraClave.length(); i++) {
                    caracter = codigoFuente.charAt(posicion);
                    if (caracter == caracteres[i]) {
                        contador++;
                    }
                    posicion++;
                }
                if (contador == palabraClave.length()) {
                    Resaltar(posicion - palabraClave.length(), posicion);
                    numCoincidencias++;
                }
            } else {
                posicion++;
            }
        }
        return numCoincidencias;
    }

    private void Resaltar(int posInicial, int posFinal) {
        DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        try {
            Resaltar.addHighlight(posInicial, posFinal, highlightPainter);
        } catch (BadLocationException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
