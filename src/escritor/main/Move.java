package escritor.main;
import escritor.Pieces.*;

public class Move {
    public int oldCol;
    public int  oldRow;
    public int newCol;
    public int newRow;
    public boolean isCapture;
    public Piece piece;
    public  Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow){
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;
        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);

        if (capture != null){
            isCapture = true;
        }else{
            isCapture = false;
        }
    }
}
