package Pieces;

public class Pawn extends Piece {
    public Pawn(Boolean white, int x, int y) {
        super("pawn ", white, x, y);
    }


    @Override
    public boolean canMove(int destX, int destY, boolean specialMove) {
        if (legitMove(destX, destY)) {
            if (!specialMove) {
                int distX = this.getxPos() - destX;
                int distY = this.getyPos() - destY;
                // Pawn only moves forward
                if ((Math.abs(distX) == 1) && distY == 0) {
                    return true;
                } else return false;
            } else {
                int distX = this.getxPos() - destX;
                int distY = this.getyPos() - destY;
                // Pawn only moves forward
                if ((Math.abs(distX) == 2) && distY == 0) {
                    return true;
                } else return false;
            }
        }
        else {
            System.err.println("This piece cannot move like this");
            return false;
        }
    }
}