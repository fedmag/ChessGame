package chess.pieces;

import chess.src.Board;

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
        this.emptyCellsLeft.clear();
        this.emptyCellsRight.clear();
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
                    }
                    return (this.emptyCellsLeft.size() + 1) == dist;
                } else { // dist < 0 we look at the right
                    dist = Math.abs(dist);
                    for (int i = 1; i < dist; i++) {
                        if (board.cellAtIsEmpty(this.getxPos(), this.getyPos() + i)) this.emptyCellsLeft.add(true);
                    }
                    return (this.emptyCellsLeft.size() + 1) == dist;
                }
            }
        } return false;
    }

    /**
     * Checks if castling was performed
     * @return if castling was performed
     */
    public boolean isCastlingDone() {
        return castlingDone;
    }

    /**
     * Sets the castling
     * @param castlingDone boolean, indicates if the castling was already performed
     */
    public void setCastlingDone(boolean castlingDone) {
        this.castlingDone = castlingDone;
    }

    @Override
    public void move(int destX, int destY, Board board) {
        super.move(destX, destY, board);
        this.castlingDone = true;
    }
}
