package de.pvs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

public class MuxSerialServer {

    Selector events = null;
    ServerSocketChannel listenChannel;
    Chat chat;

    public static void main(String[] args) {
        // create and start server
        try {
            (new MuxSerialServer(4711)).serverLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MuxSerialServer(int port) throws IOException {

        events = Selector.open(); // provides Selector object
        listenChannel = ServerSocketChannel.open();
        listenChannel.configureBlocking(false);
        listenChannel.socket().bind(new InetSocketAddress(port));
        listenChannel.register(events, SelectionKey.OP_ACCEPT);
    }

    public void serverLoop() throws IOException {

        Iterator<SelectionKey> selKeys;
        // infinite server loop
        while (true) {
            events.select(); // blocks until event occurs

            // process all pending events (might be more than 1)
            selKeys = events.selectedKeys().iterator();
            while (selKeys.hasNext()) {
                // get the selection key for the next event ...
                SelectionKey selKey = selKeys.next();

                // ... and remove it from the list to indicate
                // that it is being processed
                selKeys.remove();

                if (selKey.isReadable()) {
                    // it is a "data are available to be read" event
                    processRead(selKey);

                } else if (selKey.isAcceptable()) {
                    // it is a "remote socket wants to connect" event
                    processAccept();

                } else {
                    System.out.println("Unknown event occured");
                }

            } // end while (selKeys.hasNext())
        }
    }

    // process OP_READ event
    void processRead(SelectionKey selKey) {

        // process OP_READ event
        SocketChannel talkChan = null;
        try {
            // get the channel with the read event
            talkChan = (SocketChannel) selKey.channel();
            String s = ChannelRW.recvTextMessage(talkChan);
            // simulate server workload
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }

            chat.processMessage(talkChan, s);

            for(SocketChannel sC: chat.get_channelsTonotify())
            {
                ChannelRW.sendTextMessage(sC, chat.get_notifyMessage());
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
            try {
                // always try to close talkChannel
                talkChan.close();
            } catch (IOException ignore) {
            }
        }
    }

    // process OP_ACCEPT event
    private void processAccept() {
        // process OP_ACCEPT event
        SocketChannel talkChannel = null;
        try {
            // The returned talkChannel is in blocking mode.
            talkChannel = listenChannel.accept();
            talkChannel.configureBlocking(false);
            talkChannel.register(events, SelectionKey.OP_READ);

        } catch (IOException e) {
            System.out.println(e.getMessage());

            try {
                // always try to close talkChannel
                talkChannel.close();
            } catch (IOException ignore) {
            }
        }

    }
}
