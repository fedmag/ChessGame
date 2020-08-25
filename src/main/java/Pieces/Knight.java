package Pieces;

import src.Board;

public class Knight extends Piece {
    public Knight(Boolean white, int x, int y) {
        super("knight", white, x, y);
    }

    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            boolean correctDistance = (Math.abs(distX) == 2) && (Math.abs(distY) == 1) || (Math.abs(distX) == 1) && (Math.abs(distY) == 2);
            if (pieceAtDest == null) {
                if (correctDistance) return true;
            } else {
                if (correctDistance) {
                    if (pieceAtDest.getWhite() != this.getWhite()) return true;
                }
            }
        }
        return false;
    }
}
