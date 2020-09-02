package chess.GUI;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {
    private int x, y;

    public CellButton (int x, int y) {
        super();
        this.x = x;
        this.y = y;
        colorButton();
    }

    private void colorButton() {
        if (this.x %2 == 0) {
            if (this.y %2 == 0) this.setBackground(new Color(222, 222, 186));
            else this.setBackground(new Color(133, 194, 224));
        }else {
            if (this.y %2 ==0) this.setBackground(new Color(133, 194, 224));
            else this.setBackground(new Color(222, 222, 186));
        }
    }

    public int getCoordX() {
        return this.x;
    }

    public int getCoordY() {
        return this.y;
    }
}
