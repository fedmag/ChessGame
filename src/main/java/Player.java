import Pieces.Piece;

import java.util.ArrayList;

public class Player {
    private String name;
    private boolean white;
    boolean turn = false;
    private ArrayList<Piece> pieces = new ArrayList();

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

    public void makeMove(Board board, Player opponent, int initX, int initY, int destX, int destY) {
        Piece movingPiece = board.movingPiece(initX, initY);
        Piece destPiece = board.pieceAtDest(destX, destY);
        //we have to be sure that a player can move only pieces from his color
        if (this.isWhite() == movingPiece.getWhite()) {
            updateGame(board, opponent, movingPiece, destPiece, initX, initY, destX, destY);
        } else System.out.println("You can only move your pieces");
    }

    public void updateGame(Board board, Player opponent, Piece movingPiece, Piece destPiece, int initX, int initY, int destX, int destY) {
        // if the destination cell has a piece already
        if (destPiece != null) {
            if (destPiece.getWhite() != movingPiece.getWhite()) {
                //setting piece attribute
                if (movingPiece.canMove(destX, destY)) {
                    movingPiece.move(destX, destY);
                    //setting player attribute
                    opponent.removePiece(destPiece);
                    //setting board attribute
                    //cleaning starting position and arriving position and moving the piece
                    board.setPieceAtCell(initX, initY, null);
                    board.setPieceAtCell(destX, destY, null);
                    board.setPieceAtCell(destX, destY, movingPiece);
                }
            } else System.out.println("You cannot move there as there's another piece that belongs to you");
            // if the destination cell is empty
        } else {
            System.out.println("Destination cell is empty");
            //setting piece attribute
            if (movingPiece.canMove(destX, destY)) {
                movingPiece.move(destX, destY);
                //setting board attribute
                //cleaning starting position and arriving position and moving the piece
                board.setPieceAtCell(initX, initY, null);
                board.setPieceAtCell(destX, destY, null);
                board.setPieceAtCell(destX, destY, movingPiece);
            }
        }
    }

    public void printPiecesLeft() {
        System.out.println(this.pieces.toString());
    }
}
