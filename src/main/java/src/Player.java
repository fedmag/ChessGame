package src;

import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;

import javax.swing.*;
import java.util.ArrayList;

public class Player {
    private String name;
    private boolean white;
    private boolean firstMove = true;
    boolean turn = false;
    private ArrayList<Piece> pieces = new ArrayList();

    //FIXME probably the firstMove is related to the pawn, so each pawn can move twice for its first move, might not be related with the match
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

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void removePiece(Piece piece) {
        this.pieces.remove(piece);
    }

    public Board makeMove(Board board, Player opponent, int initX, int initY, int destX, int destY) {
        Piece movingPiece = board.movingPiece(initX, initY);
        Piece destPiece = board.pieceAtDest(destX, destY);
        //we have to be sure that a player can move only pieces from his color
        if (this.isWhite() == movingPiece.getWhite()) {
            board = updateGame(board, opponent, movingPiece, destPiece, initX, initY, destX, destY);
        } else JOptionPane.showMessageDialog(null, "You can only move pieces that belong to you!");
        return board;
    }

    public Board updateGame(Board board, Player opponent, Piece movingPiece, Piece destPiece, int initX, int initY, int destX, int destY) {
        // if the destination cell has a piece already
        if (destPiece != null) {
            if (destPiece.getWhite() != movingPiece.getWhite()) {
                //setting piece attribute
                boolean specialMove = false;
                if (this.firstMove) specialMove = true;
                if (movingPiece.canMove(destX, destY, specialMove, board)) {
                    movingPiece.move(destX, destY, specialMove, board);
                    //setting player attribute
                    opponent.removePiece(destPiece);
                    //setting board attribute
                    //cleaning starting position and arriving position and moving the piece
                    board.setPieceAtCell(initX, initY, null);
                    board.setPieceAtCell(destX, destY, null);
                    board.setPieceAtCell(destX, destY, movingPiece);
                    if((movingPiece instanceof Pawn) && ((Pawn) movingPiece).checkPromotion()) {
                        this.removePiece(movingPiece);
                        Piece newQueen = new Queen(movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos());
                        this.addPiece(newQueen);
                        board.setPieceAtCell(destX, destY, movingPiece);
                        board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos(), newQueen);

                    }
                    return board;
                } else JOptionPane.showMessageDialog(null, "You cannot move there!");
            } else JOptionPane.showMessageDialog(null, "You cannot move there, you already have a piece on that cell!");
            // if the destination cell is empty
        } else {
            System.out.println("Destination cell is empty");
            boolean specialMove = false;
            if (this.firstMove) specialMove = true;
            //setting piece attribute
            if (movingPiece.canMove(destX, destY, specialMove, board)) {
                movingPiece.move(destX, destY, specialMove, board);
                //setting board attribute
                //cleaning starting position and arriving position and moving the piece
                board.setPieceAtCell(initX, initY, null);
                board.setPieceAtCell(destX, destY, null);
                board.setPieceAtCell(destX, destY, movingPiece);
                return board;
            }
        }
        return board;
    }

    public void printPiecesLeft() {
        System.out.println(this.pieces.toString());
    }
}
