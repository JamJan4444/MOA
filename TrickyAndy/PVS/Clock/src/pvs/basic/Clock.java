package pvs.basic;

public class Clock {

    final static int ST_READY = 0;
    final static int ST_RUNNING = 1;
    final static int ST_HALTED = 2;
    final static int ST_EXIT = 3;

    int state;          // one of {ST_READY, ST_HALT ... }
    long startTime;     // stores start time
    long elapsedTime;   // stores elapsed time when
    // halt command occurs

    // constructor
    public Clock() {
        state = ST_READY; // initial state is ST_READY
        startTime = 0;
        elapsedTime = 0;
    }

    // command execute methods
    public void start() throws IllegalCmdException {
        if (state != ST_READY)
            throw new IllegalCmdException
                    ("'start' not allowed in the actual context");
        startTime = System.currentTimeMillis();
        state = ST_RUNNING;
    }

    public void reset() throws IllegalCmdException {
        if ((state != ST_HALTED) && (state != ST_RUNNING))
            throw new IllegalCmdException
                    ("'reset' not allowed in the actual context");
        startTime = 0;
        elapsedTime = 0;
        state = ST_READY;
    }

    public long getTime() throws IllegalCmdException {
        if ((state != ST_HALTED) && (state != ST_RUNNING))
            throw new IllegalCmdException
                    ("'gettime' not allowed in the actual context");
        if (state == ST_RUNNING) {
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        return elapsedTime;
    }

    public void waitTime(long time) throws IllegalCmdException // wait for time ms
    {
        if (state != ST_RUNNING)
            throw new IllegalCmdException
                    ("'wait' not allowed in the actual context");
        try {
            Thread.sleep(time);
        } catch (Exception ignore) {
        }
    }

    public long halt() throws IllegalCmdException {
        if (state != ST_RUNNING)
            throw new IllegalCmdException
                    ("'halt' not allowed in the actual context"); // freeze the elapsed time
        elapsedTime = System.currentTimeMillis() - startTime;
        state = ST_HALTED;
        return elapsedTime;
    }

    public void conTinue() throws IllegalCmdException {
        if (state != ST_HALTED)
            throw new IllegalCmdException
                    ("'continue' not allowed in the actual context");
        startTime = System.currentTimeMillis() - elapsedTime;
        state = ST_RUNNING;
    }

    public void exit() throws IllegalCmdException {
        if (state != ST_READY)
            throw new IllegalCmdException
                    ("'exit' not allowed in the actual context");
        state = ST_EXIT; // no way out
    }

    public static void main(String[] args) {
        // write your code here
    }
}
