package chess.src;

import chess.AI.FEN;
import chess.GUI.WinScreen;
import chess.pieces.*;
import chess.GUI.CellButton;
import chess.GUI.PlayerNames;


import javax.swing.*;
import java.awt.*;

//TODO:
// - add an indicator for the selected piece
// - add an indicator for the turn

public class GUIChess extends JFrame {

    private JPanel mainPanel, grid, menu;

    JTextArea movesList = new JTextArea();
    JLabel turnPlayer = new JLabel();

    private Player p1;
    private Player p2;
    private String whiteName, blackName;
    private Board board = new Board();
    private final CellButton[][] cellButtons = new CellButton[8][8];


    public GUIChess(String whiteName, String blackName) {
        super("ChessGame");
        setSize(850, 800);
        setLocation(350, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // = 3 as int
        setResizable(false);
        initSettings(whiteName, blackName);

        mainPanel = new JPanel(); // main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        grid = new JPanel(); // sub-panel
        menu = new JPanel(); // sub-panel
        menu.setSize(30, this.getHeight());
        mainPanel.add(grid);
        mainPanel.add(menu);
        add(mainPanel);
        this.makeGrid(this.board);
        this.createMenu();
        setVisible(true); // must be the last one otherwise the buttons won't show up as this makes visible only what's there when it is called, not after
    }

    private void initSettings(String whiteName, String blackName) {
        // initializing:
        // players
        this.p1 = new Player(whiteName, true, true);
        this.p2 = new Player(blackName, false, false);
        System.out.println("Players created...");
        // logic Board
        turnPlayer.setText(GameFlow.whoseTurn(p1,p2));
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
                    this.associateIcon(button, piece);
                    button.setFont(new Font("FreeSans", Font.ITALIC,20)); // nice
                    if (piece != null && piece.getWhite()) button.setForeground(Color.WHITE);
                    // getting the click
                    button.addActionListener(actionEvent -> {
                        int x = Integer.parseInt(button.getName().charAt(0)+"");
                        int y = Integer.parseInt(button.getName().charAt(1)+"");
                        // Ignore click on empty cell as first click of the round
                        if (board.cellAtIsEmpty(x,y) && GameFlow.cellSequence.size()==0) {}
                        else this.buttonClicked(button, p1, p2, board);
                    });
                    grid.add(button);
                }
            }
            add(mainPanel);
    }

    private void buttonClicked(CellButton button, Player p1, Player p2, Board board) {
        GameFlow.cellSequence.add(board.getCell(button.getCoordX(), button.getCoordY()));
        // first move  of the round and p1 can move the selected piece
        if (GameFlow.cellSequence.size() == 1 && GameFlow.playerCanMoveThisPiece(p1, board, GameFlow.cellSequence)) {
            button.setBackground(Color.red);
            playMove(button, this.p1, this.p2, this.board);
        // first move  of the round and p2 can move the selected piece
        } else if (GameFlow.cellSequence.size() == 1 && GameFlow.playerCanMoveThisPiece(p2, board, GameFlow.cellSequence)) {
            button.setBackground(Color.red);
            playMove(button, this.p2, this.p1, this.board);
        }
        // in this case I want to check if the piece selected can actually move like requested
        else if (GameFlow.cellSequence.size() == 2) { // a cell is selected already
            if (p1.turn && GameFlow.pieceCanMoveLikeRequested(board, GameFlow.cellSequence)) {  // p1's turn
                playMove(button, this.p1, this.p2, this.board);
                turnPlayer.setText(GameFlow.whoseTurn(p1,p2));
            } else if (p2.turn && GameFlow.pieceCanMoveLikeRequested(board, GameFlow.cellSequence)) { // p2's turn
                playMove(button, this.p2, this.p1, this.board);
                turnPlayer.setText(GameFlow.whoseTurn(p1,p2));
            } else { // invalid move
                GameFlow.cellSequence.clear();
                updateUI(board);
            }
        }
        // the player is trying to move a piece that does not belong to him/her
        else {
            JOptionPane.showMessageDialog(null, "You cannot move this piece! " + GameFlow.whoseTurn(p1,p2));
            GameFlow.cellSequence.clear();
            updateUI(board);
        }
    }

    private void playMove(CellButton button, Player p1, Player p2, Board board) {
        if (GameFlow.cellSequence.size() > 1) { // two cells selected
            GameFlow.playRound(GameFlow.cellSequence, p1, p2, board);
            GameFlow.cellSequence.clear(); // resetting the sequence
            // when making a move, redraw UI
            System.out.println("Redrawing mainPanel..");
            this.updateUI(board);
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

///////////////////////////////////////////////////////
///////////////////     GUI    ///////////////////////
//////////////////////////////////////////////////////

    private void winScreen() {
        new WinScreen(GameFlow.whoWon(this.p1, this.p2));
        dispose();
    }

    private void restartGame() {
        GameFlow.movesHistory.clear();
        dispose();
        new PlayerNames();
    }

    private void colorPossibleCells(CellButton button) {
        String coord = button.getName();
        int x = Integer.parseInt(coord.charAt(0)+"");
        int y = Integer.parseInt(coord.charAt(1)+"");
        Piece piece = board.pieceAtDest(x, y);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece otherCellPiece = board.pieceAtDest(i,j);
                if (piece != null && otherCellPiece == null && piece.canMove(i,j,board)) this.cellButtons[i][j].setBackground(Color.green);
                else if (piece != null && otherCellPiece != null && piece.canMove(i,j,board) && otherCellPiece.getWhite() != piece.getWhite()){
                    this.cellButtons[i][j].setBackground(Color.green);
                }
            }
        }
    }

    public void updateUI (Board board) {
        String c = FEN.translateToFEN(this.board, this.p1, this.p2);
        // grid
        grid.removeAll();
        this.makeGrid(board);
        grid.revalidate();
        grid.repaint();
        // menu
        menu.removeAll();
        this.createMenu();
        this.populateHistory(this.movesList);
        menu.revalidate();
        menu.repaint();
    }

    private void populateHistory(JTextArea movesList) {
        movesList.setText("");
        for(Move m: GameFlow.movesHistory) {
            movesList.append("\n" + m.cellMove());
        }
    }

    // FIXME fix the turn layout
    public void createMenu () {
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        turnPlayer.setText(GameFlow.whoseTurn(p1,p2));
        movesList.setEditable(false);
        menu.add(movesList);
        menu.add(turnPlayer);
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
}
