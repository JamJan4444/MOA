package de.andrej.pvs;

import java.io.*;
import java.nio.channels.*;
import java.nio.*;

public class ChannelRW {

    // send a text message to the open connection
    static public void sendTextMessage(SocketChannel sChannel, String s) throws IOException {

        // our message format specifies '\n' terminated text
        if (s.charAt(s.length() - 1) != '\n')
            s = s + '\n';

        // Non blocking write
        sChannel.write(ByteBuffer.wrap(s.getBytes("UTF-8")));
    }

    // receive a text message from the open connection(non blocking)
    // side effect: sChannel is closed when connection has been shutdown from remote
    static public String recvTextMessage(SocketChannel sChannel) throws IOException {

        // Read bytes into a ByteBuffer
        ByteBuffer recvBuffer = ByteBuffer.allocate(1024);
        int numBytesRead = sChannel.read(recvBuffer);

        switch (numBytesRead) {
            // connection no longer open
            case (-1):
                throw new IOException("Connection unexpectedly closed");
                // no data arrived from remote
            case 0:
                return "";
            // data arrived and must be decoded
            default:
                if (recvBuffer.get(numBytesRead - 1) != 10) // �\n�
                    throw new IOException("Message Frame error");

                return new String(recvBuffer.array(), "utf-8").trim();
        }
    }

}
