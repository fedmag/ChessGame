package Pieces;

import src.Board;

import java.util.ArrayList;

public class King extends Piece {

     private boolean castlingDone = false;

     //TODO there is probably a better way of implementing this
     ArrayList<Boolean> emptyCellsLeft = new ArrayList<>();
     ArrayList<Boolean> emptyCellsRight = new ArrayList<>();


    public King(Boolean white, int x, int y) {
        super("king", white, x, y);
    }

    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            int euclDist = (int) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY,2));
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if (euclDist == 1){
                if (pieceAtDest != null && pieceAtDest.getWhite() == this.getWhite()) return false;
                else return true;
            }
            // special check for castling
            else if (pieceAtDest instanceof Rook && pieceAtDest.getWhite() == this.getWhite()
                    && !this.castlingDone && !((Rook) pieceAtDest).isCastlingDone()) {
                // need to check that the space between them is empty
                int dist = this.getyPos() - pieceAtDest.getyPos();
                if (dist > 0) { // dist > 0 means we gotta look at the left of the king
                    //checking left
                    for (int i = 1; i < dist; i++) {
                        if (board.cellAtIsEmpty(this.getxPos(), this.getyPos() - i)) this.emptyCellsLeft.add(true);
                        if (this.emptyCellsLeft.size() == dist) return true;
                    }
                } else { // dist < 0 we look at the right
                    dist = Math.abs(dist);
                    for (int i = 1; i < dist; i++) {
                        if (board.cellAtIsEmpty(this.getxPos(), this.getyPos() + i)) this.emptyCellsLeft.add(true);
                        if (this.emptyCellsLeft.size() == dist) return true;
                    }
                }
            }
        } return false;
    }

    public boolean isCastlingDone() {
        return castlingDone;
    }

    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }
}
