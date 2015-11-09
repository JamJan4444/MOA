package de.pvs;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrej on 09.11.2015.
 */
public class Chat {

    ArrayList<SocketChannel> _channelsToNotify;
    String _notifyMessage;
    HashMap<?,?> benutzerliste;

    //<editor-fold desc="Getter Setter">
    public ArrayList<SocketChannel> get_channelsTonotify() {
        return _channelsToNotify;
    }

    public void set_channelsTonotify(ArrayList<SocketChannel> _channelsToNotify) {
        this._channelsToNotify = _channelsToNotify;
    }

    public String get_notifyMessage() {
        return _notifyMessage;
    }

    public void set_notifyMessage(String _notifyMessage) {
        this._notifyMessage = _notifyMessage;
    }
    //</editor-fold>

    public Chat()
    {
        // TODO Construct
    }

    public void login()
    {
        // TODO login + add user
    }

    public void logout()
    {
        // TODO logout + remove user
    }

    public void processMessage(SocketChannel sc, String message)
    {

    }
}
