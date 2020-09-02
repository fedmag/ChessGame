package chess.GUI;

import chess.src.GUIChess;

import javax.swing.*;

public class PlayerNames extends JFrame{
    private JPanel bigPanel;
    private JTextField whitePlayer;
    private JTextField blackPlayer;
    private JButton confirmBtn;
    private JButton cancelBtn;
    private JCheckBox AICheckBox;
    private String whiteName, blackName;


    public PlayerNames() {
        setSize(400, 300);
        setLocation(700, 300);
        setDefaultCloseOperation(3); // EXIT_ON_CLOSE
        setVisible(true);
        add(bigPanel);

        confirmBtn.addActionListener(actionEvent -> {
            if ((!whitePlayer.getText().equals("") && !blackPlayer.getText().equals(""))
                    || (!whitePlayer.getText().equals("") && this.AICheckBox.isSelected())) {
                if(this.AICheckBox.isSelected()) {
                    this.whiteName = whitePlayer.getText();
                    new GUIChess(this.whiteName, this.blackName); // in this case the second player name is set in the checkBox method
                }
                else {
                    this.whiteName = whitePlayer.getText();
                    this.blackName = blackPlayer.getText();
                    new GUIChess(whiteName, blackName);
                }
                dispose();
            }
        });

        cancelBtn.addActionListener(actionEvent -> {
            dispose();
        });

        AICheckBox.addActionListener(actionEvent -> {
            if (this.AICheckBox.isSelected()) {
                blackPlayer.setText("");
                blackPlayer.setEditable(false);
                this.blackName = "AI here!";
            }
            else blackPlayer.setEditable(true);
        });
    }

}
