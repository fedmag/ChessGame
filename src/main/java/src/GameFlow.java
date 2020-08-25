package src;

import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import com.GUI.CellButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO:
// - winning conditions

public class GameFlow {
    private  static List<Move> moves = new ArrayList<>();
    public GameFlow () {}

    public static void nextRound(Player player, Player opponent) {
        player.turn = !player.turn;
        opponent.turn = !opponent.turn;
    }

    public static void playRound (String coord, Player player, Player opponent, Board board) {
        System.out.println("____________");
        System.out.println("Piece you want to move");
        int x = Integer.parseInt(coord.charAt(0)+"");
        int y = Integer.parseInt(coord.charAt(1)+"");
        System.out.println(""+x+y);

        System.out.println("Destination cell");
        int destX = Integer.parseInt(coord.charAt(2)+"");
        int destY = Integer.parseInt(coord.charAt(3)+"");
        System.out.println(""+destX+destY);
        // adding move to history
        moves.add(new Move(player, x, y, destX, destY, board));
        // making the move
        player.makeMove(board, opponent, x, y, destX, destY);
        System.out.println("____________");
    }

    public static boolean playerCanMoveThisPiece(Player p, Board board, CellButton button) {
        int x = Integer.parseInt(button.getName().charAt(0) + "");
        int y = Integer.parseInt(button.getName().charAt(1) + "");
        Piece piece = board.pieceAtDest(x, y);
        if (piece != null) { // selected cell has a piece
            if (p.turn && p.isWhite() == piece.getWhite() && piece.getWhite() != null) return true;
            else return false;
        } else if (p.turn) return true; // selected cell is empty
        else return false;
    }

    public static String whoseTurn(Player p1, Player p2) {
        if (p1.turn) return "\nTurn: " + p1.getName();
        else if (p2.turn) return "\nTurn: " + p2.getName();
        else return "Error! Turn not detected...";
    }

    public static void showMovesHistory() {
        if(moves.size() > 0) {
            Iterator<Move> movesIterator = moves.iterator();
            for (Iterator<Move> it = movesIterator; it.hasNext(); ) {
                Move m = it.next();
                System.out.println(m.toString());
            }
        }
    }

    public static boolean pieceCanMoveLikeThat(Board board, String coord, Player player) {
        int x = Integer.parseInt(coord.charAt(0)+"");
        int y = Integer.parseInt(coord.charAt(1)+"");
        int destX = Integer.parseInt(coord.charAt(2)+"");
        int destY = Integer.parseInt(coord.charAt(3)+"");
        Piece piece = board.pieceAtDest(x,y);
        System.out.println(piece.shortSummary());
        if (piece != null && piece.canMove(destX, destY, board)) return true;
        else return false;
    }

    public static void checkSpecialMoves(Player player, Piece movingPiece, Board board, int destX, int destY) {
        // checking promotion for the pawn
        if((movingPiece instanceof Pawn) && ((Pawn) movingPiece).checkPromotion()) {
//            System.out.println(((Pawn) movingPiece).isFirstMove());
            player.removePiece(movingPiece);
            Piece newQueen = new Queen(movingPiece.getWhite(), movingPiece.getxPos(), movingPiece.getyPos());
            player.addPiece(newQueen);
            board.setPieceAtCell(destX, destY, movingPiece);
            board.setPieceAtCell(movingPiece.getxPos(), movingPiece.getyPos(), newQueen);
        }
        // TODO checking for en passant
        // TODO checking for arrocco
    }

    public static boolean thereIsWinner(Player p1, Player p2) {
        if (!p1.kingAlive() || !p2.kingAlive()) return true;
        else return false;
    }
}
