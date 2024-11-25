package escritor.main;

import escritor.Control.GameMetadata;
import escritor.Control.PGNWriter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainEscritor {

    public static void iniciar() {
        // Crear una nueva ventana para la aplicación
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);

        // Recopilar metadatos del usuario
        GameMetadata metadata = askForMetadata(frame);
        if (metadata == null) {
            System.out.println("El usuario canceló la entrada de metadatos.");
            return;
        }

        // Crear el tablero de ajedrez
        Board board = new Board();

        // Agregar el tablero a la ventana
        frame.add(board);

        // Crear barra de menú con opción para guardar el PGN
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem savePGNItem = new JMenuItem("Guardar PGN");

        // Acción para guardar el PGN cuando el usuario haga clic
        savePGNItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar archivo PGN");
            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                PGNWriter pgnWriter = new PGNWriter(metadata, board.movementRecord);
                pgnWriter.writeToFile(file.getAbsolutePath());
            }
        });

        // Agregar elementos al menú
        fileMenu.add(savePGNItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Mostrar la ventana
        frame.setVisible(true);
    }

    private static GameMetadata askForMetadata(JFrame frame) {
        // Crear el formulario de entrada de metadatos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JTextField eventField = new JTextField("Evento");
        JTextField siteField = new JTextField("Ubicación");
        JTextField dateField = new JTextField("2024.11.22");
        JTextField roundField = new JTextField("1");
        JTextField whitePlayerField = new JTextField("Jugador Blanco");
        JTextField blackPlayerField = new JTextField("Jugador Negro");
        JTextField resultField = new JTextField("*");

        // Añadir etiquetas y campos al panel
        panel.add(new JLabel("Evento:"));
        panel.add(eventField);
        panel.add(new JLabel("Ubicación:"));
        panel.add(siteField);
        panel.add(new JLabel("Fecha:"));
        panel.add(dateField);
        panel.add(new JLabel("Ronda:"));
        panel.add(roundField);
        panel.add(new JLabel("Jugador Blanco:"));
        panel.add(whitePlayerField);
        panel.add(new JLabel("Jugador Negro:"));
        panel.add(blackPlayerField);
        panel.add(new JLabel("Resultado:"));
        panel.add(resultField);

        // Mostrar un cuadro de diálogo
        int option = JOptionPane.showConfirmDialog(frame, panel, "Ingrese los metadatos del juego", JOptionPane.OK_CANCEL_OPTION);

        // Si se presionó OK, devolver los metadatos
        if (option == JOptionPane.OK_OPTION) {
            return new GameMetadata(
                    eventField.getText(),
                    siteField.getText(),
                    dateField.getText(),
                    roundField.getText(),
                    whitePlayerField.getText(),
                    blackPlayerField.getText(),
                    resultField.getText()
            );
        }
        return null;
    }
}
