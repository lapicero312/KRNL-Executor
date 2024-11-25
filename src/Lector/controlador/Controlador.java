package Lector.controlador;
import Lector.Modelo.LeerPGN;
import Lector.Modelo.Movimientos;
import Lector.Vista.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Controlador extends JPanel implements KeyListener, ActionListener {
    private Timer timer;
    private Tablero tablero;
    private JTextArea campoTexto;
    private JButton guardarTextoButton;
    private JButton siguienteMov;
    public LeerPGN lector;
    public Movimientos mov;
    public JButton reiniciarTablero;
    public boolean puedeMover;

    public Controlador() {
        lector = new LeerPGN("src/Lector/recursos/partida.txt");
        puedeMover = false;
        reiniciarTablero = new JButton();
        tablero = new Tablero();
        mov = new Movimientos(lector, tablero);
        setFocusable(true);

        // Configura el Timer
        timer = new Timer(5, this);
        timer.start();

        // Configura el Layout como null para posición absoluta
        setLayout(null);

        // Crea el área de texto y agrega el texto PGN
        campoTexto = new JTextArea();
        campoTexto.setLineWrap(true);
        campoTexto.setWrapStyleWord(true);
        campoTexto.setEditable(true);
        campoTexto.setBounds(800, 40, 400, 500);

        // Agrega un JScrollPane para permitir desplazamiento en el área de texto
        JScrollPane scrollPane = new JScrollPane(campoTexto);
        scrollPane.setBounds(800, 40, 400, 500);
        add(scrollPane);

        // Botón para guardar el texto en un archivo
        guardarTextoButton = new JButton("Guardar Texto");
        guardarTextoButton.setBounds(900, 540, 200, 30);
        guardarTextoButton.addActionListener(this);
        add(guardarTextoButton);

        // Botón para avanzar al siguiente movimiento
        siguienteMov = new JButton("SIGUIENTE MOVIMIENTO");
        siguienteMov.setBounds(900, 600, 200, 30);
        add(siguienteMov);
        siguienteMov.addActionListener(this);

        // Botón para reiniciar el tablero
        reiniciarTablero = new JButton("REINICIAR TABLERO");
        reiniciarTablero.setBounds(880, 660, 230, 30);
        add(reiniciarTablero);
        reiniciarTablero.addActionListener(this);

        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = mov.getIndice();
        tablero.dibujarTablero(g);
        tablero.dibujarFichas(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("serif", Font.BOLD, 18));
        g.drawString("INGRESA EL TEXTO EN FORMATO PGN", 815, 20);
        g.drawString("RONDA: " + i / 2, 815, 740);
    }

    public void reiniciarFichas() {
        tablero.inicializarTablero();
        mov = new Movimientos(lector, tablero); // Reinicia los movimientos también
        puedeMover = true;
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guardarTextoButton) {
            try {
                guardarPartida();
                leerPartida();
                puedeMover = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == siguienteMov) {
            if (puedeMover) {
                if (mov.isPartidaTerminada()) {
                    JOptionPane.showMessageDialog(this, "La partida ya ha terminado. No hay más movimientos.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    puedeMover = false;
                    siguienteMov.setEnabled(false);
                } else {
                    try {
                        mov.aplicarMovimiento();
                        repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al aplicar el movimiento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, escribe un texto en formato PGN y guárdalo antes de avanzar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getSource() == reiniciarTablero) {
            reiniciarFichas();
            siguienteMov.setEnabled(true);
        }
    }

    public void guardarPartida() {
        String texto = campoTexto.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Lector/recursos/partida.txt"))) {
            writer.write(texto);
            writer.flush();
            JOptionPane.showMessageDialog(this, "Texto guardado en partida.txt");
            reiniciarFichas();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el texto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void leerPartida() {
        lector.leerMovimientos();
        lector.leerMetadata();
        mov = new Movimientos(lector, tablero);
        if (lector.getMovimientos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron movimientos en el archivo PGN.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}