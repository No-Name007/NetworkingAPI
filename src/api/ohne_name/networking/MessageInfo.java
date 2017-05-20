package api.ohne_name.networking;

import java.net.Socket;

public class MessageInfo {

    private Socket sender;
    private final int length;
    private final String[] parts;

    MessageInfo(Socket sender, String[] parts) {
        this.sender = sender;
        this.parts = parts;
        length = this.parts.length;
    }

    public Socket getSender() {
        return this.sender;
    }
    public int getLength() {
        return this.length;
    }
    public String getPart(int i) {
        return parts[i];
    }
    public String[] getWholeMessage() {
        return this.parts;
    }

}
