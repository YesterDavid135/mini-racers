package backend.weather;

public class Weather {
    private final WeatherType weatherType;
    private final double riskMultiplier;

    /**
     * Constructor for Weather
     *
     * @param weatherType    current weather type
     * @param riskMultiplier multiplier for crash
     */
    public Weather(WeatherType weatherType, double riskMultiplier) {
        this.weatherType = weatherType;
        this.riskMultiplier = riskMultiplier;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public double getRiskMultiplier() {
        return riskMultiplier;
    }
}
