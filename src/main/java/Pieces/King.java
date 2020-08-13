package Pieces;

import src.Board;

public class King extends Piece {
    public King(Boolean white, int x, int y) {
        super("king", white, x, y);
    }



    @Override
    public boolean canMove(int destX, int destY, boolean specialMove, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            int euclDist = (int) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY,2));
            if (euclDist == 1) return true;
            else {
                System.err.println("This piece cannot move like this");
                return false;
            }
        } else return false;
    }
}
