package backend;

import java.util.ArrayList;

public class Race {
    private final Track track;
    private final ArrayList<Car> cars;
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

    public void updateDeltaList() {
        deltaList = new ArrayList<>();
        for (int i = 1; i < cars.size(); i++) {
            double delta = cars.get(i).getRacetimeTotal() - cars.get(i - 1).getRacetimeTotal();
            deltaList.add(delta);
        }
    }

    public void checkCrash() {
        ArrayList<Integer> crashedCarsIndex = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            double crashChance = cars.get(i).getCrashChance();
            double randomValue = Math.random() * 100;
            if (crashChance >= randomValue) {
                crashedCarsIndex.add(i);
                System.out.println(cars.get(i).getDriver().getName() + " crashed with " + lapsLeft + " laps left. Crash Probability: " + cars.get(i).getCrashChance());
            }
        }
        for (int crashedCarIndex : crashedCarsIndex) {
            //TODO: Fix IndexOutOfBounds bug
            cars.remove(cars.get(crashedCarIndex));
        }
    }

    public int getLapsLeft() {
        return lapsLeft;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public ArrayList<Double> getDeltaList() {
        return deltaList;
    }

    public Track getTrack() {
        return track;
    }
}
