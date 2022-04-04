package backend;

import backend.weather.Weather;

public class Track {
    private final String name;
    private final double laptimeRecord;
    private final int amountLaps;
    private final Weather weather;

    /**
     * Constructor of Track
     *
     * @param name          Name of track
     * @param laptimeRecord Alltime laptime record of track
     * @param amountLaps    Amount of laps in one race
     * @param weather       Weatherstate of the track
     */
    public Track(String name, double laptimeRecord, int amountLaps, Weather weather) {
        this.name = name;
        this.laptimeRecord = laptimeRecord;
        this.amountLaps = amountLaps;
        this.weather = weather;
    }

    /**
     * Getter of name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter of laptimeRecord
     *
     * @return laptime Record
     */
    public double getLaptimeRecord() {
        return laptimeRecord;
    }

    /**
     * getter of amountLaps
     *
     * @return amount of Laps
     */
    public int getAmountLaps() {
        return amountLaps;
    }

    /**
     * getter of weather
     *
     * @return weather
     */
    public Weather getWeather() {
        return weather;
    }
}
