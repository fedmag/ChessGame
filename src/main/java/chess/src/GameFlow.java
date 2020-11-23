package chess.src;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// FIXME history is like 1 step behind

/**
 * Manages the gameflow
 */
public class GameFlow {
    public  static List<Move> movesHistory = new ArrayList<>();
    public static List<Cell> cellSequence = new ArrayList<>();
    public static int turnCounter = 0;

    public GameFlow () {}

    /**
     * Changes round
     * @param player the player who is currently playing
     * @param opponent the player who is NOT currently playing
     */
    public static void nextRound(Player player, Player opponent) {
        player.turn = !player.turn;
        opponent.turn = !opponent.turn;
    }

    /**
     * Manages one round, calling the makeMove method from the Player class
     * @param cellSequence list storing the two cells that indicate the starting and destination cell
     * @param player Player, the player who moves
     * @param opponent Player, the player who is NOT moving
     * @param board current status of the board
     */
    public static void playRound (List<Cell> cellSequence, Player player, Player opponent, Board board) {
        int x = cellSequence.get(0).getX();
        int y = cellSequence.get(0).getY();
        int destX = cellSequence.get(1).getX();
        int destY = cellSequence.get(1).getY();
        // making the move
        player.makeMove(board, opponent, x, y, destX, destY);
        // adding move to history
        movesHistory.add(new Move(player, x, y, destX, destY, board));
        if(!player.isWhite()) turnCounter ++;
    }

    /**
     * Checks whether the player can move the piece he wants to move
     * @param p Player, who wants to move
     * @param board current status of the board
     * @param cellSequence list storing the two cells that indicate the starting and destination cell
     * @return whether the player can move the piece he wants to move
     */
    public static boolean playerCanMoveThisPiece(Player p, Board board, List<Cell> cellSequence) {
        int x = cellSequence.get(0).getX();
        int y = cellSequence.get(0).getY();
        Piece piece = board.pieceAtDest(x, y);
        if (piece != null) { // selected cell has a piece
            return p.turn && p.isWhite() == piece.getWhite() && piece.getWhite() != null;
        } else return p.turn; // selected cell is empty
    }

    /**
     * Checks whose turn is being played and prints it
     * @param p1 Player, first player
     * @param p2 Player, second player
     * @return whose turn is being played and prints it
     */
    public static String whoseTurn(Player p1, Player p2) {
        if (p1.turn) return "\nTurn: " + p1.getName();
        else if (p2.turn) return "\nTurn: " + p2.getName();
        else return "Error! Turn not detected...";
    }

    //FIXME bugged. When a wrong cell is clicked the turn changes

    /**
     * Used to compose the fen notation, giving back the color of the player who has to move
     * @param p1 Player, first player
     * @param p2 Player, second player
     * @return gives back the color of the player who has to move
     */
    public static String whoseTurnColor (Player p1, Player p2) { // we are referring to the next move, so not the one is moving right now
        if (p1.turn) return "w ";
        else if (p2.turn) return "b ";
        else return "null";
    }

    public static void showMovesHistory() {
        if(movesHistory.size() > 0) {
            Iterator<Move> movesIterator = movesHistory.iterator();
            for (Iterator<Move> it = movesIterator; it.hasNext(); ) {
                Move m = it.next();
                System.out.println(m.toString());
            }
        }
    }

    /**
     * Checks if the piece can be moved as requested
     * @param board current status of the board
     * @param cellsSequence list storing the two cells that indicate the starting and destination cell
     * @return boolean, if the piece can be moved as requested
     */
    public static boolean pieceCanMoveLikeRequested(Board board, List<Cell> cellsSequence) {
        if (cellsSequence.size() > 1){
            int x = cellsSequence.get(0).getX();
            int y = cellsSequence.get(0).getY();
            int destX = cellsSequence.get(1).getX();
            int destY = cellsSequence.get(1).getY();
            Piece piece = board.pieceAtDest(x, y);
            return piece != null && piece.canMove(destX, destY, board);
        }
        return false;
    }

    /**
     * Checks for special moves
     * @param player Player, the one who performs the move
     * @param movingPiece Piece, the piece is getting moved
     * @param board current board status
     * @param destX coord x (row) of destination cell
     * @param destY coord y (col) of destination cell
     */
    public static void checkSpecialMoves(Player player, Piece movingPiece, Board board, int destX, int destY) {
        // checking promotion for the pawn
        if((movingPiece instanceof Pawn) && ((Pawn) movingPiece).checkPromotion()) {
            player.removePiece(movingPiece);
            Piece newQueen = new Queen(movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos());
            player.addPiece(newQueen);
            board.setPieceAtCell(destX, destY, movingPiece);
            board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos(), newQueen);
        }
        // EnPassant is checked in the pawn class
        // checking for castling
        if (movingPiece instanceof King) {
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if (pieceAtDest instanceof Rook && !((King) movingPiece).isCastlingDone() && !((Rook) pieceAtDest).isCastlingDone()) {
                // now castling is performed
                ((King) movingPiece).setCastlingDone(true);
                ((Rook) pieceAtDest).setCastlingDone(true);
                int kingX  = movingPiece.getxPos();
                int kingY  = movingPiece.getyPos();
                // moving the pieces
                int dist = kingY - pieceAtDest.getyPos();
                if(dist > 0) {// castling with the rook on the left
                    // cleaning and removing
                    player.removePiece(movingPiece);
                    player.removePiece(pieceAtDest);
                    board.setPieceAtCell(kingX, kingY, null);
                    board.setPieceAtCell(pieceAtDest.getxPos(), pieceAtDest.getyPos(), null);
                    // creating to pieces with new coordinates
                    Piece newKing = new King (movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos() -2);
                    Piece newRook = new Rook(movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos() - 1);
                    //adding those pieces to both the player and the board
                    player.addPiece(newKing);
                    player.addPiece(newRook);
                    board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos() -2, newKing);
                    board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos() - 1, newRook);
                } else { // castling on the right
                    // cleaning and removing
                    player.removePiece(movingPiece);
                    player.removePiece(pieceAtDest);
                    board.setPieceAtCell(kingX, kingY, null);
                    board.setPieceAtCell(pieceAtDest.getxPos(), pieceAtDest.getyPos(), null);
                    // creating to pieces with new coordinates
                    Piece newKing = new King (movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos() + 2);
                    Piece newRook = new Rook(movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos() + 1);
                    //adding those pieces to both the player and the board
                    player.addPiece(newKing);
                    player.addPiece(newRook);
                    board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos() + 2, newKing);
                    board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos() + 1, newRook);
                }
            }
        }
    }

    /**
     * Checks for a winner
     * @param p1 Player, first player
     * @param p2 Player, second player
     * @return if one of the kings has been conquered
     */
    public static boolean thereIsWinner(Player p1, Player p2) {
        return !p1.kingAlive() || !p2.kingAlive();
    }

    /**
     * Checks who won the game
     * @param p1 Player, first player
     * @param p2 Player, second player
     * @return the name of the winner
     */
    public static String whoWon (Player p1, Player p2) {
        if (thereIsWinner(p1, p2)) {
            if (p1.kingAlive()) return p1.getName();
            else return p2.getName();
        }
        return "";
    }

    /**
     * Retrieve last move that was made
     * @return last move that was made
     */
    public static String getLastMoveCells() {
        if (movesHistory.size() > 0) return movesHistory.get(movesHistory.size() -1).cellMove();
        return "";
    }

    public static int movesSize() {
        return movesHistory.size();
    }
}
