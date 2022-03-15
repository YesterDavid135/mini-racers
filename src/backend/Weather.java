package backend;

public class Weather {
    private final String weatherType;
    private final double riskMultiplier;
    private final double laptimeMultiplier;

    public Weather(String weatherType, double riskMultiplier, double laptimeMultiplier) {
        this.weatherType = weatherType;
        this.riskMultiplier = riskMultiplier;
        this.laptimeMultiplier = laptimeMultiplier;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public double getRiskMultiplier() {
        return riskMultiplier;
    }

    public double getLaptimeMultiplier() {
        return laptimeMultiplier;
    }
}
