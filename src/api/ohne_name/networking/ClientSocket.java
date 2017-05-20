package api.ohne_name.networking;

import java.net.Socket;

public class ClientSocket {

    private Socket socket;
    private String host;
    private int port;
    private Thread thread;

    ClientSocket(String host, int port, Socket socket) {
        this.host = host;
        this.port = port;
        this.socket = socket;
    }

    public Socket getSocket() {
        return this.socket;
    }
    public String getHost() {
        return this.host;
    }
    public int getPort() {
        return this.port;
    }

    public void sendMessage(String... messageParts) {
        if(messageParts.length > 0) {
            String toSend = "";
            for (String messagePart : messageParts) {
                toSend += messagePart + "/§§/";
            }
            toSend.substring(0, toSend.length() - 4);
        }
    }

}
