package Pieces;

import src.Board;

public class Knight extends Piece {
    public Knight(Boolean white, int x, int y) {
        super("knight", white, x, y);
    }

// TODO as each piece knows the board we can check for the destination piece inside the can move method

    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            if ((Math.abs(distX) == 2) && (Math.abs(distY) == 1) && pieceAtDest != null && pieceAtDest.getWhite() != this.getWhite()) {
                return true;
            }
            else if ((Math.abs(distX) == 1) && (Math.abs(distY) == 2) && pieceAtDest != null && pieceAtDest.getWhite() != this.getWhite()) {
                return true;
            }
            else {
                return false;
            }

        } else return false;
    }
}
