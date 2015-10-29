package pvs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    ServerSocket listenSocket;

    // constructor puts server into listen mode
    public SerialServer(int port) throws IOException {
        listenSocket = new ServerSocket(port);
    }

    // service routine to serve the client
    void service(Socket talkSocket) {
        // service routine to serve the client
        String stringToConvert = null;
        try {
            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(
                            talkSocket.getInputStream(), "utf-8"));
            OutputStreamWriter toClient = new OutputStreamWriter(
                    talkSocket.getOutputStream(), "utf-8");
            while (true) {
                stringToConvert = fromClient.readLine();
                long sleepTime = Long.parseLong(stringToConvert);
                toClient.write("OK\n");
                toClient.flush();
                //simulate server workload
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                }

                toClient.write("Zeit abgelaufen\n");
                toClient.flush();
            }
        } catch (Exception ignore) {
        }
        try {
            talkSocket.close();
        } catch (Exception ignore) {
        }
    }

    // infinite server loop
    void serverLoop() throws IOException {
        // infinite server loop
        while (true) {
            // wait for client (blocking) and serve
            service(listenSocket.accept());
        }
    }

    // create and start server
    public static void main(String args[]) {
        // create and start server
        try {
            new SerialServer(81).serverLoop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
