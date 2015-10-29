package pvs.asyncclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Andrej on 27.10.2015.
 */
public class AsyncClient {

    private Socket talkSocket;
    private Scanner scn;
    private static String option = "";

    // autonomous thread for receive operation
    class ThreadHandler extends Thread {
        // autonomous thread for receive operation
        public void run() {
            try {
                recvAndProcessAsync();
            } catch (Exception e) {
                // very simply exception handling, should be more
                System.out.println(e.getMessage());
            }
        }
    }

    void recvAndProcessAsync() throws IOException {
        // receive part of the client/server dialog
        BufferedReader fromServer = new BufferedReader(
                new InputStreamReader(
                        talkSocket.getInputStream(), "utf-8"));
        String result;
        while (true) {
            option = fromServer.readLine();
            if(option.equals("Zeit abgelaufen"))
            {
                System.out.print("Aufwachen\n");
                System.out.print("Wecke mich in: ");
            }
            else {
                System.out.println(option);
            }
        }
    }

    class ThreadHelperSleeping extends Thread {
        @Override
        public void run() {

            while (true) {
                if(option.equals("OK")) {
                    System.out.print("Ich schlafe\n");
                }
                try {
                    Thread.sleep(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // send part of the client/server dialogue
    void talk() throws IOException {
        // send part of the client/server dialog
        OutputStreamWriter toServer = new OutputStreamWriter(
                talkSocket.getOutputStream(), "utf-8");
        String s = null;
        System.out.print("Wecke mich in: ");
        while (true) {
            scn = new Scanner(System.in);
            s = scn.nextLine();
            if (s.length() == 0) break;

            int ms = Integer.parseInt(s);
            ms *= 1000;
            s = String.valueOf(ms);

            // empty line => leave loop => stop client
            System.out.print(" Sekunden\n");
            toServer.write(s + '\n');
            toServer.flush(); // force message to be sent
        }
    }

    public void run() {
        try {
            talkSocket = new Socket("localhost", 81);
            (new ThreadHandler()).start(); // for async. receive
            (new ThreadHelperSleeping()).start(); //sleep message
            talk(); // for send only
            talkSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        (new AsyncClient()).run();
    }
}
