package api.ohne_name.networking;

import api.ohne_name.networking.listener.NetworkClientListener;
import api.ohne_name.networking.listener.NetworkServerListener;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkListenerManager {

    private List<NetworkClientListener> clientListeners = new ArrayList<>();
    private List<NetworkServerListener> serverListeners = new ArrayList<>();

    void addListener(NetworkClientListener listener) {
        if (!clientListeners.contains(listener)) {
            clientListeners.add(listener);
        }
    }
    void removeListener(NetworkClientListener listener) {
        clientListeners.remove(listener);
    }
    void addListener(NetworkServerListener listener) {
        if (!serverListeners.contains(listener)) {
            serverListeners.add(listener);
        }
    }
    void removeListener(NetworkServerListener listener) {
        serverListeners.remove(listener);
    }


    // event methods

    void onClientConnect(Socket socket) {
        for(NetworkServerListener listener : serverListeners) {
            listener.onClientConnect(socket);
        }
    }
    void onClientDisconnect(Socket socket) {
        for(NetworkServerListener listener : serverListeners) {
            listener.onClientDisconnect(socket);
        }
    }
    void onClientMessage(MessageInfo messageInfo) {
        for(NetworkServerListener listener : serverListeners) {
            listener.onClientMessage(messageInfo);
        }
    }
    void onServerMessage(MessageInfo messageInfo) {
        for(NetworkClientListener listener : clientListeners) {
            listener.onServerMessage(messageInfo);
        }
    }

}
