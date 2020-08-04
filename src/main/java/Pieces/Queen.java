package Pieces;

public class Queen extends Piece {
    public Queen(Boolean white, int x, int y) {
        super("queen", white, x, y);
    }

    @Override
    public void move(int destX, int destY) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            // moving on a diagonal
            if ((Math.abs(distX) == Math.abs(distY))||
                    // moving vertically or horizontally
                    ((distX == 0 && distY !=0) ) ||
                    (distX != 0 && distY ==0)) {
                this.setxPos(destX);
                this.setyPos(destY);
            }
        }
        this.summary();
    }

    @Override
    public boolean canMove(int destX, int destY) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            // moving on a diagonal
            if ((Math.abs(distX) == Math.abs(distY))||
                    // moving vertically or horizontally
                    ((distX == 0 && distY !=0) ) ||
                    (distX != 0 && distY ==0)) {
                return true;
            } else return false;
        } else return false;
    }
}
