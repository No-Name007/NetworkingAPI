package test;

import api.ohne_name.networking.MessageInfo;
import api.ohne_name.networking.NetworkingAPI;
import api.ohne_name.networking.Server;
import api.ohne_name.networking.listener.NetworkServerListener;

import java.io.IOException;
import java.net.Socket;

public class Server2 implements NetworkServerListener {

    Server server;

    public static void main(String[] args) {
        new Server2();
    }

    Server2() {
        NetworkingAPI.addListener(this);
        try {
            server = NetworkingAPI.createServer(3345);
            server.start();
            System.out.println("Server started!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClientConnect(Socket socket) {
        System.out.println("CLIENT CONNECTED");
    }

    @Override
    public void onClientDisconnect(Socket socket) {
        System.out.println("CLIENT DISCONNECTED");
    }

    @Override
    public void onClientMessage(MessageInfo messageInfo) {
        System.out.println("MESSAGE RECEIVED: " + messageInfo.getWholeMessage().toString());

        server.sendMessage(messageInfo.getSender(), "A");
        //server.broadcastMessage("B");
    }
}
