package Pieces;

import src.Board;

public class King extends Piece {
    public King(Boolean white, int x, int y) {
        super("king", white, x, y);
    }



    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            int euclDist = (int) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY,2));
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if (euclDist == 1){
                if (pieceAtDest != null && pieceAtDest.getWhite() == this.getWhite()) return false;
                else return true;
            }
            else {
                return false;
            }
        } else return false;
    }
}
