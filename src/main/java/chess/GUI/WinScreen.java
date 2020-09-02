package chess.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinScreen extends JFrame{

    private JButton restartBtn;
    private JPanel panel;
    private JLabel winnerLabel;

    public WinScreen (String winner) {
        this.winnerLabel.setText(winner);
        setSize(400, 300);
        setLocation(700, 300);
        setDefaultCloseOperation(3); // EXIT_ON_CLOSE
        setVisible(true);
        add(panel);

        restartBtn.addActionListener(actionEvent -> {
            new PlayerNames();
            dispose();
        });
    }


}
