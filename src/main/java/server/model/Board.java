package server.model;

import server.model.entities.Color;
import server.model.entities.Placeable;
import server.model.entities.Stone;
import server.model.entities.Void;

import java.io.Serializable;

public class Board implements Serializable {
    public static final int xSize = 19;
    public static final int ySize = 19;
    public static final int winCondition = 6;

    private Placeable[][] entities = new Placeable[xSize][ySize];

    public Board() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                entities[i][j] = new Void();
            }
        }
    }

    public Outcome makeMove(Color color, int x, int y) {
        if (!validateMove(x, y)) {
            return Outcome.IMPOSSIBLE_MOVE;
        }

        entities[x][y] = new Stone(color);

        return checkWin();
    }

    private boolean validateMove(int x, int y) {
        return x < xSize - 1 && x >= 0 && y < ySize - 1 && y >= 0 && !entities[x][y].isOccupied();
    }

    public Placeable[][] getBoard() {
        return entities;
    }

    private Outcome checkWin() {
        for (int i = 0; i < xSize - winCondition + 1; i++) {
            for (int j = 0; j < ySize - winCondition + 1; j++) {
                if (entities[i][j].isOccupied()) {
                    Color color = ((Stone) entities[i][j]).getColor();
                    if (checkXDirection(color, i, j)
                            || checkYDirection(color, i, j)
                            || checkDiagDirections(color, i, j)) {
                        if (color == Color.WHITE) {
                            return Outcome.WHITE_WIN;
                        } else {
                            return Outcome.BLACK_WIN;
                        }
                    }
                }
            }
        }
        return Outcome.NOTHING;
    }

    private boolean checkXDirection(Color color, int x, int y) {
        if (x + winCondition > xSize) {
            return false;
        }

        for (int i = 1; i < winCondition; i++) {
            if (!entities[x+i][y].isOccupied()) {
                return false;
            }

            if (((Stone) entities[x+i][y]).getColor() != color) {
                return false;
            }
        }

        return true;
    }

    private boolean checkYDirection(Color color, int x, int y) {
        if (y + winCondition > ySize) {
            return false;
        }

        for (int i = 1; i < winCondition; i++) {
            if (!entities[x][y+i].isOccupied()) {
                return false;
            }

            if (((Stone) entities[x][y+i]).getColor() != color) {
                return false;
            }
        }

        return true;
    }

    private boolean checkDiagDirections(Color color, int x, int y) {
        return checkLeftDiag(color, x, y) || checkRightDiag(color, x, y);
    }

    private boolean checkLeftDiag(Color color, int x, int y) {
        if (x - winCondition + 1 < 0 || y + winCondition > ySize) {
            return false;
        }

        for (int i = 1; i < winCondition; i++) {
            if (!entities[x-i][y+i].isOccupied()) {
                return false;
            }

            if (((Stone) entities[x-i][y+i]).getColor() != color) {
                return false;
            }
        }

        return true;
    }

    private boolean checkRightDiag(Color color, int x, int y) {
        if (x + winCondition > xSize || y + winCondition > ySize) {
            return false;
        }
        for (int i = 1; i < winCondition; i++) {
            if (!entities[x+i][y+i].isOccupied()) {
                return false;
            }

            if (((Stone) entities[x+i][y+i]).getColor() != color) {
                return false;
            }
        }

        return true;
    }
}
