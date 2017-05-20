package api.ohne_name.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Server implements Runnable {

    private int port;
    private int backlog = -1;
    private InetAddress bindAddr = null;

    private ServerSocket serverSocket;
    private Thread serverThread;
    private HashMap<Socket, Map.Entry<SocketRunnable, Thread>> socketRunnables = new HashMap<>();

    Server(int port) {
        this.port = port;
    }
    Server(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
    }
    Server(int port, int backlog, InetAddress bindAddr) {
        this.port = port;
        this.backlog = backlog;
        this.bindAddr = bindAddr;
    }

    public void start() throws IOException {
        if(serverSocket != null && !serverSocket.isClosed()) {
            throw new IOException("The socket is already running!");
        }
        if(backlog != -1 && bindAddr != null) {
            serverSocket = new ServerSocket(port, backlog, bindAddr);
        } else if(backlog != -1) {
            serverSocket = new ServerSocket(port, backlog);
        } else {
            serverSocket = new ServerSocket(port);
        }
        startThread();
    }
    public void stop() {
        stopThread();
        clearSocketRunnables();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void sendMessage(Socket socket, String... messageParts) {
        if(socketRunnables.containsKey(socket)) {
            socketRunnables.get(socket).getKey().sendMessage(messageParts);
        }
    }
    public void broadcastMessage(String... messageParts) {
        for(Socket socket : socketRunnables.keySet()) {
            sendMessage(socket, messageParts);
        }
    }

    private void startThread() {
        if(serverThread == null || !serverThread.isAlive()) {
            serverThread = new Thread(this);
            serverThread.start();
        }
    }
    private void stopThread() {
        if(serverThread != null && serverThread.isAlive()) {
            serverThread.stop();
            serverThread.interrupt();
        }
        for(Socket socket : socketRunnables.keySet()) {
            socketRunnables.get(socket).getValue().interrupt();
        }
    }
    private void clearSocketRunnables() {
        socketRunnables.clear();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                NetworkingAPI.manager.onClientConnect(socket);
                SocketRunnable socketRunnable = new SocketRunnable(socket, true);
                Thread thread = new Thread(socketRunnable);
                thread.start();
                socketRunnables.put(socket, new AbstractMap.SimpleEntry<>(socketRunnable, thread));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
