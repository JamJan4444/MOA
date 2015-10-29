package pvs.server;

import pvs.basic.Clock;
import pvs.basic.IllegalCmdException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;

public class ClockServer {

    static final int CMD_START = 1;
    static final int CMD_STOP = 2;
    static final int CMD_EXIT = 3;
    static final int CMD_HALT = 4;
    static final int CMD_WAIT = 5;
    static final int CMD_CONTINUE = 6;
    static final int CMD_GETTIME = 7;
    static final int CMD_RESET = 8;
    static final int CMD_NOT_EXECUTED = 9;

    private ServerSocket _listenSocket;
    private Socket _talkSocket;
    private BufferedReader _bfrdReader;
    private OutputStreamWriter _result;
    private String _message;
    private int _style;
    private BigDecimal _price;
    private MyBufferedReader _mybfrdReader;
    private long _time;

    public long get_Time() {
        return _time;
    }

    public void set_Time(long time) {
        this._time = time;
    }

    private Clock clock;

    //region Setter
    public void set_listenSocket(ServerSocket _listenSocket) {
        this._listenSocket = _listenSocket;
    }

    public void set_talkSocket(Socket _talkSocket) {
        this._talkSocket = _talkSocket;
    }

    public void set_bfrdReader(BufferedReader _bfrdReader) {
        this._bfrdReader = _bfrdReader;
    }

    public void set_result(OutputStreamWriter _result) {
        this._result = _result;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public void set_style(int _style) {
        this._style = _style;
    }

    public void set_price(BigDecimal _price) {
        this._price = _price;
    }

    //endregion

    //region Getter
    public ServerSocket get_listenSocket() {
        return _listenSocket;
    }

    public Socket get_talkSocket() {
        return _talkSocket;
    }

    public BufferedReader get_bfrdReader() {
        return _bfrdReader;
    }

    public OutputStreamWriter get_result() {
        return _result;
    }

    public String get_message() {
        return _message;
    }

    public int get_style() {
        return _style;
    }

    public BigDecimal get_price() {
        return _price;
    }
    //endregion

    public void init() {
        try {
            System.out.println("Server started...");
            _listenSocket = new ServerSocket(4711);
            _talkSocket = _listenSocket.accept();
            System.out.println("Connection request from address " + this.get_talkSocket().getInetAddress() + " port " + this.get_talkSocket().getPort());
            _message = "";
            clock = new Clock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveMessageFromClient() {
        try {
            _mybfrdReader = new MyBufferedReader(new
                    InputStreamReader(_talkSocket.getInputStream(), "Cp1252"));

            _message = _mybfrdReader.readLine();
            System.out.println("received: " + _message);

            if (this.get_message().equals(""))
                this.set_message("0");

            this.modifyClock();

            /*
            if (this.get_message().equals("ENDE")) {
                return;
            } else if (this.get_message().equals("1") || this.get_message().equals("2")) {
                this.selectConvertingStyle(this.get_message());
                this.set_price(new BigDecimal(0));
            } else {
                this.calculatePrice();
            }
            */
            this.returnMessageToClient();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyClock() {

        if (this.get_message().length() > 1)
            this.set_Time(Long.parseLong(this.get_message().substring(1)));

        try {
            switch (Integer.parseInt(this.get_message().substring(0, 1))) {

                case CMD_START:
                    clock.start();
                    this.set_message("clock started");
                    break;
                case CMD_CONTINUE:
                    clock.conTinue();
                    this.set_message("clock continued");
                    break;
                case CMD_GETTIME:
                    this.set_message("elapsed time = ");
                    this.set_message(get_message() + String.valueOf(clock.getTime()));
                    this.set_message(get_message() + "ms");
                    break;
                case CMD_HALT:
                    this.set_message("clock halted, elapsed time = ");
                    this.set_message(get_message() + String.valueOf(clock.halt()));
                    this.set_message(get_message() + "ms");
                    break;
                case CMD_RESET:
                    clock.reset();
                    this.set_message("clock reset");
                    break;
                case CMD_WAIT:
                    clock.waitTime(this.get_Time());
                    this.set_message("wait finished");
                    break;
                case CMD_EXIT:
                    clock.exit();
                    clock = new Clock();
                    this.set_message("program stop");
                    break;
                default:
                    this.set_message("Illegal command");
            }
        } catch (IllegalCmdException e) {
            set_message(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            set_message(e.getMessage());
            e.printStackTrace();
        } finally {
            this.returnMessageToClient();
        }
    }

    public void returnMessageToClient() {
        try {
            _result = new OutputStreamWriter(
                    _talkSocket.getOutputStream(), "Cp1252");

            if (!_message.contains("\n")) {
                _message += "\n";
            }

            _result.write(_message);
            _result.flush();
            System.out.println("send: " + _message);

            this.receiveMessageFromClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            _result = new OutputStreamWriter(
                    _talkSocket.getOutputStream(), "Cp1252");
            _result.write("Server shutdown\n");
            _result.flush();

            _result.close(); // close writer
            _mybfrdReader.close(); // close reader
            _talkSocket.close();
            _listenSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("\n\nServer shutting down!");
        System.exit(0);
    }

    public static void main(String[] args) {

        ClockServer ss = new ClockServer();

        ss.init();

        ss.receiveMessageFromClient();


        ss.shutdown();

    }
}
