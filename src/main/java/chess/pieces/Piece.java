package chess.pieces;
import chess.src.Board;

//TODO:
//  - add special moves that are still missing

// FIXME:
//  - I think pieces cannot jump so this must be fixed


public abstract class Piece {
    // attributes
    private boolean white = false;
    private String pieceName;
    private int xPos;
    private int yPos;

    // constructors
    public Piece (String name, Boolean white, int x, int y) {
        this.pieceName = name;
        this.white = white;
        this.xPos = x;
        this.yPos = y;
    }


    // getters and setters
    public Boolean getWhite() {
        return white;
    }
    public void setWhite(Boolean white) {
        this.white = white;
    }

    public String getPieceName() {
        return pieceName;
    }
    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public int getxPos() {
        return xPos;
    }
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    // methods
    public void move(int destX, int destY, Board board) {
        if (this.canMove(destX,destY, board)){
            this.setxPos(destX);
            this.setyPos(destY);
        }
        this.summary();
    }
    public abstract boolean canMove(int destX, int destY, Board board);

    public void summary() {
//        System.out.println("Position is set to " + this.getxPos() + ":" + this.getyPos() + " for this " + this.getPieceName() + ", white: " + this.getWhite());
    }

    public String shortSummary() {
        if (this.getWhite()) return (this.getPieceName() + " white");
        else return this.getPieceName() + " black";
    }

    public boolean legitMove(int x, int y) {
        if (x >= 0  && x < 8 && y >= 0  && y < 8) return true;
        else {
//            System.err.println("This move cannot be done as the piece moves out of the board!!!");
            return false;
        }
    }
}
