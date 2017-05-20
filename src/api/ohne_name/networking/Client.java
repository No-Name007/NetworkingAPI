package api.ohne_name.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private String host = "localhost";
    private InetAddress address = null;
    private int port;
    private Socket socket;

    private SocketRunnable socketRunnable;
    private Thread socketThread;

    Client(int port) {
        this.port = port;
    }
    Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() throws IOException {
        if(address != null) {
            socket = new Socket(address, port);
        } else {
            socket = new Socket(host, port);
        }
        socketRunnable = new SocketRunnable(socket, this);
        socketThread = new Thread(socketRunnable);
        socketThread.start();
    }
    public void disconnect() throws IOException {
        sendMessage("SYSTEM", "DISCONNECT");
        socket.close();
        socketThread.stop();
        socketThread.interrupt();
    }
    public Socket getSocket() {
        return this.socket;
    }
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public void sendMessage(String... messageParts) {
        socketRunnable.sendMessage(messageParts);
    }

}
