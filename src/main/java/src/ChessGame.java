//import java.util.Scanner;
//
//public class ChessGame {
//    public static void main(String[] args) {
//        boolean playing = true;
//        Scanner scanner = new Scanner(System.in);
//        GUIChess guiChess = new GUIChess();
//
//        guiChess.makeGrid();
//
//        Player p1 = new Player("Federico", true, true);
//        Player p2 = new Player("Cattivo", false, false);
//
//        Board board = new Board();
//        board.buildBoard(p1, p2);
//        board.drawBoard();
//
//
//
//        while (playing) {
//            if (p1.turn) {
//                GameFlow.playRound(scanner, p1, p2, board);
//                GameFlow.nextRound(p1, p2);
//            } else {
//                GameFlow.playRound(scanner, p2, p1, board);
//                GameFlow.nextRound(p1, p2);
//            }
//        }
//    }
//
//}
