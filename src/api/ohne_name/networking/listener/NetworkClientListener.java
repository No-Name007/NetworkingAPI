package api.ohne_name.networking.listener;

import api.ohne_name.networking.MessageInfo;

public interface NetworkClientListener {

    void onServerMessage(MessageInfo messageInfo);

}
