package chess.pieces;
import chess.src.Board;

//TODO:
//  - add special moves that are still missing

// FIXME:
//  - I think pieces cannot jump so this must be fixed

/**
 *  Abstract class used as base for each piece in the game
 */
public abstract class Piece {
    // attributes
    private boolean white = false;
    private String pieceName;
    private int xPos;
    private int yPos;


    /**
     *
     * @param name Piece name (King, Queen, etc.. )
     * @param white Boolean, true for white pieces
     * @param x x position on the grid
     * @param y y position on the grid
     */
    public Piece (String name, Boolean white, int x, int y) {
        this.pieceName = name;
        this.white = white;
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Retrieves the team to which the piece belong (white / black)
     * @return the team to which the piece belong (white / black)
     */
    // getters and setters
    public Boolean getWhite() {
        return white;
    }

    /**
     * Set piece color
     * @param white boolean, indicates whether the piece is white
     */
    private void setWhite(Boolean white) {
        this.white = white;
    }

    /**
     * Retrieves piece name
     * @return piece name (Knight, Queen, etc.. )
     */
    public String getPieceName() {
        return pieceName;
    }

    /**
     * Set piece name
     * @param pieceName new piece name
     */
    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    /**
     * Retrieves x position on the grid
     * @return x position on the grid
     */
    public int getxPos() {
        return xPos;
    }
    /**
     * Set new x position
     * @param xPos new x position
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Retrieves y position on the grid
     * @return y position on the grid
     */
    public int getyPos() {
        return yPos;
    }
    /**
     * Set new y position
     * @param yPos new x position
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Used to perform a move, changing the locations stored internally by the piece
     * @param destX desired x location
     * @param destY desired y location
     * @param board current board, used to check if the move is possible
     */
    public void move(int destX, int destY, Board board) {
        if (this.canMove(destX,destY, board)){
            this.setxPos(destX);
            this.setyPos(destY);
        }
    }

    /**
     * Boolean that express if the desired move can actually be performed give the rules of the game
     * @param destX desired x location
     * @param destY desired y location
     * @param board current board, used to check if the move is possible
     * @return if the desired move can actually be performed give the rules of the game
     */
    public abstract boolean canMove(int destX, int destY, Board board);


    /**
     * Mainly for debugging, retrieve a  short description of the main characteristics of the piece
     * @return a String with a description of the main characteristics of the piece
     */
    public String shortSummary() {
        if (this.getWhite()) return (this.getPieceName() + " white");
        else return this.getPieceName() + " black";
    }

    /**
     * Used to check if the desired move falls within the board
     * @param x desired x location
     * @param y desired y location
     * @return boolean, if the desired move falls within the board
     */
    public boolean legitMove(int x, int y) {
        if (x >= 0  && x < 8 && y >= 0  && y < 8) return true;
        else {
//            System.err.println("This move cannot be done as the piece moves out of the board!!!");
            return false;
        }
    }
}
