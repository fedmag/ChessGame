package Pieces;

import src.Board;
import src.Cell;

import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(Boolean white, int x, int y) {
        super("bishop", white, x, y);
    }

    private ArrayList<Cell> availableCells = new ArrayList<>();

    @Override
    public boolean canMove(int destX, int destY, Board board) {
        if (legitMove(destX, destY)) {
            int distX = this.getxPos() - destX;
            int distY = this.getyPos() - destY;
            if (Math.abs(distX) == Math.abs(distY)) {
                this.checkSurroundings(board);
                for (Cell cell : availableCells) {
                    if (cell.getX() == destX && cell.getY() == destY) {
                        this.availableCells.clear();
                        return true;
                    }
                }
            }
        }
        // we dont want the cell to be added otherwise by the end of the game almost all the grid would be considered as available
        this.availableCells.clear();
        return false;
    }

    // FIXME depending on the posisition of the bishop the most extreme cells are not considered available
    private void checkSurroundings(Board board) {
        int bishopX = this.getxPos();
        int bishopY = this.getyPos();
        // top left corner
        for (int i = 1; i <=  bishopX && i <= bishopY ; i++) {
            int checkinX = bishopX - i;
            int checkinY = bishopY - i;
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
        for (int i = 1; i <= bishopX && i < 8 - bishopY ; i++) {
            int checkinX = bishopX - i;
            int checkinY = bishopY + i;
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
        for (int i = 1; i < 8 - bishopX && i <= bishopY  ; i++) {
            int checkinX = bishopX + i;
            int checkinY = bishopY - i;
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
        for (int i = 1; i < 8 - bishopX && i < 8 - bishopY ; i++) {
            int checkinX = bishopX + i;
            int checkinY = bishopY + i;
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
