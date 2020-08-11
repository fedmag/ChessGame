package Pieces;

public class Pawn extends Piece {
    public Pawn(Boolean white, int x, int y) {
        super("pawn ", white, x, y);
    }


    @Override
    public boolean canMove(int destX, int destY, boolean specialMove) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            if (!specialMove) {
                // Pawn only moves forward
                return (Math.abs(distX) == 1) && distY == 0;
            } else {
                // Pawn only moves forward
                return (Math.abs(distX) == 2) && distY == 0 || (Math.abs(distX) == 1) && distY == 0; // normal move can still be performed
            }
        }
        else {
            System.err.println("This piece cannot move like this");
            return false;
        }
    }
}