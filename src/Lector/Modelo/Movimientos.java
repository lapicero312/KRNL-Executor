package Lector.Modelo;
import Lector.Vista.Tablero;

import javax.swing.*;
import java.util.ArrayList;

import static Lector.Vista.Tablero.fichas;

public class Movimientos{
    private ArrayList<String> movimientos;
    private int totalRondas;
    private ArrayList<String> ronda;
    private int i;
    private Tablero tablero;
    private boolean partidaTerminada; // Nueva bandera para controlar el estado de la partida

    public Movimientos() {
    }

    public Movimientos(LeerPGN move, Tablero tablero) {
        this.movimientos = move.getMovimientos();
        this.tablero = tablero;
        this.ronda = new ArrayList<>();
        this.totalRondas = movimientos.size();
        this.separarMovimientos();
        this.i = 0;
        this.partidaTerminada = false; // Inicializamos la partida como activa
    }

    private void separarMovimientos() {
        for (String linea : movimientos) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            String[] partes = linea.split("\\d+\\.\\s?");
            if (partes.length > 1) {
                String[] jugadas = partes[1].trim().split(" ");
                if (jugadas.length > 0) ronda.add(jugadas[0]);  // Movimiento de blancas
                if (jugadas.length > 1) ronda.add(jugadas[1]);  // Movimiento de negras
            }
        }
    }

    public void aplicarMovimiento() {
        if (partidaTerminada) { // Evitar procesar si ya terminó la partida
            System.out.println("La partida ya ha terminado. No se procesarán más movimientos.");
            return;
        }

        if (i >= ronda.size()) {
            System.out.println("Fin de la partida. No hay más movimientos. Ronda final: " + i);
            JOptionPane.showMessageDialog(null, "FIN DE LA PARTIDA");
            partidaTerminada = true; // Marcamos que la partida ha terminado
            return;
        }

        String movimiento = ronda.get(i);

        if (movimiento == null || movimiento.isEmpty()) {
            System.out.println("Movimiento inválido o vacío en la ronda " + i + ": " + movimiento);
            return; // No incrementamos i para permitir revisar el problema.
        }

        System.out.println("Vamos por la ronda " + i);

        // Manejo del enroque
        if (movimiento.equals("O-O") || movimiento.equals("O-O-O")) {
            manejarEnroque(movimiento);
            i++; // Incrementa el índice solo si el enroque fue procesado
            return;
        }

        // Procesar movimiento estándar
        if (aplicarMovimientoEstandar(movimiento)) {
            i++; // Incrementar solo si el movimiento fue válido
        } else {
            System.out.println("No se pudo procesar el movimiento: " + movimiento);
        }
    }

    private boolean aplicarMovimientoEstandar(String movimiento) {
        movimiento = movimiento.replace("+", "").replace("#", ""); // Quitar notaciones especiales

        char piezaChar;
        String destino;
        String desambiguacion = null;

        if (Character.isUpperCase(movimiento.charAt(0))) {
            piezaChar = movimiento.charAt(0);
            movimiento = movimiento.substring(1);

            if (movimiento.length() > 2 && !movimiento.contains("x")) {
                desambiguacion = movimiento.substring(0, 1);
                destino = movimiento.substring(1);
            } else if (movimiento.contains("x")) {
                desambiguacion = movimiento.substring(0, movimiento.indexOf("x"));
                destino = movimiento.substring(movimiento.indexOf("x") + 1);
            } else {
                destino = movimiento;
            }
        } else {
            piezaChar = 'P';
            destino = movimiento.contains("x") ? movimiento.split("x")[1] : movimiento;
        }

        int colDestino = destino.charAt(0) - 'a';
        int rowDestino = 8 - Character.getNumericValue(destino.charAt(1));

        if (!coordenadasValidas(rowDestino, colDestino)) {
            System.out.println("Coordenadas fuera de límites: " + destino);
            return false;
        }

        int piezaNumero = (i % 2 == 0) ? convertirPiezaANumeroBlancas(piezaChar) : convertirPiezaANumeroNegras(piezaChar);

        if (movimiento.contains("x")) {
            manejarCaptura(piezaNumero, rowDestino, colDestino);
            return true;
        } else if (moverPiezaConDesambiguacion(piezaNumero, rowDestino, colDestino, desambiguacion)) {
            return true;
        }

        System.out.println("Movimiento inválido para la pieza: " + movimiento);
        return false;
    }

    private void manejarCaptura(int piezaNumero, int rowDestino, int colDestino) {
        int piezaEnDestino = fichas[rowDestino][colDestino];

        System.out.println("Intentando capturar en " + (char) (colDestino + 'a') + (8 - rowDestino));
        System.out.println("Pieza en destino: " + piezaEnDestino);

        if (piezaEnDestino == 0) {
            System.out.println("No hay pieza en el destino para capturar.");
            return;
        }
        if (esPiezaAliada(piezaEnDestino, piezaNumero)) {
            System.out.println("La pieza en el destino es aliada, no se puede capturar.");
            return;
        }

        if (moverPiezaConDesambiguacion(piezaNumero, rowDestino, colDestino, null)) {
            System.out.println("Pieza capturada correctamente en " + (char) (colDestino + 'a') + (8 - rowDestino));
            i++;
        } else {
            System.out.println("Movimiento de captura inválido.");
        }
    }

    private boolean moverPiezaConDesambiguacion(int piezaNumero, int rowDestino, int colDestino, String desambiguacion) {
        for (int fila = 0; fila < tablero.fichas.length; fila++) {
            for (int col = 0; col < tablero.fichas[fila].length; col++) {
                if (tablero.fichas[fila][col] == piezaNumero) {
                    if (desambiguacion != null) {
                        if (Character.isLetter(desambiguacion.charAt(0))) {
                            if (col != desambiguacion.charAt(0) - 'a') continue;
                        } else if (Character.isDigit(desambiguacion.charAt(0))) {
                            if (fila != 8 - Character.getNumericValue(desambiguacion.charAt(0))) continue;
                        }
                    }
                    if (tablero.validarMovimiento(fila, col, rowDestino, colDestino)) {
                        tablero.fichas[rowDestino][colDestino] = piezaNumero;
                        tablero.fichas[fila][col] = 0;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean esPiezaAliada(int piezaEnDestino, int piezaNumero) {
        // Determinar si ambas piezas son del mismo jugador
        return (piezaNumero % 2 == 0 && piezaEnDestino % 2 == 0);
    }

    private boolean coordenadasValidas(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void retrocederMovimiento() {
        if (i <= 0) {
            System.out.println("No hay movimientos anteriores.");
            return;
        }

        i--; // Retroceder al último movimiento aplicado
        String movimiento = ronda.get(i);

        // Revertir el movimiento
        System.out.println("Deshacer movimiento: " + movimiento);
        // Implementa la lógica para restaurar la posición del tablero
    }

    private void manejarEnroque(String enroque) {
        int rey = (i % 2 == 0) ? convertirPiezaANumeroBlancas('K') : convertirPiezaANumeroNegras('K');
        int torre = (i % 2 == 0) ? convertirPiezaANumeroBlancas('R') : convertirPiezaANumeroNegras('R');
        if (enroque.equals("O-O")) {
            enroqueCorto(rey, torre);  // Enroque corto
        } else if (enroque.equals("O-O-O")) {
            enroqueLargo(rey, torre);  // Enroque largo
        }
    }

    private void enroqueCorto(int rey, int torre) {
        int row = (i % 2 == 0) ? 7 : 0; // Blancas en la fila 7, negras en la fila 0
        // Validar posiciones iniciales
        if (fichas[row][4] != rey || fichas[row][7] != torre) {
            System.out.println("El enroque corto no es válido (piezas no en posiciones iniciales).");
            return;
        }

        // Actualizar el tablero
        fichas[row][4] = 0; // Eliminar rey de la posición inicial
        fichas[row][6] = rey; // Colocar rey en su nueva posición
        fichas[row][7] = 0; // Eliminar torre de la posición inicial
        fichas[row][5] = torre; // Colocar torre en su nueva posición

        // Depuración
        System.out.println("Estado del tablero después del enroque corto:");
        imprimirTablero();
    }

    private void imprimirTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(fichas[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void enroqueLargo(int rey, int torre) {
        int row = (i % 2 == 0) ? 7 : 0; // Blancas en la fila 7, negras en la fila 0

        if (fichas[row][4] != rey || fichas[row][0] != torre) {
            System.out.println("El enroque largo no es válido (piezas no en posiciones iniciales).");
            return;
        }

        fichas[row][4] = 0;
        fichas[row][2] = rey;
        fichas[row][0] = 0;
        fichas[row][3] = torre;

        System.out.println("Estado del tablero después del enroque largo:");
        imprimirTablero();
    }

    private boolean esReyEnJaques(int row, int col) {
        // Verifica si el rey está en jaque en la casilla dada
        return tablero.esReyEnJaques(row, col);
    }

    public int convertirPiezaANumeroBlancas(char pieza) {
        switch (pieza) {
            case 'K': return 30;
            case 'Q': return 15;
            case 'R': return 3;
            case 'B': return 7;
            case 'N': return 5;
            case 'P': return 1;
            default: return 0;
        }
    }

    public int convertirPiezaANumeroNegras(char pieza) {
        switch (pieza) {
            case 'K': return 20;
            case 'Q': return 10;
            case 'R': return 4;
            case 'B': return 8;
            case 'N': return 6;
            case 'P': return 2;
            default: return 0;
        }
    }

    public ArrayList<String> getRonda() {
        return ronda;
    }

    public void setIndice(int i){
        this.i=i;
    }
    public int  getIndice(){
        return i;
    }
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }
}