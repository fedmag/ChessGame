package Pieces;

import src.Board;

public class Knight extends Piece {
    public Knight(Boolean white, int x, int y) {
        super("knight", white, x, y);
    }



    @Override
    public boolean canMove(int destX, int destY, boolean specialMove, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            System.out.println("distX: " + distX + "distY: " + distY);
            if ((Math.abs(distX) == 2) && (Math.abs(distY) == 1)) {
                this.summary();
                return true;
            }
            else if ((Math.abs(distX) == 1) && (Math.abs(distY) == 2)) {
                this.summary();
                return true;
            }
            else {
                return false;
            }

        } else return false;
    }
}
