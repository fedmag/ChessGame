package chess.pieces;

import chess.src.Board;
import chess.src.Cell;

import java.util.ArrayList;

public class Rook extends Piece {

    private ArrayList<Cell> availableCells = new ArrayList<>();
    private boolean castlingDone = false;

    public Rook(Boolean white, int x, int y) {
        super("rook", white, x, y);
    }


    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if ((distX ==0 && distY !=0) || (distX != 0 && distY ==0)) {
                this.checkSurroundings(board);
                for (Cell cell : availableCells) {
                    if (cell.getX() == destX && cell.getY() == destY) {
                        this.availableCells.clear();
                        return true;
                    }
                }
            }
        } else return false;
        // we dont want the cell to be added otherwise by the end of the game almost all the grid would be considered as available
        this.availableCells.clear();
        return false;
    }



    private void checkSurroundings(Board board) {
        int rookX = this.getxPos();
        int rookY = this.getyPos();
        // moving up
        for (int i = 1; i <=  rookX; i++) {
            int checkinX = rookX - i;
            int checkinY = rookY;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // moving right
        for (int i = 1;  i < 8 - rookY ; i++) {
            int checkinX = rookX;
            int checkinY = rookY + i;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // moving left
        for (int i = 1; i <= rookY  ; i++) {
            int checkinX = rookX;
            int checkinY = rookY - i;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // moving down
        for (int i = 1; i < 8 - rookX ; i++) {
            int checkinX = rookX + i;
            int checkinY = rookY;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
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

