package escritor.Control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PGNWriter {

    private GameMetadata metadata;
    private MovementRecord movementRecord;

    public PGNWriter(GameMetadata metadata, MovementRecord movementRecord) {
        this.metadata = metadata;
        this.movementRecord = movementRecord;
    }

    /**
     * Write the PGN data to a file.
     * @param fileName Name of the file to save the PGN.
     */
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write metadata
            writer.write(metadata.toString());
            writer.newLine();
            writer.newLine();

            // Write moves
            String pgnMoves = movementRecord.getPGN();
            if (pgnMoves.isEmpty()) {
                System.out.println("No moves to write."); // Debugging output
            } else {
                writer.write(pgnMoves);
                writer.newLine();
            }

            System.out.println("PGN file saved successfully as: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
