package test;

import api.ohne_name.networking.Client;
import api.ohne_name.networking.MessageInfo;
import api.ohne_name.networking.NetworkingAPI;
import api.ohne_name.networking.listener.NetworkClientListener;

import java.io.IOException;

public class Client2 implements NetworkClientListener {

    public static void main(String[] args) {
        new Client2();
    }

    Client2() {
        NetworkingAPI.addListener(this);
        Client client = NetworkingAPI.createClient(3345);
        try {
            client.connect();
            client.sendMessage("teeeeeeeest");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServerMessage(MessageInfo messageInfo) {
        //System.out.println("MESSAGE RECEIVED: " + messageInfo.getWholeMessage().toString());
        //System.exit(0);
    }
}
