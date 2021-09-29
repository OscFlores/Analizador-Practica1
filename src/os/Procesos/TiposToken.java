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
public enum TiposToken {
    IDENTIFICADOR("Identificador"),
    NUMERO_ENTERO("Numero entero"),
    NUMERO_DECIMAL("Numero decimal"),
    SIGNO_PUNTUACION("Signo de puntuacion"),
    OPERADOR("Operador"),
    SIGNO_AGRUPACION("Signo de agrupacion"),
    ERROR("Error");

    private final String descripcion;

    private TiposToken(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}
