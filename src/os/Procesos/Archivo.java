/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.Procesos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JTextArea;
/**
 *
 * @author Oscar
 */
public class Archivo {
    private ArrayList<String> LineasArchivo = new ArrayList();
    public String Texto;
    public Archivo(File archivo){
        this.listarLineasArchivo(archivo);
    }
    public Archivo(){}
      
     private void listarLineasArchivo(File archivo) {
        try {
            BufferedReader leer = new BufferedReader(new FileReader(archivo));
            String linea = leer.readLine();
            while (linea != null) {
                LineasArchivo.add(linea);
                linea = leer.readLine();
            }
            leer.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
     
    public void mostrarLineas(JTextArea taCodigoFuente) {
        taCodigoFuente.setText("");
        for (String linea : LineasArchivo) {
            taCodigoFuente.append(linea+"\n");
        }
    }
    public boolean guardarArchivo(File archivo, String documento){
        FileOutputStream salida;
        boolean guardado = false;
        try{
            salida = new FileOutputStream(archivo);
            byte[] bytxt = documento.getBytes();
            salida.write(bytxt);
            guardado = true;
        }catch(FileNotFoundException ex){
        } catch (IOException ex) {
        }
        return guardado;
    }

}
