package de.pvs;

import java.net.Socket;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrej on 09.11.2015.
 */
public class Chat {

    HashMap<SocketChannel, String> _user = null;
    ArrayList<SocketChannel> _channelsToNotify;
    String _notifyMessage;

    //<editor-fold desc="Getter Setter">
    public ArrayList<SocketChannel> get_channelsToNotify() {
        return _channelsToNotify;
    }

    public void set_channelsToNotify(ArrayList<SocketChannel> _channelsToNotify) {
        this._channelsToNotify = _channelsToNotify;
    }

    public String get_notifyMessage() {
        return _notifyMessage;
    }

    public void set_notifyMessage(String _notifyMessage) {
        this._notifyMessage = _notifyMessage;
    }
    //</editor-fold>

    public Chat() {
        _user = new HashMap<SocketChannel, String>();
    }

    public void login(Channel c, String name) {
        _user.put(c, name);
    }

    public void logout() {
        // TODO logout + remove user
    }

    public void messageForUsers(SocketChannel sc, String message)
    {
        this.set_notifyMessage(this._user.get(sc) + ": " + message);
        this.set_channelsToNotify(new ArrayList<>(this._user.keySet()));
        this.get_channelsToNotify().remove(sc);
    }

    public void processMessage(SocketChannel sc, String message) {

        // client command
        if (message.substring(0, 1).equals("/")) {
            String[] pattern = message.split(" ");
            String command = pattern[0];
            String paramter = "";
            for (int i = 1; i < pattern.length; i++) {
                paramter += pattern[i];
            }

            switch (command) {
                case "/login":
                    this.login(sc, paramter);
                    break;
                case "/logout":
                    this.logout(sc);
                    break;
                case "/userlist":
                    this.getUserList(sc);
                    break;
                default:
                    // TODO error message CommandNotFound
            }
        }
        else //client message
        {
            this.messageForUsers(sc, message);
        }
    }
}
