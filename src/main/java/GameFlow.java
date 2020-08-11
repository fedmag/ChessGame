import java.util.Scanner;

public class GameFlow {
    public GameFlow () {}

    public static String moveSequence = "";

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
//        board.drawBoard();
    }
}
