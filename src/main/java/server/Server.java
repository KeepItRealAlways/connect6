package server;

import server.model.Board;
import server.model.Outcome;
import server.model.entities.Color;
import server.model.entities.Placeable;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements GameLogic {
    private Board board = new Board();

    private int playerCount = 0;
    private Map<UUID, Color> colorMap = new HashMap<>();
    private UUID[] players = new UUID[2];

    private int moveCounter = 1;
    private int turn = -1;
    private boolean isEnded = false;
    private boolean isStarted = false;

    public static void main(String[] args) {
        try {
            Server obj = new Server();
            GameLogic stub = (GameLogic) UnicastRemoteObject.exportObject(obj, 666);
            Registry registry = LocateRegistry.createRegistry(666);
            registry.rebind("Server", stub);
            System.out.println("Server is ready!");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public Outcome makeMove(UUID playerId, int x, int y) {
        moveCounter++;
        if (moveCounter == 2) {
            moveCounter = 0;
            if (turn == 0) {
                turn = 1;
            } else if (turn == 1) {
                turn = 0;
            }
        }
        Outcome outcome = board.makeMove(colorMap.get(playerId), x, y);

        if (outcome == Outcome.BLACK_WIN || outcome == Outcome.WHITE_WIN || outcome == Outcome.DRAW) {
            isEnded = true;
            isStarted = false;
            moveCounter = 0;
            if (turn == 0) {
                turn = 1;
            } else if (turn == 1) {
                turn = 0;
            }
        }

        return outcome;
    }

    @Override
    public Color getColor(UUID playerId) {
        return colorMap.get(playerId);
    }

    @Override
    public UUID getId() {
        playerCount++;
        if (playerCount == 2) {
            startGame();
        }
        UUID id = UUID.randomUUID();
        players[playerCount-1] = id;
        return id;
    }

    private void startGame() {
        colorMap.put(players[0], Color.WHITE);
        colorMap.put(players[1], Color.BLACK);
        turn = 1;
        moveCounter = 1;
        board = new Board();
        isEnded = false;
        isStarted = true;
    }

    @Override
    public boolean isMyMove(UUID playerId) throws RemoteException {
        if (turn == -1) {
            return false;
        }
        return players[turn].equals(playerId);
    }

    @Override
    public boolean isStarted() throws RemoteException {
        return isStarted;
    }

    @Override
    public boolean isEnded() throws RemoteException {
        return isEnded;
    }

    @Override
    public void leave() {
        playerCount--;
    }

    @Override
    public Placeable[][] getBoard() throws RemoteException {
        return board.getBoard();
    }
}
