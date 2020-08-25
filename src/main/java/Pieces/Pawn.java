package Pieces;


import src.Board;

import javax.swing.*;

public class Pawn extends Piece {

    private boolean actuallyMovedAlready = false;

    public Pawn(Boolean white, int x, int y) {
        super("pawn", white, x, y);
    }

    public boolean checkPromotion() {
        if(this.getWhite() && this.getxPos() == 0) {
            System.out.println("this pawn is getting promoted!");
            return true;
        } else if (!this.getWhite() && this.getxPos() == 7) {
            System.out.println("this pawn is getting promoted!");
            return true;
        }
        return false;
    }

    //FIXME:
    //  - adding the firstMove to each pawn causes them to always be able to move two squares

    @Override
    public boolean canMove(int destX, int destY, Board board) {
        // if the move is within the board -> is legit
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            int absDistX = Math.abs(distX);
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if (this.actuallyMovedAlready) { // not the first move of this pawn
                // in order to eat diagonally we check the pieces at the left and at the right
                Piece leftPiece = this.getyPos() - 1 > 0 ? board.pieceAtDest(destX, this.getyPos() - 1) : null;
                Piece rightPiece = this.getyPos() + 1 < 8 ? board.pieceAtDest(destX, this.getyPos() + 1) : null;
                // Pawn only moves forward (if the cell is empty) or diagonally to eat
                if (this.getWhite()) { //if white
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
                    } else {
//                        System.out.println("white piece not able to move");
                        return false;
                    }
                } else { // if black
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
            } else { // first move of the match for that pawn
                if ((absDistX == 2 && distY == 0) || ((absDistX == 1 && distY == 0))) { // normal move can still be performed
                    return true;
                }
            }
        // not a legit move
        } else {
            return false;
        }
    // in general the move is not possible
    return false;
    }

    // the method has to be overrided because we want to store whether the piece was actually moved or not.
    // This boolean changes only when the move is actually performed. This is important because we check the canMove()
    // to color all the cells.
    @Override
    public void move(int destX, int destY, Board board) {
        super.move(destX, destY, board);
        this.actuallyMovedAlready = true;
    }
}