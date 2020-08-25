package Pieces;

import src.Board;

public class Queen extends Piece {
    public Queen(Boolean white, int x, int y) {
        super("queen", white, x, y);
    }


    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            // moving on a diagonal
            if ((Math.abs(distX) == Math.abs(distY))||
                    // moving vertically or horizontally
                    ((distX == 0 && distY !=0) ) ||
                    (distX != 0 && distY ==0)) {
                if (pieceAtDest != null && this.getWhite() != pieceAtDest.getWhite()) return true;
                else if (pieceAtDest == null) return true;
            } else return false;
        } else return false;
        return false;
    }
}
