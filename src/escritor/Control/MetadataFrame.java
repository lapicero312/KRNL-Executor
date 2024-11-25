package escritor.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetadataFrame extends JFrame {
    private GameMetadata metadata;

    // Form fields
    private static JTextField eventField;
    private static JTextField siteField;
    private static JTextField dateField;
    private static JTextField roundField;
    private static JTextField whitePlayerField;
    private static JTextField blackPlayerField;
    private static JTextField resultField;
    private JTextArea metadataDisplay;

    public MetadataFrame(GameMetadata metadata) {
        this.metadata = metadata;
        initUI();
    }

    private void initUI() {
        setTitle("Chess Game Metadata");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 2, 10, 10));

        // Add labels and fields
        formPanel.add(new JLabel("Event:"));
        eventField = new JTextField(metadata.getEvent());
        formPanel.add(eventField);

        formPanel.add(new JLabel("Site:"));
        siteField = new JTextField(metadata.getSite());
        formPanel.add(siteField);

        formPanel.add(new JLabel("Date (YYYY.MM.DD):"));
        dateField = new JTextField(metadata.getDate());
        formPanel.add(dateField);

        formPanel.add(new JLabel("Round:"));
        roundField = new JTextField(metadata.getRound());
        formPanel.add(roundField);

        formPanel.add(new JLabel("White Player:"));
        whitePlayerField = new JTextField(metadata.getWhitePlayer());
        formPanel.add(whitePlayerField);

        formPanel.add(new JLabel("Black Player:"));
        blackPlayerField = new JTextField(metadata.getBlackPlayer());
        formPanel.add(blackPlayerField);

        formPanel.add(new JLabel("Result:"));
        resultField = new JTextField(metadata.getResult());
        formPanel.add(resultField);

        // Buttons
        JButton saveButton = new JButton("Save");
        JButton displayButton = new JButton("Display Metadata");

        formPanel.add(saveButton);
        formPanel.add(displayButton);

        // Metadata display area
        metadataDisplay = new JTextArea();
        metadataDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(metadataDisplay);

        // Add components to frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Actions
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMetadata();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMetadata();
            }
        });
    }

    private void saveMetadata() {
        metadata.setEvent(eventField.getText());
        metadata.setSite(siteField.getText());
        metadata.setDate(dateField.getText());
        metadata.setRound(roundField.getText());
        metadata.setWhitePlayer(whitePlayerField.getText());
        metadata.setBlackPlayer(blackPlayerField.getText());
        metadata.setResult(resultField.getText());
        JOptionPane.showMessageDialog(this, "Metadata saved successfully!");
    }

    private void displayMetadata() {
        metadataDisplay.setText(metadata.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMetadata metadata = new GameMetadata(eventField.getText(), siteField.getText(), dateField.getText(), roundField.getText(), whitePlayerField.getText(), blackPlayerField.getText(), resultField.getText());
            MetadataFrame frame = new MetadataFrame(metadata);
            frame.setVisible(true);
        });
    }
}
