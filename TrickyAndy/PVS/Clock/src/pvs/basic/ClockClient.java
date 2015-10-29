package pvs.basic;

/**
 * Created by Andrej on 21.10.2015.
 */

import pvs.client.Clock_stub;

import java.io.*;
import java.util.StringTokenizer;   // for command decoding
import java.util.regex.Pattern;     // and syntax validation

public class ClockClient {

    static final int CMD_START = 1;
    static final int CMD_STOP = 2;
    static final int CMD_EXIT = 3;
    static final int CMD_HALT = 4;
    static final int CMD_WAIT = 5;
    static final int CMD_CONTINUE = 6;
    static final int CMD_GETTIME = 7;
    static final int CMD_RESET = 8;
    static final int CMD_NOT_EXECUTED = 9;

    class Command {
        public int cmd; // stored command
        public long parameter; // stored parameter

        // create Command without parameter
        public Command(int cmd) {
            this.cmd = cmd;
        }

        // create Command with parameter
        public Command(int cmd, long parameter) {
            this.cmd = cmd;
            this.parameter = parameter;
        }
    }

    void display(String msg) // displays a messsage on the screen
    {
        System.out.println(" " + msg);
    }

    void prompt(String msg) // sends a prompt messsage for user (command) input
    {
        System.out.print(msg);
    }

    Command getCommand() // read syntactically correct command from keyboard
    {
        // allowed commands as regular expression
        Pattern commandSyntax =
                Pattern.compile("s|c|h|r|e|g|w +[1-9][0-9]*");
        String cmdText = null;
        BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                prompt("command [s|c|h|r|e|g|w]: ");
                cmdText = in.readLine();
                if (!commandSyntax.matcher(cmdText).matches())
                    throw new Exception();
                break; // leave loop here if correct command detected
            } catch (Exception e) {
                display("command syntax error");
            }
        }

        StringTokenizer st = new StringTokenizer(cmdText);
        switch (st.nextToken().charAt(0)) // first token=command
        {
            case 'c':
                return new Command(CMD_CONTINUE);
            case 'g':
                return new Command(CMD_GETTIME);
            case 's':
                return new Command(CMD_START);
            case 'w':
                return new Command(CMD_WAIT,
                        Long.parseLong(st.nextToken()));
            // second token = parameter
            case 'h':
                return new Command(CMD_HALT);
            case 'r':
                return new Command(CMD_RESET);
            default:
                return new Command(CMD_EXIT);
            // case 'e' and any other char
        }
    }

    void run() {
        Clock_stub clock = new Clock_stub();
        //Clock clock = new Clock();
        display("accepted commands:");
        display("s[tart] h[old] c[ontinue] r[eset])");
        display("g[et time] e[xit] w[ait] 4711\n");
        Command command;
        do {
            command = getCommand(); // from console

            clock.modifyClock(command.cmd, command.parameter);
            display(clock.get_result());
        } while (command.cmd != CMD_EXIT);

        clock.cleanUp();
    }

    public static void main(String[] args) {

        (new ClockClient()).run();
    }
}
