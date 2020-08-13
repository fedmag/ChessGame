import Pieces.Piece;
import com.GUI.CellButton;

import java.util.Scanner;

public class GameFlow {
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

        board = player.makeMove(board, opponent, x, y, destX, destY);
        player.setFirstMove(false);
        System.out.println("____________");


    }

    public static boolean playerCanMove(Player p, Board board, CellButton button) {
        int x = Integer.parseInt(button.getName().charAt(0) + "");
        int y = Integer.parseInt(button.getName().charAt(1) + "");
        Piece piece = board.pieceAtDest(x, y);
        if (piece != null) {
            if (p.turn && p.isWhite() == piece.getWhite() && piece.getWhite() != null) return true;
            else return false;
        } else if (p.turn) return true;
        else return false;
    }

    public static String whoseTurn(Player p1, Player p2) {
        if (p1.turn) return "\nTurn: " + p1.getName();
        else if (p2.turn) return "\nTurn: " + p2.getName();
        else return "Error! Turn not detected...";
    }
}