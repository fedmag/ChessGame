import Pieces.*;

import java.io.InputStream;
import java.util.Scanner;

public class ChessGame {
    public static void main(String[] args) {
        boolean playing = true;
        Scanner scanner = new Scanner(System.in);

        Player p1 = new Player("Federico", true, true);
        Player p2 = new Player("Cattivo", false, false);

        Board board = new Board();
        board.buildBoard(p1, p2);
        board.drawBoard();



        p1.printPiecesLeft();

        while (playing) {
            if (p1.turn) {
                playRound(scanner, p1, p2, board);
                nextRound(p1, p2);
            } else {
                playRound(scanner, p2, p1, board);
                nextRound(p1, p2);
            }
        }
    }

    public static void nextRound(Player player, Player opponent) {
        player.turn = !player.turn;
        opponent.turn = !opponent.turn;
    }

    public static void playRound (Scanner scanner, Player player, Player opponent, Board board) {
        System.out.println(player.getName() + "'s turn");
        System.out.println("Piece you want to move");
        String line = scanner.next();
        int x = Integer.parseInt(line.split(",")[0]);
        int y = Integer.parseInt(line.split(",")[1]);

        System.out.println("Destination cell");
        line = scanner.next();
        int destX = Integer.parseInt(line.split(",")[0]);
        int destY = Integer.parseInt(line.split(",")[1]);

        player.makeMove(board, opponent, x, y, destX, destY);
        player.setFirstMove(false);
        board.drawBoard();
    }
}
