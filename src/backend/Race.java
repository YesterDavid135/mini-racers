package backend;

import java.util.ArrayList;

public class Race {
    private final Track track;
    private final ArrayList<Car> cars;
    private final ArrayList<Car> crashedCars = new ArrayList<>();
    private ArrayList<Car> crashedCarsThisRound = new ArrayList<>();
    private ArrayList<Double> deltaList;
    private int lapsLeft;

    public Race(Track track, ArrayList<Car> cars) {
        this.track = track;
        this.cars = cars;
        this.lapsLeft = track.getAmountLaps();
        updateDeltaList();
    }

    public void nextLap() {
        lapsLeft--;
        updateCarStats();
        updateDeltaList();
    }

    public void updateCarStats() {
        for (Car car : cars) {
            car.getDriver().updateStamina();
            car.updateFuel();
            car.updateTyreCondition();
            car.updateLaptime(track.getWeather().getLaptimeMultiplier());
            car.updateCrashChance(track.getWeather().getRiskMultiplier());
            car.updateRacetimeTotal();
        }
        checkCrash();
        updateCarList();
        updateCarPositions();
    }

    public void updateCarList() {
        boolean isFinished = false;
        while (!isFinished) {
            boolean hasChanged = false;
            for (int i = 1; i < cars.size(); i++) {
                if (cars.get(i).getRacetimeTotal() < cars.get(i - 1).getRacetimeTotal()) {
                    Car tempCar = cars.get(i);
                    cars.set(i, cars.get(i - 1));
                    cars.set(i - 1, tempCar);
                    hasChanged = true;
                }
            }
            if (!hasChanged) {
                isFinished = true;
            }
        }
    }

    public void updateCarPositions() {
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).updatePosition(i + 1);
        }
    }

    public void updateDeltaList() {
        deltaList = new ArrayList<>();
        for (int i = 1; i < cars.size(); i++) {
            double delta = cars.get(i).getRacetimeTotal() - cars.get(i - 1).getRacetimeTotal();
            deltaList.add(delta);
        }
    }

    public void checkCrash() {
        this.crashedCarsThisRound = new ArrayList<>();
        for (Car car : cars) {
            double crashChance = car.getCrashChance();
            double randomValue = Math.random() * 100;
            if (crashChance >= randomValue) {
                crashedCarsThisRound.add(car);
                crashedCars.add(car);
                System.out.println(car.getDriver().getName() + " crashed with " + lapsLeft + " laps left. Crash Probability: " + car.getCrashChance());
            }
        }
        for (Car car : crashedCarsThisRound){
            cars.remove(car);
        }

    }

    public int getLapsLeft() {
        return lapsLeft;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public ArrayList<Car> getCrashedCars() {
        return crashedCars;
    }

    public ArrayList<Car> getCrashedCarsThisRound() {
        return crashedCarsThisRound;
    }

    public ArrayList<Double> getDeltaList() {
        return deltaList;
    }

    public Track getTrack() {
        return track;
    }
}
