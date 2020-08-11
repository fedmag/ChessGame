import Pieces.*;

public class Board {
    public Board() {}

    private Cell[][] board= new Cell[8][8];

    public void buildBoard(Player p1, Player p2) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1) board[i][j] = new Cell(i,j, new Pawn(false,i,j));
                else if (i == 6) board[i][j]= new Cell(i,j, new Pawn(true,i,j));
                else board[i][j] = new Cell(i,j, null);
            }
        }
        // adding black pieces
        board[0][0].setPiece(new Rook(false, 0,0));
        board[0][1].setPiece(new Knight(false, 0,1));
        board[0][2].setPiece(new Bishop(false, 0,2));
        board[0][3].setPiece(new Queen(false, 0,3));
        board[0][4].setPiece(new King(false, 0,4));
        board[0][5].setPiece(new Bishop(false, 0,5));
        board[0][6].setPiece(new Rook(false, 0,6));
        board[0][7].setPiece(new Knight(false, 0,7));
        // adding white pieces
        board[7][0].setPiece(new Rook(true, 7,0));
        board[7][1].setPiece(new Knight(true, 7,1));
        board[7][2].setPiece(new Bishop(true, 7,2));
        board[7][3].setPiece(new Queen(true, 7,3));
        board[7][4].setPiece(new King(true, 7,4));
        board[7][5].setPiece(new Bishop(true, 7,5));
        board[7][6].setPiece(new Rook(true, 7,6));
        board[7][7].setPiece(new Knight(true, 7,7));

        givePiecesToPlayer(this, p1, p2);
    }


    public boolean isEmpty (int x, int y) {
        return board[x][y] == null;
    }

    public Piece movingPiece (int x, int y) {
        return board[x][y].getPiece();
    }

    public Piece pieceAtDest (int x, int y) {
        if (! isEmpty(x,y)) return board[x][y].getPiece();
        else return null;
    }

    public void setPieceAtCell (int x, int y, Piece piece) {
        board[x][y].setPiece(piece);
    }

    public void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = movingPiece(i, j);
                if (piece != null) {
                    System.out.print(piece.shortSummary() + " ||");
                }
                else System.out.print(" --   o   --||");
            }
            System.out.println("");
        }
    }

    public void givePiecesToPlayer (Board board, Player p1, Player p2) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = this.movingPiece(i, j);
                if (piece != null && piece.getWhite() == p1.isWhite()) {
                    p1.addPiece(piece);
                }
                else if (piece != null && piece.getWhite() == p2.isWhite()) {
                    p2.addPiece(piece);
                }
            }
        }
    }

}
