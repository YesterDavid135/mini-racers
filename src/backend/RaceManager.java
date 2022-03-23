package backend;

import data.DataManager;

import java.util.ArrayList;

public class RaceManager {
    private final DataManager dataManager = new DataManager();
    private final Race race;

    public RaceManager() {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord());
        Safetycar safetycar = dataManager.generateSafetycar(track.getLaptimeRecord());
        this.race = new Race(track, cars, safetycar);
    }

    public Race getRace() {
        return race;
    }

    public ArrayList<Car> getCrashedCarsThisRound(){
        return race.getCrashedCarsThisLap();
    }

    public int getLapsLeft(){
        return race.getLapsLeft();
    }
}
