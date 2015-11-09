package de.pvs;

import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Created by Andrej on 09.11.2015.
 */
public class Chat {

    HashMap<Channel,String> _user = null;

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

    public Object processMessage(SocketChannel sc, String message)
    {
        return null;
    }
}
