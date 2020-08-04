package Pieces;

public class Rook extends Piece {
    public Rook(Boolean white, int x, int y) {
        super("rook", white, x, y);
    }

    @Override
    public boolean canMove(int destX, int destY) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;

            if ((distX ==0 && distY !=0) || (distX != 0 && distY ==0)) {
                return true;
            }
            else {
                System.err.println("This piece cannot move like this");
                return false;
            }
        } else return false;
    }
}

