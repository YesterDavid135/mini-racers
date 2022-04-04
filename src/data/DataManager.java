package data;

import backend.Car;
import backend.Driver;
import backend.Safetycar;
import backend.Track;
import backend.tyre.HardCompound;
import backend.tyre.SoftCompound;
import backend.tyre.Tyre;
import backend.tyre.WetCompound;
import backend.weather.Weather;
import backend.weather.WeatherType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DataManager {

    /**
     * read drivers from textfile
     *
     * @return list of drivers
     */
    public ArrayList<Driver> readDrivers() {
        ArrayList<Driver> drivers = new ArrayList<>();
        try {
            File file = new File("resources/drivers.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] driverInfo = scanner.nextLine().split(",");
                String driverName = driverInfo[0];
                int driverNumber = Integer.parseInt(driverInfo[1]);
                Driver driver = new Driver(driverName, driverNumber, generateDifficulty());
                drivers.add(driver);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Collections.shuffle(drivers);
        return drivers;
    }

    /**
     * read a random safetycar driver from textfile
     *
     * @return safetycar driver
     */
    public Driver readRandomSafetycarDriver() {
        ArrayList<Driver> drivers = new ArrayList<>();
        try {
            File file = new File("resources/safetycarDrivers.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] driverInfo = scanner.nextLine().split(",");
                String driverName = driverInfo[0];
                int driverNumber = Integer.parseInt(driverInfo[1]);
                Driver driver = new Driver(driverName, driverNumber, generateDifficulty());
                drivers.add(driver);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Collections.shuffle(drivers);
        return drivers.get(0);
    }

    /**
     * read random track from textfile
     *
     * @return track
     */
    public Track readRandomTrack() {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            File file = new File("resources/tracks.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] trackInfo = scanner.nextLine().split(",");
                String trackName = trackInfo[0];
                double trackLaptimeRecord = Double.parseDouble(trackInfo[1]);
                int amountLaps = Integer.parseInt(trackInfo[2]);
                Weather weather = generateRandomWeather();
                Track track = new Track(trackName, trackLaptimeRecord, amountLaps, weather);
                tracks.add(track);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Collections.shuffle(tracks);
        return tracks.get(0);
    }

    /**
     * generate cars
     *
     * @param laptimeReference laptime reference for all cars
     * @param weatherType      weatherstate
     * @param playerNumber     number of player
     * @return list of cars
     */
    public ArrayList<Car> generateCars(double laptimeReference, WeatherType weatherType, int playerNumber) {
        ArrayList<Driver> drivers = readDrivers();
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < drivers.size(); i++) {
            Driver driver = drivers.get(i);
            if (driver.getNumber() == playerNumber) driver.setNumber(85);
            Car car = new Car(driver, i + 1, laptimeReference, i * 0.5, generateTyres(weatherType), false);
            cars.add(car);
        }
        return cars;
    }

    /**
     * generate player car
     *
     * @param laptimeReference laptime reference
     * @param weatherType      weatherstate
     * @param playerName       name of player
     * @param playerNumber     number of player
     * @param playerDifficulty game difficulty
     * @param cars             list of cars
     * @return player car
     */
    public Car generatePlayerCar(double laptimeReference, WeatherType weatherType, String playerName, int playerNumber, Difficulty playerDifficulty, ArrayList<Car> cars) {
        Driver driver = new Driver(playerName, playerNumber, playerDifficulty);
        return new Car(driver, cars.size() + 1, laptimeReference, cars.get(cars.size() - 1).getRacetimeTotal() + 0.5, generateTyres(weatherType), true);
    }

    /**
     * generates safetycar
     *
     * @param laptimeReference laptime reference
     * @param weatherType      weatherstate
     * @return safetycar
     */
    public Safetycar generateSafetycar(double laptimeReference, WeatherType weatherType) {
        Driver driver = readRandomSafetycarDriver();
        return new Safetycar(driver, 0, laptimeReference, 0, generateTyres(weatherType), 1.4);
    }

    /**
     * generate random weatherstate
     *
     * @return weather object
     */
    public Weather generateRandomWeather() {
        double randomValue = Math.random();
        if (randomValue > 0.6) {
            return new Weather(WeatherType.DRY, 1);
        } else if (randomValue > 0.2) {
            return new Weather(WeatherType.CLOUDY, 1);
        } else {
            return new Weather(WeatherType.WET, 1.5);
        }
    }

    /**
     * generate random difficulty
     *
     * @return difficulty
     */
    public Difficulty generateDifficulty() {
        double randomValue = Math.random();
        if (randomValue < 0.3) {
            return Difficulty.EASY;
        } else if (randomValue < 0.6) {
            return Difficulty.INTERMEDIATE;
        } else if (randomValue < 0.9) {
            return Difficulty.HARD;
        } else {
            return Difficulty.HELL;
        }
    }

    /**
     * generate list of four tyres
     *
     * @param weatherType
     * @return list of tyres
     */
    public Tyre[] generateTyres(WeatherType weatherType) {
        Tyre[] tyres = new Tyre[4];
        if (weatherType == WeatherType.WET) {
            for (int i = 0; i < 4; i++) {
                tyres[i] = (new WetCompound());
            }
        } else {
            double randomValue = Math.random();
            if (randomValue > 0.5) {
                for (int i = 0; i < 4; i++) {
                    tyres[i] = (new SoftCompound());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    tyres[i] = (new HardCompound());
                }
            }
        }
        return tyres;
    }

}
