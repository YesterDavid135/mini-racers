package backend;

public class LogEntry {

    private final String message;
    private final int lap;

    public LogEntry(String message, int lap) {
        this.message = message;
        this.lap = lap;
    }

    public String getMessage() {
        return message;
    }

    public int getLap() {
        return lap;
    }

}
