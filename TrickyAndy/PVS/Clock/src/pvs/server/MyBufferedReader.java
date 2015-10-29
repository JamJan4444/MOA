package pvs.server;

import java.io.InputStreamReader;

/**
 * Created by Andrej on 19.10.2015.
 */
public class MyBufferedReader {

    private InputStreamReader _isR;

    public MyBufferedReader() {

    }

    public MyBufferedReader(InputStreamReader _isR) {
        this._isR = _isR;
    }

    //<editor-fold desc="Getter & Setter">
    public InputStreamReader get_isR() {
        return _isR;
    }

    public void set_isR(InputStreamReader _isR) {
        this._isR = _isR;
    }
    //</editor-fold>

    public String readLine() {
        String read = "";

        try {
            int data = this.get_isR().read();
            while (data != -1) {
                char ch = (char) data;
                if (ch != '\n') {
                    read += ch;
                    data = this.get_isR().read();
                } else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return read;
    }

    public void close() {
        try {
            this.get_isR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
