package Lector.Vista;

import java.awt.*;

import Lector.Modelo.Movimientos;

public class Tablero {
    public int[][] mapa; // Mapa del tablero (8x8)
    public static int[][] fichas;
    public Movimientos mov;// Piezas en el tablero (0 es vacío)

    public Tablero() {
        mov=new Movimientos();
        inicializarTablero();
    }

    // Inicializar las piezas en su posición inicial
    public void inicializarTablero() {
        mapa = new int[8][8];
        fichas = new int[8][8];

        // Configuración inicial de las piezas
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1) fichas[i][j] = 2; // Peones negros
                else if (i == 6) fichas[i][j] = 1; // Peones blancos
                else fichas[i][j] = 0; // Vacío
            }
        }

        // Torres
        fichas[0][0] = fichas[0][7] = 4; // Torres negras
        fichas[7][0] = fichas[7][7] = 3; // Torres blancas
        // Caballos
        fichas[0][1] = fichas[0][6] = 6; // Caballos negros
        fichas[7][1] = fichas[7][6] = 5; // Caballos blancos
        // Alfiles
        fichas[0][2] = fichas[0][5] = 8; // Alfiles negros
        fichas[7][2] = fichas[7][5] = 7; // Alfiles blancos
        // Reyes y Reinas
        fichas[0][3] = 10; // Reina negra
        fichas[0][4] = 20; // Rey negro
        fichas[7][3] = 15; // Reina blanca
        fichas[7][4] = 30; // Rey blanco
    }

    // Método para dibujar el tablero
    public void dibujarTablero(Graphics g) {
        int size = 100; // Tamaño de cada celda del tablero
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                g.setColor((i + j) % 2 == 0 ? new Color(227,214,219) : new Color(159,22,70));
                g.fillRect(j * size, i * size, size, size);
            }
        }
    }


    // Método para dibujar las piezas
    public void dibujarFichas(Graphics g) {
        int size = 100; // Tamaño de cada celda del tablero
        Pieza pieza = new Pieza(); // Instancia la clase Pieza para acceder a las imágenes

        for (int i = 0; i < fichas.length; i++) {
            for (int j = 0; j < fichas[0].length; j++) {
                Image img = null;
                // Asigna la imagen correspondiente a la pieza según su valor
                switch (fichas[i][j]) {
                    case 1: img = pieza.getPeonBlanco(); break;
                    case 2: img = pieza.getPeonNegro(); break;
                    case 3: img = pieza.getTorreBlanca(); break;
                    case 4: img = pieza.getTorreNegra(); break;
                    case 5: img = pieza.getCaballoBlanco(); break;
                    case 6: img = pieza.getCaballoNegro(); break;
                    case 7: img = pieza.getAlfilBlanco(); break;
                    case 8: img = pieza.getAlfilNegro(); break;
                    case 15: img = pieza.getReinaBlanca(); break;
                    case 10: img = pieza.getReinaNegra(); break;
                    case 20: img = pieza.getReyNegro(); break;
                    case 30: img = pieza.getReyBlanco(); break;
                    default: img = null; break;
                }
                if (img != null) {
                    g.drawImage(img, j * size, i * size, size, size, null);
                }
            }
        }
    }

    // Método para mover una pieza en el tablero
    public boolean moverPieza(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        if (validarMovimiento(filaOrigen, colOrigen, filaDestino, colDestino)) {
            fichas[filaDestino][colDestino] = fichas[filaOrigen][colOrigen];
            fichas[filaOrigen][colOrigen] = 0;
            return true;
        }
        return false;
    }


    public boolean esReyEnJaques(int row, int col) {
        int piezaRey = fichas[row][col];
        if (piezaRey != 20 && piezaRey != 30) {
            System.out.printf("No hay un rey en la posición [%d][%d]\n", row, col);
            return false;
        }

        System.out.printf("Verificando jaque para el rey en [%d][%d]...\n", row, col);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int pieza = fichas[i][j];
                if (pieza == 0 || mov.esPiezaAliada(pieza, piezaRey)) continue;

                if (puedeAtacar(pieza, i, j, row, col)) {
                    System.out.printf("El rey está en jaque por la pieza en [%d][%d]\n", i, j);
                    return true;
                }
            }
        }

        System.out.println("El rey no está en jaque.");
        return false;
    }

    private boolean puedeAtacar(int pieza, int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        switch (pieza) {
            case 3:  // Torre blanca
            case 4:  // Torre negra
                return validarMovimientoTorre(filaOrigen, colOrigen, filaDestino, colDestino);
            case 5:  // Caballo blanco
            case 6:  // Caballo negro
                return validarMovimientoCaballo(filaOrigen, colOrigen, filaDestino, colDestino);
            case 7:  // Alfil blanco
            case 8:  // Alfil negro
                return validarMovimientoAlfil(filaOrigen, colOrigen, filaDestino, colDestino);
            case 15: // Reina blanca
            case 10: // Reina negra
                return validarMovimientoReina(filaOrigen, colOrigen, filaDestino, colDestino);
            case 30: // Rey blanco
            case 20: // Rey negro
                return validarMovimientoRey(filaOrigen, colOrigen, filaDestino, colDestino);
            default:
                return false;  // Si no es una pieza que pueda atacar, retornamos falso
        }
    }
    // Validación de los movimientos
    public boolean validarMovimiento(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        int pieza = fichas[filaOrigen][colOrigen];
        switch (pieza) {
            case 1: // Peón blanco
                return validarMovimientoPeon(filaOrigen, colOrigen, filaDestino, colDestino, true);
            case 2: // Peón negro
                return validarMovimientoPeon(filaOrigen, colOrigen, filaDestino, colDestino, false);
            case 3: // Torre blanca
            case 4: // Torre negra
                return validarMovimientoTorre(filaOrigen, colOrigen, filaDestino, colDestino);
            case 5: // Caballo blanco
            case 6: // Caballo negro
                return validarMovimientoCaballo(filaOrigen, colOrigen, filaDestino, colDestino);
            case 7: // Alfil blanco
            case 8: // Alfil negro
                return validarMovimientoAlfil(filaOrigen, colOrigen, filaDestino, colDestino);
            case 10: // Reina negra
            case 15: // Reina blanca
                return validarMovimientoReina(filaOrigen, colOrigen, filaDestino, colDestino);
            case 20: // Rey negro
            case 30: // Rey blanco
                return validarMovimientoRey(filaOrigen, colOrigen, filaDestino, colDestino);
            default:
                return false;
        }
    }

    // Validación del movimiento del peón
    public boolean validarMovimientoPeon(int filaOrigen, int colOrigen, int filaDestino, int colDestino, boolean esBlanco) {
        int direccion = esBlanco ? -1 : 1; // Dirección del peón (negro hacia abajo, blanco hacia arriba)
        // Mover una casilla hacia adelante
        if (colOrigen == colDestino && fichas[filaDestino][colDestino] == 0 && filaDestino == filaOrigen + direccion) {
            return true;
        }
        // Mover dos casillas en el primer movimiento
        if (colOrigen == colDestino && fichas[filaDestino][colDestino] == 0 && filaDestino == filaOrigen + 2 * direccion && (esBlanco ? filaOrigen == 6 : filaOrigen == 1)) {
            return true;
        }
        // Captura en diagonal
        if (Math.abs(colDestino - colOrigen) == 1 && filaDestino == filaOrigen + direccion && fichas[filaDestino][colDestino] != 0) {
            return true;
        }
        return false;
    }

    // Validación del movimiento de la torre
    public boolean validarMovimientoTorre(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        if (filaOrigen != filaDestino && colOrigen != colDestino) return false; // Solo puede moverse en línea recta
        if (existeObstaculo(filaOrigen, colOrigen, filaDestino, colDestino)) return false; // Verifica si hay piezas en el camino
        return true;
    }

    // Validación del movimiento del caballo
    public boolean validarMovimientoCaballo(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        int deltaX = Math.abs(filaDestino - filaOrigen);
        int deltaY = Math.abs(colDestino - colOrigen);
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }

    // Validación del movimiento del alfil
    public boolean validarMovimientoAlfil(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        if (Math.abs(filaDestino - filaOrigen) != Math.abs(colDestino - colOrigen)) return false; // El movimiento debe ser diagonal
        if (existeObstaculo(filaOrigen, colOrigen, filaDestino, colDestino)) return false; // Verifica si hay piezas en el camino
        return true;
    }

    // Validación del movimiento de la reina
    public boolean validarMovimientoReina(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        // La reina se mueve como la torre y como el alfil
        return validarMovimientoTorre(filaOrigen, colOrigen, filaDestino, colDestino) || validarMovimientoAlfil(filaOrigen, colOrigen, filaDestino, colDestino);
    }

    // Validación del movimiento del rey
    public boolean validarMovimientoRey(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        int deltaX = Math.abs(filaDestino - filaOrigen);
        int deltaY = Math.abs(colDestino - colOrigen);
        return deltaX <= 1 && deltaY <= 1;
    }

    // Verifica si hay obstáculos en el camino
    public boolean existeObstaculo(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        int direccionX = Integer.signum(filaDestino - filaOrigen);
        int direccionY = Integer.signum(colDestino - colOrigen);

        int x = filaOrigen + direccionX;
        int y = colOrigen + direccionY;

        while (x != filaDestino || y != colDestino) {
            if (fichas[x][y] != 0) {
                return true;
            }
            x += direccionX;
            y += direccionY;
        }
        return false;
    }
}