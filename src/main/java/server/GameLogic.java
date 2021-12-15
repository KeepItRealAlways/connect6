package server;

import server.model.Board;
import server.model.Outcome;
import server.model.entities.Color;
import server.model.entities.Placeable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface GameLogic extends Remote {
    Outcome makeMove(UUID playerId, int x, int y) throws RemoteException;
    Color getColor(UUID playerId) throws RemoteException;
    UUID getId() throws RemoteException;
    void leave() throws RemoteException;
    boolean isMyMove(UUID playerId) throws RemoteException;
    boolean isStarted() throws RemoteException;
    boolean isEnded() throws RemoteException;
    Placeable[][] getBoard() throws RemoteException;
}
