package escritor.Control;

import escritor.Pieces.*;
import java.util.ArrayList;
import java.util.List;
import escritor.main.Move;
import escritor.main.Board;

public class MovementRecord {
    private final Board board; // Reference to the game board
    public List<String> moves; // List to store moves in PGN format
    private int moveCounter; // Counter for numbering moves

    public MovementRecord(Board board) {
        this.board = board; // Use the existing board and its checkScanner
        this.moves = new ArrayList<>();
        this.moveCounter = 1;
    }

    /**
     * Record a move in PGN format, including captures, checks, and castling.
     *
     * @param move The move being made.
     */
    public void recordMove(Move move) {
        String pgnMove = convertToPGN(move);
        System.out.println("Recording move: " + pgnMove); // Debugging output

        // Append '+' for check or '#' for checkmate
        if (board.checkScanner.isKingChecked(move)) {
            Piece opponentKing = board.findKing(!move.piece.isWhite);
            if (opponentKing != null && board.checkScanner.isGameOver(opponentKing)) {
                pgnMove += "#"; // Checkmate
            } else {
                pgnMove += "+"; // Check
            }
        }

        // Add the move to the list
        if (move.piece.isWhite) {
            moves.add(moveCounter + ". " + pgnMove);
        } else {
            moves.set(moves.size() - 1, moves.get(moves.size() - 1) + " " + pgnMove);
            moveCounter++;
        }
    }

    /**
     * Convert a move to PGN format.
     */
    private String convertToPGN(Move move) {
        // Handle castling
        if (isKingsideCastling(move)) {
            return "O-O";
        } else if (isQueensideCastling(move)) {
            return "O-O-O";
        }

        // Handle regular moves and captures
        String pieceNotation = getPieceNotation(move.piece);
        String columnLetter = String.valueOf((char) ('a' + move.newCol)); // Convert column to letter
        String captureSymbol = (move.capture != null) ? "x" : ""; // Include 'x' if a piece is captured

        // Special case for pawn captures: add the starting column
        if (move.piece.name.equalsIgnoreCase("pawn") && move.capture != null) {
            String startColumnLetter = String.valueOf((char) ('a' + move.oldCol));
            return startColumnLetter + captureSymbol + columnLetter + (8 - move.newRow); // Example: "exd5"
        }

        return pieceNotation + captureSymbol + columnLetter + (8 - move.newRow); // Example: "Nxe4"
    }

    private boolean isKingsideCastling(Move move) {
        return move.piece.name.equalsIgnoreCase("king") &&
                Math.abs(move.newCol - move.oldCol) == 2 &&
                move.newCol > move.oldCol;
    }

    private boolean isQueensideCastling(Move move) {
        return move.piece.name.equalsIgnoreCase("king") &&
                Math.abs(move.newCol - move.oldCol) == 2 &&
                move.newCol < move.oldCol;
    }

    private String getPieceNotation(Piece piece) {
        switch (piece.name.toLowerCase()) {
            case "king":
                return "K";
            case "queen":
                return "Q";
            case "rook":
                return "R";
            case "bishop":
                return "B";
            case "knight":
                return "N";
            default: // Pawns have no notation in PGN
                return "";
        }
    }

    /**
     * Get all moves in PGN format as a string.
     */
    public String getPGN() {
        if (moves.isEmpty()) {
            System.out.println("No moves recorded yet."); // Debugging output
        } else {
            System.out.println("Moves in PGN format: " + moves); // Debugging output
        }
        StringBuilder pgn = new StringBuilder();
        for (String move : moves) {
            pgn.append(move).append(" ");
        }
        return pgn.toString().trim();
    }

    /**
     * Display the PGN moves in the console.
     */
    public void displayMoves() {
        System.out.println(getPGN());
    }
}
