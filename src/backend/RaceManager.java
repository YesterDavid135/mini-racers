package backend;

import data.DataManager;

import java.util.ArrayList;

public class RaceManager {
    private final DataManager dataManager = new DataManager();;
    private final Race race;

    public RaceManager() {
        Track track = dataManager.readRandomTrack();
        ArrayList<Car> cars = dataManager.generateCars(track.getLaptimeRecord());
        this.race = new Race(track, cars);
    }

    public Race getRace() {
        return race;
    }
}
