package backend;

public class Track {
    private final String name;
    private final double laptimeRecord;
    private final int amountLaps;
    private final Weather weather;

    public Track(String name, double laptimeRecord, int amountLaps, Weather weather) {
        this.name = name;
        this.laptimeRecord = laptimeRecord;
        this.amountLaps = amountLaps;
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public double getLaptimeRecord() {
        return laptimeRecord;
    }

    public int getAmountLaps() {
        return amountLaps;
    }

    public Weather getWeather() {
        return weather;
    }
}
