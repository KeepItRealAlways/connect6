package server.model.entities;

import java.io.Serializable;

public class Void implements Placeable, Serializable {
    public boolean isOccupied() {
        return false;
    }
}
