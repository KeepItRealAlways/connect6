package client.model;

import client.graphics.BoardGraphics;
import client.graphics.StoneGraphics;
import server.GameLogic;
import server.model.Outcome;
import server.model.entities.Color;
import server.model.entities.Placeable;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.UUID;

import static server.model.Board.xSize;
import static server.model.Board.ySize;

public class Board {
    private GameLogic stub;
    private UUID id;
    private Color myColor;
    private Frame frame;

    private BoardGraphics boardGraphics = new BoardGraphics();
    private Stone[][] stones = new Stone[xSize][ySize];

    public Board(Frame frame, GameLogic stub, UUID id, Color myColor) {
        this.frame = frame;
        this.stub = stub;
        this.id = id;
        this.myColor = myColor;
    }

    public Outcome makeMove(int x, int y) throws RemoteException {
        if (stones[x][y] == null) {
            stones[x][y] = new Stone(myColor, x, y);
            frame.add(stones[x][y].getStoneGraphics());
            frame.setVisible(true);
            Outcome outcome = stub.makeMove(id, x, y);
            if (outcome == Outcome.IMPOSSIBLE_MOVE) {
                frame.remove(stones[x][y].getStoneGraphics());
            }
            return outcome;
        }

        return Outcome.IMPOSSIBLE_MOVE;
    }

    public void updateBoard(Frame frame, Placeable[][] entities) {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (entities[i][j].isOccupied()) {
                    stones[i][j] = new Stone(((server.model.entities.Stone)entities[i][j]).getColor(), i, j);
                    frame.add(stones[i][j].getStoneGraphics());
                    frame.setVisible(true);
                }
            }
        }
    }

    public BoardGraphics getBoardGraphics() {
        return boardGraphics;
    }
}
