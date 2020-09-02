package chess.src;

import chess.pieces.Pawn;
import chess.pieces.Piece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {

    private String name = "";
    private boolean white;
    boolean turn = false;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    public Player(String name, boolean white, boolean startingPlayer) {
        this.name = name;
        this.white = white;
        this.turn = startingPlayer;
    }

    public boolean isWhite() {
        return white;
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
    }

    public String getName() {
        return name;
    }

    public void removePiece(Piece piece) {
        this.pieces.remove(piece);
    }


    // TODO this checks might be implemented in the Piece abstract class or in the various piece classes
    public void makeMove(Board board, Player opponent, int initX, int initY, int destX, int destY) {
        Piece movingPiece = board.movingPiece(initX, initY);
        Piece destPiece = board.pieceAtDest(destX, destY);
        //we have to be sure that a player can move only pieces from his color
        if (this.isWhite() == movingPiece.getWhite()) {
            // if we are moving on a cell with an opponent piece
            if(destPiece != null && destPiece.getWhite() != movingPiece.getWhite()) updateGame(board, opponent, movingPiece, destPiece, initX, initY, destX, destY);
            // the destination cell is empty
            else if (destPiece == null) updateGame(board, opponent, movingPiece, null, initX, initY, destX, destY);
            // in case the clicked cell contains another piece belonging to this player I check for casteling
            else GameFlow.checkSpecialMoves(this, movingPiece, board, destX,destY);
        } else JOptionPane.showMessageDialog(null, "You can only move pieces that belong to you!");
    }

    public void updateGame(Board board, Player opponent, Piece movingPiece, Piece destPiece, int initX, int initY, int destX, int destY) {
        // if the destination cell has a piece already
        if (destPiece != null) {
            // not trying to eat your own piece
            if (destPiece.getWhite() != movingPiece.getWhite()) {
                //setting piece attribute
                if (movingPiece.canMove(destX, destY,board)) {
                    movingPiece.move(destX, destY, board);
                    //setting player attribute
                    opponent.removePiece(destPiece);
                    //setting board attribute
                    //cleaning starting position and arriving position and moving the piece
                    board.setPieceAtCell(initX, initY, null);
                    board.setPieceAtCell(destX, destY, null);
                    board.setPieceAtCell(destX, destY, movingPiece);
                    GameFlow.checkSpecialMoves(this, movingPiece, board, destX, destY);
                } else JOptionPane.showMessageDialog(null, "You cannot move there!");
            } else GameFlow.checkSpecialMoves(this, movingPiece, board, destX, destY);
        // if the destination cell is empty
        } else {
//            System.out.println("Destination cell is empty \n");
            // checking for EN PASSANT
            if (movingPiece instanceof Pawn && ((Pawn) movingPiece).enPassantAvailable(destX, destY)) {
                movingPiece.move(destX, destY, board);
                //setting board attribute
                //cleaning starting position and arriving position and moving the piece
                board.setPieceAtCell(initX, initY, null);
                board.setPieceAtCell(destX, destY, null);
                board.setPieceAtCell(destX, destY, movingPiece);
                if (movingPiece.getWhite()) { // white
                    Piece blackPiece = board.pieceAtDest(destX + 1, destY);
                    opponent.removePiece(blackPiece);
                    board.setPieceAtCell(destX + 1, destY, null);
                } else  {
                    board.setPieceAtCell(destX - 1, destY, null);
                    Piece whitePiece = board.pieceAtDest(destX - 1, destY);
                    opponent.removePiece(whitePiece);
                }
            }
            //setting piece attribute in the most general case
            else if (movingPiece.canMove(destX, destY, board)) {
                movingPiece.move(destX, destY, board);
                //setting board attribute
                //cleaning starting position and arriving position and moving the piece
                board.setPieceAtCell(initX, initY, null);
                board.setPieceAtCell(destX, destY, null);
                board.setPieceAtCell(destX, destY, movingPiece);
                GameFlow.checkSpecialMoves(this, movingPiece, board, destX, destY);
            }
        }
    }

    public void printPiecesLeft() {
//        System.out.println(this.pieces.toString());
    }

    public boolean kingAlive() {
        Iterator<Piece> iter = this.pieces.iterator();
        for (Iterator<Piece> it = iter; it.hasNext(); ) {
            Piece p = it.next();
            if (p.getPieceName().equals("king")) return true;
        } return false;
    }

    public void setName(String name) {
        this.name = name;
    }
}
