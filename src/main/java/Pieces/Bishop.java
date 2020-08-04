package Pieces;

public class Bishop extends Piece{
    public Bishop(Boolean white, int x, int y) {
        super("bishop", white, x, y);
    }


    @Override
    public boolean canMove(int destX, int destY, boolean specialMove) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            if (Math.abs(distX) == Math.abs(distY)) {
                return true;
            } else {
                System.err.println("This piece cannot move like this");
                return false;
            }
        } else return false;
    }
}
