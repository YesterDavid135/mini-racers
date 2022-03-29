package starter;

import backend.RaceManager;
import gui.StartupView;

public class Application {

    public static void main(String[] args) {
        startRace();
    }

    public static void startRace() {
        new StartupView();
    }
}