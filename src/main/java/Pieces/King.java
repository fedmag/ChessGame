package Pieces;

public class King extends Piece {
    public King(Boolean white, int x, int y) {
        super("king", white, x, y);
    }



    @Override
    public boolean canMove(int destX, int destY, boolean specialMove) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            if ((distX == 1 || distX == -1) && (distY == 1 || distY == -1)) {
                return true;
            } else if ((distX == 1 || distX == -1) && destY == 0) {
                return true;
            } else if ((distY == 1 || distY == -1) && destX == 0) {
                return true;
            } else {
                System.err.println("This piece cannot move like this");
                return false;
            }
        } else return false;
    }
}
