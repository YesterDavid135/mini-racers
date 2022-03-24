package starter;

import backend.RaceManager;
import gui.RaceView;

public class Application {

    public static void main(String[] args) {
        startRace();
    }

    public static void startRace() {
        RaceManager raceManager = new RaceManager();
        new RaceView(raceManager);
    }
}