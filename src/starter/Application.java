package starter;

import backend.RaceManager;
import gui.Gui;
import gui.Log;

public class Application {

    public static void main(String[] args) {
        startRace();
    }

    public static void startRace() {
        RaceManager raceManager = new RaceManager();
        new Gui(raceManager);
    }
}