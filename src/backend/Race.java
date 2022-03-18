package backend;

import java.util.ArrayList;

public class Race {
    private final Track track;
    private final ArrayList<Car> cars;
    private final Safetycar safetycar;
    private ArrayList<Double> deltaList;
    private int lapsLeft;

    public Race(Track track, ArrayList<Car> cars, Safetycar safetycar) {
        this.track = track;
        this.cars = cars;
        this.safetycar = safetycar;
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
        if (isSafetycarDeployed()) {
            safetycar.subtractLapsDeployedLeft();
        } else {
            if (cars.get(0) == safetycar) {
                removeSafetycarFromCarList();
            }
            checkCrash();
        }
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
        ArrayList<Integer> crashedCarsIndex = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            double crashChance = cars.get(i).getCrashChance();
            double randomValue = Math.random() * 100;
            if (crashChance >= randomValue) {
                crashedCarsIndex.add(i);
                System.out.println(cars.get(i).getDriver().getName() + " crashed with " + lapsLeft + " laps left. Crash Probability: " + cars.get(i).getCrashChance());
                deploySafetycar();
                break;
            }
        }
        for (int crashedCarIndex : crashedCarsIndex) {
            //TODO: Fix IndexOutOfBounds bug
            cars.remove(cars.get(crashedCarIndex));
        }
    }

    public void deploySafetycar() {
        safetycar.deploySafetycar();
        addSafetycarToCarList();
    }

    public void addSafetycarToCarList() {
        cars.add(0, safetycar);
    }

    public void removeSafetycarFromCarList() {
        cars.remove(0);
    }

    public boolean isSafetycarDeployed() {
        return safetycar.getLapsDeployedLeft() > 0;
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
