package pvs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Andrej on 27.10.2015.
 */
public class ParallelServer {
    ServerSocket listenSocket;

    // constructor puts server into listen mode
    public ParallelServer(int port) throws IOException {
        listenSocket = new ServerSocket(port);
    }

    // each connected client gets its own thread
    class ThreadHandler extends Thread {
        // each connected client gets its own thread
        Socket talkSocket;

        public ThreadHandler(Socket talkSocket) {
            this.talkSocket = talkSocket;
        }

        public void run() {
            service(talkSocket);
        }
    }

    class ThreadHelperSleeping extends Thread {

        Socket talksocket;

        ThreadHelperSleeping(Socket socket)
        {
            talksocket = socket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                OutputStreamWriter toClient = new OutputStreamWriter(
                        talksocket.getOutputStream(), "utf-8");
                    toClient.write("Ich schlafe\n");
                    toClient.flush();

                    Thread.sleep(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // service routine to serve an individual client
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
                ThreadHelperSleeping ths = new ThreadHelperSleeping(talkSocket);
                ths.start();
                //simulate server workload
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                }
                ths.stop();
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
            (new ThreadHandler(listenSocket.accept())).start();
        }
    }

    // create and start server
    public static void main(String args[]) {
        // create and start server
        try {
            new ParallelServer(81).serverLoop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
