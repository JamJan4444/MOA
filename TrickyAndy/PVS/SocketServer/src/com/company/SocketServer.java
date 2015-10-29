package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.io.*;

public class SocketServer {

    private ServerSocket _listenSocket;
    private Socket _talkSocket;
    private BufferedReader _bfrdReader;
    private OutputStreamWriter _result;
    private String _message;
    private int _style;
    private BigDecimal _price;
    private MyBufferedReader _mybfrdReader;

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

            if (this.get_message().equals("ENDE")) {
                return;
            } else if (this.get_message().equals("1") || this.get_message().equals("2")) {
                this.selectConvertingStyle(this.get_message());
                this.set_price(new BigDecimal(0));
            } else {
                this.calculatePrice();
            }

            this.returnMessageToClient();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnMessageToClient() {
        try {
            _result = new OutputStreamWriter(
                    _talkSocket.getOutputStream(), "Cp1252");

            this.convertWithConvertingStyle();

            _message = _message + "\t | \t" +
                    this.get_price().toString() + " € \n";
            _result.write(_message);
            _result.flush();
            System.out.println("send: " + _message);

            this.receiveMessageFromClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculatePrice() {
        Double p = _message.replaceAll(" ", "").length() * 0.015;
        this.set_price(new BigDecimal(p).setScale(2, RoundingMode.HALF_UP));
    }

    public void selectConvertingStyle(String p_style) {
        int style = Integer.parseInt(p_style);
        this.set_style(style);

        switch (this.get_style()) {
            case 1:
                this.set_message("Converting into BIG letters!");
                break;
            case 2:
                this.set_message("Converting into small letters!");
                break;
            default:
                break;

        }
    }

    public void convertWithConvertingStyle() {

        switch (this.get_style()) {
            case 1:
                this.set_message(this.get_message().toUpperCase());
                break;
            case 2:
                this.set_message(this.get_message().toLowerCase());
                break;
            default:
                break;
        }
    }

    public void shutdown() {
        try {
            _result.write("Server shutdown\n");
            _result.flush();

            _result.close(); // close writer
            _mybfrdReader.close(); // close reader
            _talkSocket.close();
            _listenSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SocketServer ss = new SocketServer();

        ss.init();

        ss.receiveMessageFromClient();


        ss.shutdown();
        System.out.print("\n\nServer shutting down!");
    }
}
