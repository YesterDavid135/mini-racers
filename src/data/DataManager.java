package data;

import backend.*;
import backend.tyre.HardCompound;
import backend.tyre.SoftCompound;
import backend.tyre.Tyre;
import backend.tyre.WetCompound;

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
                Driver driver = new Driver(driverName, driverNumber);
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
                Driver driver = new Driver(driverName, driverNumber);
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

    public ArrayList<Car> generateCars(double laptimeReference, String weatherType) {
        ArrayList<Driver> drivers = readDrivers();
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < drivers.size(); i++) {
            Car car = new Car(drivers.get(i), i + 1, laptimeReference, i * 0.5, generateTyres(weatherType));
            cars.add(car);
        }
        return cars;
    }

    public Safetycar generateSafetycar(double laptimeReference, String weatherType) {
        Driver driver = readRandomSafetycarDriver();
        return new Safetycar(driver, 0, laptimeReference, 0, generateTyres(weatherType));
    }

    public Weather generateRandomWeather() {
        double randomValue = Math.random();
        if (randomValue > 0.6) {
            return new Weather("Dry", 1, 1);
        } else if (randomValue > 0.2) {
            return new Weather("Cloudy", 1, 1);
        } else {
            return new Weather("Wet", 2, 1.05);
        }
    }

    public ArrayList<Tyre> generateTyres(String weatherType) {
        ArrayList<Tyre> tyres = new ArrayList<>();
        if (weatherType.equals("Wet")) {
            for (int i = 0; i < 4; i++) {
                tyres.add(new WetCompound());
            }
        } else {
            double randomValue = Math.random();
            if (randomValue > 0.5) {
                for (int i = 0; i < 4; i++) {
                    tyres.add(new SoftCompound());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    tyres.add(new HardCompound());
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
