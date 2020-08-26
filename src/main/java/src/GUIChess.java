package src;

import Pieces.Piece;
import com.GUI.CellButton;

import javax.swing.*;
import java.awt.*;

//TODO:
// - add an indicator for the selected piece
// - add an indicator for the turn

public class GUIChess extends JFrame {

    private JPanel mainPanel, grid, menu;

    private Player p1;
    private Player p2;
    private Board board = new Board();
    private String moveSequence = "";
    private final CellButton[][] cellButtons = new CellButton[8][8];

    public GUIChess() {
        super("ChessGame");
        setSize(900, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // = 3 as int
        setResizable(false);
        initSettings();

        mainPanel = new JPanel(); // main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        grid = new JPanel(); // sub-panel 1
        menu = new JPanel();
        menu.setSize(100, this.getHeight());
        mainPanel.add(grid);
        mainPanel.add(menu);
        add(mainPanel);
        this.makeGrid(this.board);
        this.createMenu();
        setVisible(true); // must be the last one otherwise the buttons won't show up as this makes visible only what's there when it is called, not after
    }

    private void initSettings() {
        // initializing:
        // players
        String whitePlayerName = (String)JOptionPane.showInputDialog(
                this,
                "Choose white player name...",
                "Player names...",
                JOptionPane.PLAIN_MESSAGE);
        String blackPlayerName = (String)JOptionPane.showInputDialog(
                this,
                "Choose black player name...",
                "Player names...",
                JOptionPane.PLAIN_MESSAGE);

        this.p1 = new Player(whitePlayerName, true, true);
        this.p2 = new Player(blackPlayerName, false, false);
        System.out.println("Players created...");
        // logic Board
        this.board.buildBoard(this.p1, this.p2);
        System.out.println("Board created...");
    }

    public void makeGrid(Board board) {
            grid.setLayout(new GridLayout(8,8));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    CellButton button = new CellButton(i, j);
                    this.cellButtons[i][j] = button;
                    String btnCoord = ""+i+j;
                    button.setName(btnCoord);
                    // naming the cell
                    Piece piece = board.pieceAtDest(i,j);
                    String buttonName = (piece == null)? "" : piece.getPieceName();
//                    button.setText(buttonName);
                    this.associateIcon(button, piece);
                    button.setFont(new Font("FreeSans", Font.ITALIC,20)); // nice
//                    this.cellButtons[i][j] = button;
                    if (piece != null && piece.getWhite()) button.setForeground(Color.WHITE);
                    // getting the click
                    button.addActionListener(actionEvent -> {
                        System.out.println(button.getName() + " clicked");
                        int x = Integer.parseInt(button.getName().charAt(0)+"");
                        int y = Integer.parseInt(button.getName().charAt(1)+"");
                        // Ignore click on empty cell as first click of the round
                        if (board.cellAtIsEmpty(x,y) && this.moveSequence.equals("")) {System.out.println("Select a piece to move..");}
                        else this.buttonClicked(button, p1, p2, board);
                    });
                    grid.add(button);
                }
            }
            add(mainPanel);
    }


    public void createMenu () {
        JLabel label = new JLabel(GameFlow.whoseTurn(p1,p2));
//        TextField player1Name = new TextField("White player name..", 30);
//        JTextField player2Name = new JTextField("Black player name..", 30);
//        JButton confirmNames = new JButton("Confirm");
//        confirmNames.addActionListener(ActiveEvent -> {
//            if(!player1Name.getText().equals("") && !player2Name.getText().equals("")) {
//                p1.setName(player1Name.getText());
//                p2.setName(player2Name.getText());
//                menu.setVisible(false);
//            } else {
//                System.out.println(player1Name.getText());
//            }
//        });
//        menu.add(player1Name);
//        menu.add(player2Name);
//        menu.add(confirmNames);
        menu.add(label);
    }

    private void associateIcon(CellButton button, Piece piece) {
        if (piece != null){
            if (piece.getPieceName().equals("bishop")) {
                if (piece.getWhite())
                    button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/white-bishop-50.png"));
                else button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/icons8-bishop-50-2.png"));
            }
            if (piece.getPieceName().equals("knight")) {
                if (piece.getWhite())
                    button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/white-knight-50.png"));
                else button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/icons8-knight-50-2.png"));
            }
            if (piece.getPieceName().equals("rook")) {
                if (piece.getWhite())
                    button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/white-rook-50.png"));
                else button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/icons8-rook-50-2.png"));
            }
            if (piece.getPieceName().equals("pawn")) {
                if (piece.getWhite())
                    button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/white-pawn-50.png"));
                else button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/icons8-pawn-50-2.png"));
            }
            if (piece.getPieceName().equals("queen")) {
                if (piece.getWhite())
                    button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/white-queen-50.png"));
                else button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/icons8-queen-50-2.png"));
            }
            if (piece.getPieceName().equals("king")) {
                if (piece.getWhite())
                    button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/white-king-50.png"));
                else button.setIcon(new ImageIcon("src/main/resources/icons/50px/chessIcons/icons8-king-50-2.png"));
            }

        }
    }

    private void buttonClicked(CellButton button, Player p1, Player p2, Board board) {
        //FIXME make it not possible to select an empty cell at the start
        // first move of the round and p1 can move the selected piece
        if (this.moveSequence.equals("") && GameFlow.playerCanMoveThisPiece(p1, board, button)) {
            button.setBackground(Color.red);
            playMove(button, this.p1, this.p2, this.board);
        // first move  of the round and p2 can move the selected piece
        } else if (this.moveSequence.equals("") && GameFlow.playerCanMoveThisPiece(p2, board, button)) {
            button.setBackground(Color.red);
            playMove(button, this.p2, this.p1, this.board);
        }
        // in this case I want to check if the piece selected can actually move like requested
        else if (this.moveSequence.length() == 2) {
            this.moveSequence += button.getName();
            if (p1.turn && GameFlow.pieceCanMoveLikeThat(board, this.moveSequence, p1)) {  // p1's turn
                playMove(button, this.p1, this.p2, this.board);
            } else if (p2.turn && GameFlow.pieceCanMoveLikeThat(board, this.moveSequence, p2)) { // p2's turn
                playMove(button, this.p2, this.p1, this.board);
            } else { // invalid move
                System.out.println("Invalid move" + this.moveSequence);
                this.moveSequence = "";
                JOptionPane.showMessageDialog(null, "This piece is not able to perform this move \nChessGUI");
                updateUI(board, mainPanel);
            }
        }
        // the player is trying to move a piece that does not belong to him/her
        else {
            JOptionPane.showMessageDialog(null, "You cannot move this piece! " + GameFlow.whoseTurn(p1,p2));
            updateUI(board, mainPanel);
        }
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
            System.out.println("Redrawing mainPanel..");
            this.updateUI(board, this.mainPanel);
            // changing turns
            GameFlow.nextRound(p1, p2);
        } else {
            System.out.println("select next cell..");
            this.colorPossibleCells(button);
        }
        if (GameFlow.thereIsWinner(p1, p2)) {
            this.winScreen();
        }
    }

    private void winScreen() {
        System.out.println("WE HAVE A WINNER!!!!!!!!!!!!!!!!!!!!!!!!");
        JPopupMenu popup = new JPopupMenu();
        JLabel label = new JLabel(GameFlow.whoWon(p1, p2));
        JButton resetButton = new JButton("Restart");
        resetButton.addActionListener(ActionEvent -> {
            this.restartGame();
            popup.setVisible(false);
        });
        popup.setPopupSize(300, 300);
        popup.setLocation(400, 400);
        popup.add(label);
        popup.add(resetButton);
        popup.setVisible(true);
    }

    private void restartGame() {
        GUIChess guiChess = new GUIChess();
    }

    private void colorPossibleCells(CellButton button) {
        String coord = button.getName();
        int x = Integer.parseInt(coord.charAt(0)+"");
        int y = Integer.parseInt(coord.charAt(1)+"");
        Piece piece = board.pieceAtDest(x, y);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece otherCellPiece = board.pieceAtDest(i,j);
                if (piece != null && otherCellPiece == null && piece.canMove(i,j,board)) {
                    this.cellButtons[i][j].setBackground(Color.green);
                } else if (piece != null && otherCellPiece != null && piece.canMove(i,j,board) && otherCellPiece.getWhite() != piece.getWhite()){
                    this.cellButtons[i][j].setBackground(Color.green);
                }
            }
        }
    }

    public void updateUI (Board board, JPanel mainPanel) {
        grid.removeAll();
        this.makeGrid(board);
        grid.revalidate();
        grid.repaint();
        GameFlow.showMovesHistory();
    }
}
