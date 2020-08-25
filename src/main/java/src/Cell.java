package src;

import Pieces.Piece;

public class Cell {
    private int x,y;
    private Piece piece;
    private char[] lettersOnTheBoard = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

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
        return "" + standardNumbOrder + lettersOnTheBoard[this.y];
    }
}
