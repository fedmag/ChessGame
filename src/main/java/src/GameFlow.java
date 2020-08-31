package src;

import Pieces.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// FIXME history is like 1 step behind

public class GameFlow {
    public  static List<Move> movesHistory = new ArrayList<>();
    public static List<Cell> cellSequence = new ArrayList<>();

    public GameFlow () {}

    public static void nextRound(Player player, Player opponent) {
        player.turn = !player.turn;
        opponent.turn = !opponent.turn;
    }

    public static void playRound (List<Cell> cellSequence, Player player, Player opponent, Board board) {
        int x = cellSequence.get(0).getX();
        int y = cellSequence.get(0).getY();
        int destX = cellSequence.get(1).getX();
        int destY = cellSequence.get(1).getY();
        // making the move
        player.makeMove(board, opponent, x, y, destX, destY);
        // adding move to history
        movesHistory.add(new Move(player, x, y, destX, destY, board));
        System.out.println("____________");
    }

    public static boolean playerCanMoveThisPiece(Player p, Board board, List<Cell> cellSequence) {
        int x = cellSequence.get(0).getX();
        int y = cellSequence.get(0).getY();
        Piece piece = board.pieceAtDest(x, y);
        if (piece != null) { // selected cell has a piece
            return p.turn && p.isWhite() == piece.getWhite() && piece.getWhite() != null;
        } else return p.turn; // selected cell is empty
    }

    public static String whoseTurn(Player p1, Player p2) {
        if (p1.turn) return "\nTurn: " + p1.getName();
        else if (p2.turn) return "\nTurn: " + p2.getName();
        else return "Error! Turn not detected...";
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

    public static boolean pieceCanMoveLikeRequested(Board board, List<Cell> cells) {
        if (cells.size() > 1){
            int x = cells.get(0).getX();
            int y = cells.get(0).getY();
            int destX = cells.get(1).getX();
            int destY = cells.get(1).getY();
            Piece piece = board.pieceAtDest(x, y);
            return piece != null && piece.canMove(destX, destY, board);
        }
        return false;
    }

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
        if (movingPiece instanceof King ) {
            Piece pieceAtDest = board.pieceAtDest(destX, destY);
            if (pieceAtDest instanceof  Rook && !((King) movingPiece).isCastlingDone() && !((Rook) pieceAtDest).isCastlingDone()) {
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

    public static boolean thereIsWinner(Player p1, Player p2) {
        return !p1.kingAlive() || !p2.kingAlive();
    }

    public static String whoWon (Player p1, Player p2) {
        if (thereIsWinner(p1, p2)) {
            if (p1.kingAlive()) return p1.getName();
            else return p2.getName();
        }
        return "";
    }

    public static String getLastMoveCells() {
        if (movesHistory.size() > 0) return movesHistory.get(movesHistory.size() -1).cellMove();
        return "";
    }

    public static int movesSize() {
        return movesHistory.size();
    }
}
