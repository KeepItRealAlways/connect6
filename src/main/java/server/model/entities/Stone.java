package server.model.entities;

import java.io.Serializable;

public class Stone implements Placeable, Serializable {
    private final Color color;

    public Stone(Color color) {
        this.color = color;
    }

    public boolean isOccupied() {
        return true;
    }

    public Color getColor() {
        return color;
    }
}
