package Pieces;


import src.Board;

import javax.swing.*;

public class Pawn extends Piece {
    public Pawn(Boolean white, int x, int y) {
        super("pawn ", white, x, y);
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

    @Override
    public boolean canMove(int destX, int destY, boolean specialMove, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            if (!specialMove) {
                Piece leftPiece = this.getyPos()-1 > 0 ? board.pieceAtDest(destX, this.getyPos() - 1): null;
                Piece rightPiece = this.getyPos()+ 1 < 8 ?board.pieceAtDest(destX, this.getyPos() + 1): null;
                // Pawn only moves forward or diagonally to eat
                if (this.getWhite()) { //if white
                    if(distX == 1 && distY == 0) return true;
                    else if (leftPiece != null && leftPiece.getWhite() != this.getWhite()) return true;
                    else if (rightPiece != null && rightPiece.getWhite()!= this.getWhite()) return true;
                    else return false;
                }
                else { // if black
                    if (distX == -1 && distY == 0) return true;
                    else if (leftPiece != null && leftPiece.getWhite() != this.getWhite()) return true;
                    else if (rightPiece != null && rightPiece.getWhite()!= this.getWhite()) return true;
                    else return false;
                }
            } else { // first move of the match
                return (Math.abs(distX) == 2) && distY == 0 || (Math.abs(distX) == 1) && distY == 0; // normal move can still be performed
            }
        } else {
            JOptionPane.showMessageDialog(null, "This piece cannot move like this");
            return false;
        }
    }
}