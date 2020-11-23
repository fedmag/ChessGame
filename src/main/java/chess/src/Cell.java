package chess.src;

import chess.pieces.Piece;
import java.util.List;

/**
 * Each square on the grid is represtented by a cell
 */
public class Cell {
    private int x;
    private final int y;
    private Piece piece;
    private static final char[] lettersOnTheBoard = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    /**
     * Creates a cell
     * @param x x coordinate (row)
     * @param y y coordinate (col)
     * @param piece piece to place
     */
    public Cell (int x, int y, Piece piece) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    /**
     * Set a piece on the cell
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Retrieve a piece on the cell
     * @return the piece on the cell
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Get the x (row) coordinate of the cell
     * @return the x (row) coordinate of the cell
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieve the y (col) coordinate of the cell
     * @return the y (col) coordinate of the cell
     */
    public int getY() {
        return y;
    }

    /**
     * Transforms the cell position from matrix format to standard format (eg. e4)
     * @return the cell position in the standard format (eg. e4)
     */
    public String standardNameCell () {
        int standardNumbOrder = 8 - this.x;
        return "" + lettersOnTheBoard[this.y] +  standardNumbOrder ;
    }

    /**
     * Transforms the col number in the associated letter
     * @param y columns
     * @return the col number in the associated letter
     */
    public static char yInLetter(int y) { return lettersOnTheBoard[y]; }


    public static String fromMatrixNotationToLetters (List<Cell> cells) {
        if (cells.size() == 1) {
            int x = cells.get(0).getX();
            int y = cells.get(0).getY();
            return ""+ x + Cell.yInLetter(y);
        } else {
            int x = cells.get(0).getX();
            int y = cells.get(0).getY();
            int destX = cells.get(1).getX();
            int destY = cells.get(1).getY();
            return ""+ x + Cell.yInLetter(y) + " -> " + destX + Cell.yInLetter(destY) ;
        }
    }

    /**
     * Transforms the letter from the standard form to the col number coordinate
     * @param c letter indicating the col
     * @return the col number coordinate
     */
    public static int letterToNumb (char c) {
        for (int i = 0; i < lettersOnTheBoard.length ; i++) {
            if (lettersOnTheBoard[i] == c) return i;
        }
        return -1;
    }
}
