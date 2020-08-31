package src;

import Pieces.Piece;

import java.util.List;

public class Cell {
    private int x;
    private final int y;
    private Piece piece;
    private static char[] lettersOnTheBoard = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public Cell (int x, int y, Piece piece) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String standardNameCell () {
        int standardNumbOrder = 8 - this.x;
        return "" + lettersOnTheBoard[this.y] +  standardNumbOrder ;
    }

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

    public static int letterToNumb (char c) {
        for (int i = 0; i < lettersOnTheBoard.length ; i++) {
            if (lettersOnTheBoard[i] == c) return i;
        }
        return -1;
    }
}
