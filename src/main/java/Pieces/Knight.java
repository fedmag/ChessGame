package Pieces;

public class Knight extends Piece {
    public Knight(Boolean white, int x, int y) {
        super("knight", white, x, y);
    }



    @Override
    public boolean canMove(int destX, int destY) {
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
                System.err.println("This piece cannot move like this");
                return false;
            }

        } else return false;
    }
}
