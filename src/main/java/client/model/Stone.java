package client.model;

import client.graphics.StoneGraphics;
import server.model.entities.Color;

public class Stone {
    private final Color color;
    private final StoneGraphics stoneGraphics;
    public Stone(Color color, int x, int y) {
        this.color = color;
        stoneGraphics = new StoneGraphics(14 + 27 * x - 10, 14 + 27 * y - 10, color);
    }

    public StoneGraphics getStoneGraphics() {
        return stoneGraphics;
    }
}
