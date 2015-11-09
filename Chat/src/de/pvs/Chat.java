package de.pvs;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrej on 09.11.2015.
 */
public class Chat {


    HashMap<Channel,String> _user = null;
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

    public Chat()
    {
        _user = new HashMap<Channel,String>();
    }

    public void login(Channel c, String name)
    {
        _user.put(c,name);
    }

    public void logout()
    {
        // TODO logout + remove user
    }

    public void processMessage(SocketChannel sc, String message)
    {

    }
}
