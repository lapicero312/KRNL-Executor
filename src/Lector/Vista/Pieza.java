package Lector.Vista;

import javax.swing.*;
import java.awt.*;

public class Pieza {
    private Image peonBlanco;
    private Image alfilBlanco;
    private Image torreBlanca;
    private Image reinaBlanca;
    private Image reyBlanco;
    private Image caballoBlanco;
    private  Image peonNegro;
    private  Image alfilNegro;
    private Image torreNegra;
    private  Image reinaNegra;
    private Image reyNegro;
    private Image caballoNegro;
    public Pieza(){
        //BLANCAS
        peonBlanco= new ImageIcon("src/Lector/recursos/imagenes/peonBlanco.png").getImage();
        alfilBlanco= new ImageIcon("src/Lector/recursos/imagenes/alfilBlanco.png").getImage();
        reyBlanco= new ImageIcon("src/Lector/recursos/imagenes/reyBlanco.png").getImage();
        caballoBlanco= new ImageIcon("src/Lector/recursos/imagenes/caballoBlanco.png").getImage();
        torreBlanca= new ImageIcon("src/Lector/recursos/imagenes/torreBlanca.png").getImage();
        reinaBlanca= new ImageIcon("src/Lector/recursos/imagenes/reinaBlanca.png").getImage();
        //NEGRAS
        peonNegro= new ImageIcon("src/Lector/recursos/imagenes/peonNegro.png").getImage();
        alfilNegro= new ImageIcon("src/Lector/recursos/imagenes/alfilNegro.png").getImage();
        reyNegro= new ImageIcon("src/Lector/recursos/imagenes/reyNegro.png").getImage();
        caballoNegro= new ImageIcon("src/Lector/recursos/imagenes/caballoNegro.png").getImage();
        torreNegra= new ImageIcon("src/Lector/recursos/imagenes/torreNegra.png").getImage();
        reinaNegra= new ImageIcon("src/Lector/recursos/imagenes/reinaNegra.png").getImage();
    }

    public Image getReinaBlanca(){
        return reinaBlanca;
    }
    public Image getReinaNegra(){
        return reinaNegra;
    }
    public Image getReyBlanco(){
        return reyBlanco;
    }

    public Image getAlfilBlanco() {
        return alfilBlanco;
    }

    public Image getAlfilNegro() {
        return alfilNegro;
    }

    public Image getCaballoBlanco() {
        return caballoBlanco;
    }

    public Image getCaballoNegro() {
        return caballoNegro;
    }

    public Image getPeonBlanco() {
        return peonBlanco;
    }

    public Image getPeonNegro() {
        return peonNegro;
    }

    public Image getTorreBlanca() {
        return torreBlanca;
    }

    public Image getTorreNegra() {
        return torreNegra;
    }

    public Image getReyNegro() {
        return reyNegro;
    }
}
