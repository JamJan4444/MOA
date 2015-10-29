package pvs.client;

import pvs.basic.IllegalCmdException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Andrej on 21.10.2015.
 */
public class Clock_stub {

    static final int CMD_START = 1;
    static final int CMD_STOP = 2;
    static final int CMD_EXIT = 3;
    static final int CMD_HALT = 4;
    static final int CMD_WAIT = 5;
    static final int CMD_CONTINUE = 6;
    static final int CMD_GETTIME = 7;
    static final int CMD_RESET = 8;
    static final int CMD_NOT_EXECUTED = 9;

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
    //endregion

    public Clock_stub() {
        this.init();
    }

    public void modifyClock(int command, long parameter)
    {
        this.set_message(String.valueOf(command)+String.valueOf(parameter));
        writeMessageToServer();
    }

    public void init() {

        try {
            _message = "";
            _result = "";

            _scn = new Scanner(System.in);
            _talkSocket = new Socket("localhost", 4711);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeMessageToServer() {

        try {
            if (!_message.contains("\n")) {
                _message += "\n";
            }

            _bfrdReader = new BufferedReader(new InputStreamReader(_talkSocket.getInputStream(), "Cp1252"));
            _outStrmWriter = new OutputStreamWriter(_talkSocket.getOutputStream(), "Cp1252");
            _outStrmWriter.write(_message);
            _outStrmWriter.flush();

            this.receiveMessageFromServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveMessageFromServer() {

        try {
            _result = _bfrdReader.readLine();
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

        Clock_stub sc = new Clock_stub();

        while (!sc.get_result().equals("Server shutdown")) {
            sc.writeMessageToServer();
            sc.receiveMessageFromServer();
        }

        sc.cleanUp();
    }
}
