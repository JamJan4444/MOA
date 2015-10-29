package com.company;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SocketClient {

    private Scanner _scn;
    private Socket _talkSocket;
    private BufferedReader _bfrdReader;
    private OutputStreamWriter _outStrmWriter;
    private String _message;
    private String _result;

    //region Setter
    public void set_scn(Scanner _scn) {
        this._scn = _scn;
    }

    public void set_talkSocket(Socket _talkSocket) {
        this._talkSocket = _talkSocket;
    }

    public void set_bfrdReader(BufferedReader _bfrdReader) {
        this._bfrdReader = _bfrdReader;
    }

    public void set_outStrmWriter(OutputStreamWriter _outStrmWriter) {
        this._outStrmWriter = _outStrmWriter;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public void set_result(String _result) {
        this._result = _result;
    }
    //endregion

    //region Getter
    public Scanner get_scn() {
        return _scn;
    }

    public Socket get_talkSocket() {
        return _talkSocket;
    }

    public BufferedReader get_bfrdReader() {
        return _bfrdReader;
    }

    public OutputStreamWriter get_outStrmWriter() {
        return _outStrmWriter;
    }

    public String get_message() {
        return _message;
    }

    public String get_result() {
        return _result;
    }

    public SocketClient() {
        this.init();
    }
    //endregion

    public void init() {

        try
        {
            _message = "";
            _result = "";

            _scn = new Scanner(System.in);
            _talkSocket = new Socket("localhost", 4711);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeMessageToServer() {

        try {

            System.out.print("\nGeben Sie den Text ein: (1/2 für Groß-/Kleinkonvertierung))");
            _message = _scn.nextLine();

            if(!_message.contains("\n"))
            {
                _message += "\n";
            }

            _bfrdReader = new BufferedReader(new InputStreamReader(_talkSocket.getInputStream(), "Cp1252"));
            _outStrmWriter = new OutputStreamWriter(_talkSocket.getOutputStream(), "Cp1252");
            _outStrmWriter.write(_message);
            _outStrmWriter.flush();
            System.out.print("\nSend: " + _message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void receiveMessageFromServer() {

        try {
            _result = _bfrdReader.readLine();

            System.out.print("Received: " + _result + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanUp() {

        try {
            _bfrdReader.close();
            _outStrmWriter.close();
            _talkSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SocketClient sc = new SocketClient();

        while (!sc.get_result().equals("Server shutdown")) {
            sc.writeMessageToServer();
            sc.receiveMessageFromServer();
        }

        sc.cleanUp();
    }
}
