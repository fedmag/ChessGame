package src;

import Pieces.Piece;
import com.GUI.CellButton;

import javax.swing.*;
import java.awt.*;

//TODO:
// - add an indicator for the selected piece
// - add an indicator for the turn

public class GUIChess extends JFrame {

    private JPanel grid;
    private Player p1;
    private Player p2;
    private final Board board = new Board();
    private boolean playing;
    private String moveSequence = "";

    public GUIChess() {
        super("ChessGame");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // = 3 as int
        setResizable(false);
        initSettings();
        this.makeGrid(this.board);
        setVisible(true); // must be the last one otherwise the buttons won't show up as this makes visible only what's there when it is called, not after
    }

    private void initSettings() {
        // initializing:
        this.playing = true;
        // players
        this.p1 = new Player("Federico", true, true);
        this.p2 = new Player("Alfonso", false, false);
        System.out.println("Players created...");
        // logic Board
        this.board.buildBoard(this.p1, this.p2);
        System.out.println("Board created...");
    }

    public void makeGrid(Board board) {
            grid.setLayout(new GridLayout(8,10));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    CellButton button = new CellButton(i, j);
                    String btnCoord = ""+i+j;
                    button.setName(btnCoord);
                    // naming the cell
                    Piece piece = board.pieceAtDest(i,j);
                    String buttonName = (piece == null)? "" : piece.getPieceName();
                    button.setText(buttonName);
                    button.setFont(new Font("FreeSans", 5,20)); // nice
                    if (piece != null && piece.getWhite()) button.setForeground(Color.WHITE);
                    // getting the click
                    button.addActionListener(actionEvent -> {
                        System.out.println(button.getName() + " clicked");
//                        if(this.moveSequence.equals("")) { // first cell selected -> check if player can move that cell
//                            if (GameFlow.playerCanMoveThisPiece(p1, board, button)) playMove(button, this.p1, this.p2, this.board);
//                            else if (GameFlow.playerCanMoveThisPiece(p2, board, button)) playMove(button, this.p2, this.p1, this.board);
//                            else JOptionPane.showMessageDialog(null, "You cannot move now! " + GameFlow.whoseTurn(p1,p2));
//                        } else if (this.moveSequence.length() == 2) { // two cells selected -> no need to check
//                            if (p1.turn) playMove(button, this.p1, this.p2, this.board);
//                            else if (p2.turn) playMove(button, this.p2, this.p1, this.board);
//                        }

                        if (this.moveSequence.equals("") && GameFlow.playerCanMoveThisPiece(p1, board, button)) {
                            playMove(button, this.p1, this.p2, this.board);
                        } else if (this.moveSequence.equals("") && GameFlow.playerCanMoveThisPiece(p2, board, button)) {
                            playMove(button, this.p2, this.p1, this.board);
                        }
                        // in this case I want to check if the piece selected can actually move like requested
                        else if (this.moveSequence.length() == 2) {
                            this.moveSequence += button.getName();
                            if (p1.turn && GameFlow.pieceCanMoveLikeThat(board, this.moveSequence, p1)) {
                                playMove(button, this.p1, this.p2, this.board);
                            } else if (p2.turn && GameFlow.pieceCanMoveLikeThat(board, this.moveSequence, p2)) {
                                playMove(button, this.p2, this.p1, this.board);
                            } else {
                                System.out.println(this.moveSequence);
                                this.moveSequence = "";
                                JOptionPane.showMessageDialog(null, "This piece is not able to perform this move \nChessGUI");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "You cannot move this piece! " + GameFlow.whoseTurn(p1,p2));
                        }
                    });
                    grid.add(button);
                }
            }
            add(grid);
        }

    private void playMove(CellButton button, Player p1, Player p2, Board board) {
        System.out.println(GameFlow.whoseTurn(p1,p2));
        String coord = button.getName();
        this.moveSequence += coord;
        System.out.println("Sequence: " + this.moveSequence);
        if (this.moveSequence.length() > 3) { // two cells selected
            GameFlow.playRound(this.moveSequence, p1, p2, board);
            this.moveSequence = ""; // resetting the sequence
            // when making a move, redraw UI
            System.out.println("Redrawing grid..");
            this.updateUI(board, this.grid);
            // changing turns
            GameFlow.nextRound(p1, p2);
        } else {
            System.out.println("select next cell..");
        }
    }

    public void updateUI (Board board, JPanel grid) {
        grid.removeAll();
        this.makeGrid(board);
        grid.revalidate();
        grid.repaint();
        GameFlow.showMovesHistory();
    }
}

