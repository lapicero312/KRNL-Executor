package escritor.Pieces;
import escritor.main.Board;

import java.awt.image.BufferedImage;
public class Bishop  extends Piece{
    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Bishop";
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs(this.col -col)==Math.abs(this.row -row);
    }

   public boolean moveCollidesWithPiece(int col, int row){
        //upleft

        if(this.col > col && this.row> row)
            for(int i=1;i<Math.abs(this.col - col);i++)
                if(board.getPiece(this.col-i, this.row-i)!=null)
                    return true;
       //upRight

       if(this.col < col && this.row> row)
           for(int i=1;i<Math.abs(this.col - col);i++)
               if(board.getPiece(this.col+i, this.row-i)!=null)
                   return true;

       //downLeft

       if(this.col > col && this.row< row)
           for(int i=1;i<Math.abs(this.col - col);i++)
               if(board.getPiece(this.col-i, this.row+i)!=null)
                   return true;
       //downRight

       if(this.col < col && this.row< row)
           for(int i=1;i<Math.abs(this.col - col);i++)
               if(board.getPiece(this.col+i, this.row+i)!=null)
                   return true;
       return false;
   }
}
