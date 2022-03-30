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

    //TODO: Optimize method
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

    //TODO: Optimize method
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

    public ArrayList<Car> generateCars(double laptimeReference, WeatherType weatherType, int playerNumber) {
        ArrayList<Driver> drivers = readDrivers();
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < drivers.size(); i++) {
            Driver driver = drivers.get(i);
            if (driver.getNumber() == playerNumber) driver.setNumber(85);
            Car car = new Car(driver, i + 1, laptimeReference, i * 0.5, generateTyres(weatherType));
            cars.add(car);
        }
        return cars;
    }

    public Car generatePlayerCar(double laptimeReference, WeatherType weatherType, String playerName, int playerNumber, Difficulty playerDifficulty, ArrayList<Car> cars) {
        Driver driver = new Driver(playerName, playerNumber, playerDifficulty);
        return new Car(driver, cars.size(), laptimeReference, cars.get(cars.size() - 1).getRacetimeTotal() + 0.5, generateTyres(weatherType));
    }

    public Safetycar generateSafetycar(double laptimeReference, WeatherType weatherType) {
        Driver driver = readRandomSafetycarDriver();
        return new Safetycar(driver, 0, laptimeReference, 0, generateTyres(weatherType), 1.4);
    }

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

    public Tyre[] generateTyres(WeatherType weatherType) {
        Tyre tyres[] = new Tyre[4];
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

    //TODO: Implementation
    public void saveRaceData() {

    }

    //TODO: Implementation
    public void loadRaceData() {

    }
}
