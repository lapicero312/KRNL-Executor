package Lector.Modelo;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.util.*;

public class LeerPGN {
    ArrayList <String> movimientos;
    private String ruta;
    public ArrayList <String> metadata;
    public LeerPGN(String ruta){
        movimientos=new ArrayList<>();
        metadata= new ArrayList<>();
        this.setRuta(ruta);
    }

    public void leerMetadata(){
        File archivo= new File(ruta);
        try(BufferedReader br= new BufferedReader(new FileReader(archivo))){
            String linea;
            int contadorEspacios=0;
            String lineasJuego ="";
            while((linea=br.readLine())!=null){
                if(linea.equals("")){
                    contadorEspacios++;
                    continue;
                }
                if(linea.contains("[")){
                    metadata.add(linea);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void leerMovimientos() {
        File archivo = new File(ruta);
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean movimientosEncontrados = false;
            StringBuilder movimientosLinea = new StringBuilder();

            while ((linea = br.readLine()) != null) {
                // Si encontramos una línea vacía, ignoramos las líneas de metadata
                if (linea.trim().isEmpty() && !movimientosEncontrados) {
                    continue;
                }
                // Cuando encontramos una línea que no contiene "[" (que indica metadata), asumimos que es parte de los movimientos
                if (!linea.startsWith("[") && !linea.trim().isEmpty()) {
                    movimientosEncontrados = true;
                    movimientosLinea.append(linea).append(" ");
                }
            }

            // Una vez acumulados todos los movimientos en una sola línea, los dividimos en turnos
            String[] partes = movimientosLinea.toString().trim().split("\\d+\\.\\s?");
            for (int i = 1; i < partes.length; i++) { // Empezamos en 1 para ignorar el primer split vacío
                String turno = i + ". " + partes[i].trim(); // Agrega el número del turno
                movimientos.add(turno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getMovimientos(){
        return movimientos;
    }


    public String getMetadata(){
        return metadata.toString();
    }

    void setRuta(String ruta){
        this.ruta=ruta;
    }

    public void limpiar(){
        this.movimientos.clear();
        this.metadata.clear();
    }
}