package api.ohne_name.networking.listener;

import api.ohne_name.networking.MessageInfo;

import java.net.Socket;

public interface NetworkServerListener {

    void onClientConnect(Socket socket);
    void onClientDisconnect(Socket socket);
    void onClientMessage(MessageInfo messageInfo);

}
