package client;

import client.graphics.BoardGraphics;
import client.graphics.StoneGraphics;
import client.model.Board;
import server.GameLogic;
import server.model.Outcome;
import server.model.entities.Color;
import server.model.entities.Placeable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class Client extends JPanel {
    private static JFrame frame = new JFrame("Connect6");
    private static int click_x;
    private static int click_y;
    private static int x;
    private static int y;

    private static UUID id;
    private static Color myColor;
    private static Board board;

    private Client() {
    }

    public static void createGUI() {
        frame.setDefaultCloseOperation(3);
        frame.setSize(465, 480);
        frame.add(board.getBoardGraphics());
        frame.setVisible(true);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click_x = e.getX();
                click_y = e.getY();
            }
        });
    }

    public static void boardInit(GameLogic stub, UUID id, Color myColor) {
        board = new Board(frame, stub, id, myColor);
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(666);
            GameLogic stub = (GameLogic)registry.lookup("Server");

            id = stub.getId();

            while (!stub.isStarted()) {
                Thread.sleep(15L);
            }

            myColor = stub.getColor(id);

            if (myColor == Color.WHITE) {
                frame.setTitle("Игра Connect6 - Поле белых фишек");
            } else {
                frame.setTitle("Игра Connect6 - Поле черных фишек");
            }

            boardInit(stub, id, myColor);
            createGUI();

            while(true) {
                while (!stub.isMyMove(id)) {
                    Thread.sleep(15L);
                }

                if (stub.isEnded()) {
                    System.out.println("You lost");
                    stub.leave();
                    break;
                }

                board.updateBoard(frame, stub.getBoard());

                x = (click_x - 14 + 10) / 27;
                y = (click_y - 14 + 10) / 27 - 1;
                if (x >= 0 && x <= 18 && y >= 0 && y <= 18) {
                    Outcome outcome = board.makeMove(x, y);

                    if (outcome == Outcome.WHITE_WIN || outcome == Outcome.BLACK_WIN) {
                        System.out.println("You won");
                        stub.leave();
                        break;
                    }

                    click_x = -100;
                    click_y = -100;
                }
            }
            System.exit(0);
        } catch (Exception var5) {
            System.err.println("Client exception: " + var5.toString());
            var5.printStackTrace();
        }
    }
}
