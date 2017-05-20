package api.ohne_name.networking;

import api.ohne_name.networking.listener.NetworkClientListener;
import api.ohne_name.networking.listener.NetworkServerListener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NetworkingAPI {

    static NetworkListenerManager manager = new NetworkListenerManager();

    public static void addListener(NetworkClientListener clientListener) {
        manager.addListener(clientListener);
    }
    public static void removeListener(NetworkClientListener clientListener) {
        manager.removeListener(clientListener);
    }
    public static void addListener(NetworkServerListener serverListener) {
        manager.addListener(serverListener);
    }
    public static void removeListener(NetworkServerListener serverListener) {
        manager.removeListener(serverListener);
    }


    public static Server createServer(int port) throws IOException {
        Server server = new Server(port);
        return server;
    }
    public static Server createServer(int port, int backlog) throws IOException {
        Server server = new Server(port, backlog);
        return server;
    }
    public static Server createServer(int port, int backlog, InetAddress bindAddr) throws IOException {
        Server server = new Server(port, backlog, bindAddr);
        return server;
    }

    public static Client createClient(int port) {
        Client client = new Client(port);
        return client;
    }
    public static Client createClient(String host, int port) {
        Client client = new Client(host, port);
        return client;
    }
    public static Client createClient(InetAddress address, int port) {
        Client client = new Client(address, port);
        return client;
    }

}
