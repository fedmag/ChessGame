package chess.pieces;

import chess.src.Board;
import chess.src.Cell;

import java.util.ArrayList;

public class Queen extends Piece {

    private ArrayList<Cell> availableCells = new ArrayList<>();

    public Queen(Boolean white, int x, int y) {
        super("queen", white, x, y);
    }


    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            // moving on a diagonal
            if ((Math.abs(distX) == Math.abs(distY))||
                    // moving vertically or horizontally
                    ((distX == 0 && distY !=0) ) ||
                    (distX != 0 && distY ==0)) {
                this.checkSurroundings(board);
                for (Cell cell : availableCells) {
                    if (cell.getX() == destX && cell.getY() == destY) {
                        this.availableCells.clear();
                        return true;
                    }
                }
            }
        } else return false;
        return false;
    }

    /**
     * Used to store available empty cells around the queen, used to prevent movements on other pieces
     * @param board the current board
     */
    private void checkSurroundings(Board board) {
        int queenX = this.getxPos();
        int queenY = this.getyPos();
        // top left corner
        for (int i = 1; i <=  queenX && i <= queenY ; i++) {
            int checkinX = queenX - i;
            int checkinY = queenY - i;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // top right corner
        for (int i = 1; i <= queenX && i < 8 - queenY ; i++) {
            int checkinX = queenX - i;
            int checkinY = queenY + i;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // bottom left corner
        for (int i = 1; i < 8 - queenX && i <= queenY  ; i++) {
            int checkinX = queenX + i;
            int checkinY = queenY - i;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // bottom right corner
        for (int i = 1; i < 8 - queenX && i < 8 - queenY ; i++) {
            int checkinX = queenX + i;
            int checkinY = queenY + i;
            if (board.cellAtIsEmpty(checkinX, checkinY)) this.availableCells.add(board.getCell(checkinX, checkinY));
            else {
                Piece piece = board.pieceAtDest(checkinX, checkinY);
                if (piece.getWhite() != this.getWhite()) {
                    this.availableCells.add(board.getCell(checkinX, checkinY));
                    break;
                } else break;
            }
        }
        // moving up
        for (int i = 1; i <=  queenX; i++) {
            int checkinX = queenX - i;
            int checkinY = queenY;
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
        for (int i = 1;  i < 8 - queenY ; i++) {
            int checkinX = queenX;
            int checkinY = queenY + i;
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
        for (int i = 1; i <= queenY  ; i++) {
            int checkinX = queenX;
            int checkinY = queenY - i;
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
        for (int i = 1; i < 8 - queenX ; i++) {
            int checkinX = queenX + i;
            int checkinY = queenY;
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
}
