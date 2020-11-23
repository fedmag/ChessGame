package chess.pieces;


import chess.src.Board;
import chess.src.Cell;
import chess.src.GameFlow;

public class Pawn extends Piece {

    private boolean movedAlready = false;

    public Pawn(Boolean white, int x, int y) {
        super("pawn", white, x, y);
    }

    /**
     * The pawn can be promoted to a new piece, checks if that's the case
     * @return if the pawn must be promoted
     */
    public boolean checkPromotion() {
        if (this.getWhite() && this.getxPos() == 0) {
//            System.out.println("this pawn is getting promoted!");
            return true;
        } else if (!this.getWhite() && this.getxPos() == 7) {
//            System.out.println("this pawn is getting promoted!");
            return true;
        }
        return false;
    }


    @Override //FIXME in some cases the pawn it s able to move back
    public boolean canMove(int destX, int destY, Board board) {
        // if the move is within the board -> is legit
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            int absDistX = Math.abs(distX);
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if (this.movedAlready) { // not the first move of this pawn
                // in order to eat diagonally we check the pieces at the left and at the right
                Piece leftPiece = this.getyPos() - 1 > 0 ? board.pieceAtDest(destX, this.getyPos() - 1) : null;
                Piece rightPiece = this.getyPos() + 1 < 8 ? board.pieceAtDest(destX, this.getyPos() + 1) : null;
                // Pawn only moves forward (if the cell is empty) or diagonally to eat
                if (this.getWhite()) { //if white
                    if (enPassantAvailable(destX, destY)) return enPassantAvailable(destX, destY); //checking for enpassant
                    if (distX == 1 && distY == 0 && pieceAtDest == null) { // base movement
                        return true;
                    } else if (leftPiece != null && leftPiece.getWhite() != this.getWhite() // there s an opponent piece on the upper-left square
                            && pieceAtDest == leftPiece // you want to move on that square
                            && (distX == 1 && distY == 1)) { // and you're still respecting the base movement range
                        return true;
                    } else if (rightPiece != null && rightPiece.getWhite() != this.getWhite() // there s an opponent piece on the upper-right square
                            && pieceAtDest == rightPiece // you want to move on that square
                            && (distX == 1 && distY == -1)) { // and you're still respecting the base movement range
                        return true;
                    } else return false;
                } else { // if black
                    if (enPassantAvailable(destX, destY)) return enPassantAvailable(destX, destY); //checking for enpassant
                    if (distX == -1 && distY == 0 && pieceAtDest == null) { // base movement
                        return true;
                    } else if (leftPiece != null && leftPiece.getWhite() != this.getWhite() // there s an opponent piece on the lower-left square
                            && pieceAtDest == leftPiece // you want to move on that square
                            && (distX == -1 && distY == 1)) { // and you're still respecting the base movement range
                        return true;
                    } else if (rightPiece != null && rightPiece.getWhite() != this.getWhite() // there s an opponent piece on the lower-right square
                            && pieceAtDest == rightPiece // you want to move on that square
                            && (distX == -1 && distY == -1)) { // and you're still respecting the base movement range
                        return true;
                    } else return false;
                }
            } else { // first move of the match for that pawn // FIXME the pawn must me able to eat diagonlly even tho is its first move
                if ((absDistX == 2 && distY == 0) || ((absDistX == 1 && distY == 0))) { // normal move can still be performed
                    if (this.getWhite() & destX < this.getxPos()) return true;
                    else if (!this.getWhite() & destX > this.getxPos()) return true;
                }
            }
        }
        // in general the move is not possible
        return false;
    }

    /**
     * Checks if it's possible to perform an en-passant
     * @param destX new x location
     * @param destY new y location
     * @return if it's possible to perform an en-passant
     */
    public boolean enPassantAvailable(int destX, int destY) {
        if ((this.getWhite() && this.getxPos() == 3) || (!this.getWhite() && this.getxPos() == 4)) { // white side
            String[] cells = GameFlow.getLastMoveCells().split(" -> ");
            String fromCell = cells[0];
            String toCell = cells[1];
            if (fromCell.charAt(0) == toCell.charAt(0)) {
                int x = Integer.parseInt(String.valueOf(fromCell.charAt(1)));
                int y = Integer.parseInt(String.valueOf(toCell.charAt(1)));
                if (this.getWhite()) return Math.abs(x - y) == 2 && destX == this.getxPos() - 1 && destY == Cell.letterToNumb(fromCell.charAt(0));
                else return Math.abs(x - y) == 2 && destX == this.getxPos() + 1 && destY == Cell.letterToNumb(fromCell.charAt(0));
            }
        }
        return false;
    }

    // the method has to be overrided because we want to store whether the piece was actually moved or not.
    // This boolean changes only when the move is actually performed. This is important because we check the canMove()
    // to color all the cells.
    @Override
    public void move(int destX, int destY, Board board) {
        super.move(destX, destY, board);
        if(!this.movedAlready)this.movedAlready = true;
    }
}