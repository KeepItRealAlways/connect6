package client.graphics;

import server.model.entities.Color;

import javax.swing.*;
import java.awt.*;

public class StoneGraphics extends JPanel {
    private final Color color;
    private final int x;
    private final int y;
    Image img;

    public StoneGraphics(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void paint(Graphics g) {
        if (this.color == Color.WHITE) {
            this.img = (new ImageIcon("src\\images\\white-stone.gif")).getImage();
        } else {
            this.img = (new ImageIcon("src\\images\\black-stone.gif")).getImage();
        }
        g.drawImage(this.img, this.x, this.y, this);
    }
}

