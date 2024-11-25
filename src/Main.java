import Lector.controlador.Controlador;
import escritor.main.MainEscritor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JPanel mainPanel; // Panel principal que contendrá los componentes

    public Main() {
        // Configurar el JFrame
        setTitle("Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800); // Tamaño del frame
        setLocationRelativeTo(null); // Centra el frame en la pantalla

        // Crear panel principal con imagen de fondo
        mainPanel = new FondoPanel();
        mainPanel.setLayout(null); // Usar diseño absoluto
        getContentPane().add(mainPanel);

        // Crear botón "Jugar"
        JButton playButton = new JButton("Jugar");
        playButton.setBounds(960, 500, 140, 40); // Posición y tamaño del botón
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Inicializar la ventana de MainEscritor
                MainEscritor.iniciar();
            }
        });
        mainPanel.add(playButton);

        // Crear botón "Leer Partida"
        JButton readButton = new JButton("Leer Partida");
        readButton.setBounds(960, 400, 140, 40); // Posición y tamaño del botón
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reemplazar el contenido del JFrame con el Controlador
                cambiarAPanelControlador();
            }
        });
        mainPanel.add(readButton);

        // Hacer visible el frame
        setVisible(true);
    }

    private void cambiarAPanelJuego() {
        // Quitar todo lo que haya en el panel principal
        getContentPane().removeAll();

        // Crear instancia del Controlador
        Controlador controlador = new Controlador();
        controlador.setBounds(0, 0, 1200, 800);

        // Agregar el Controlador al JFrame
        getContentPane().add(controlador);

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }


    private void cambiarAPanelControlador() {
        // Quitar todo lo que haya en el panel principal
        getContentPane().removeAll();

        // Crear instancia del Controlador
        Controlador controlador = new Controlador();
        controlador.setBounds(0, 0, 1200, 800);

        // Agregar el Controlador al JFrame
        getContentPane().add(controlador);

        // Actualizar la interfaz gráfica
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Iniciar la aplicación
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }

    // Clase personalizada para mostrar una imagen de fondo
    private static class FondoPanel extends JPanel {
        private Image backgroundImage;

        public FondoPanel() {
            try {

                // Cargar la imagen desde el archivo
                backgroundImage = new ImageIcon("src/Lector/recursos/imagenes/fondochess.png").getImage();
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Dibujar la imagen escalada al tamaño del panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g.setColor(Color.white);
                g.setFont(new Font("serif", Font.BOLD, 26));
                g.drawString("ELIGE UNA OPCION: ", 910, 80);
            }
        }
    }
}