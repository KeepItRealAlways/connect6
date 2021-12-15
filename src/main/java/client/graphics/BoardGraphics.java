package client.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class BoardGraphics extends JPanel {
    Image img = (new ImageIcon("src\\images\\board.png")).getImage();

    public BoardGraphics() {}

    public void paint(Graphics g) {
        g.drawImage(this.img, 0, 0, null);
    }
}