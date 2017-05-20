package api.ohne_name.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketRunnable implements Runnable {

    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;

    private boolean isServer;
    private Client client;
    private Server server;

    // timeout at server has to be greater then at the client
    private int timeout = 10;

    SocketRunnable(Socket socket, Client client) {
        this.socket = socket;
        this.isServer = false;
        this.client = client;
        try {
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    SocketRunnable(Socket socket, Server server) {
        this.socket = socket;
        this.isServer = true;
        this.server = server;
        try {
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String... messageParts) {
        String message = "";
        for (String messagePart : messageParts) {
            message += messagePart + "/§§/";
        }
        message = message.substring(0, message.length() - 4);
        printWriter.println(message);
        printWriter.flush();
    }

    @Override
    public void run() {
        int sec_counter = 0, timeout_counter = 0;
        while (true) {
            sec_counter++;

            if (sec_counter == 10) {
                try {
                    if(socket.getInputStream().read() != -1) {
                        timeout_counter = 0;
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    timeout_counter++;
                    if (timeout_counter > timeout) {
                        // TODO disconnect client
                        NetworkingAPI.manager.onClientDisconnect(socket);
                        Thread.currentThread().stop();
                        Thread.currentThread().interrupt();
                    }
                }
                sec_counter = 0;
            }

            if (scanner.hasNext()) {
                MessageInfo messageInfo = new MessageInfo(socket, scanner.nextLine().split("/§§/"));
                if (messageInfo.getLength() == 2 && messageInfo.getPart(0).equals("SYSTEM") && messageInfo.getPart(1).equals("DISCONNECT")) {
                    NetworkingAPI.manager.onClientDisconnect(socket);
                    Thread.currentThread().interrupt();
                } else {
                    if (isServer) {
                        NetworkingAPI.manager.onClientMessage(messageInfo);
                    } else {
                        NetworkingAPI.manager.onServerMessage(messageInfo);
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

}
