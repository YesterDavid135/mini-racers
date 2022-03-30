package backend;

import java.util.ArrayList;

public class Race {
    private final Track track;
    private final ArrayList<Car> cars;
    private final Safetycar safetycar;
    private final ArrayList<Car> crashedCars = new ArrayList<>();
    private ArrayList<Car> crashedCarsThisLap = new ArrayList<>();
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
        this.crashedCarsThisLap = new ArrayList<>();
        updateCarStats();
        updateDeltaList();
    }

    public void updateCarStats() {
        for (Car car : cars) {
            car.getDriver().updateStamina();
            car.updateFuel();
            car.updateTyres(car.getDriver().getSkill(), track.getWeather().getWeatherType());
            car.updateLaptime(track.getWeather(), isSafetycarDeployed(), safetycar.getSafetycarLaptimeMultiplier());
            car.updateCrashChance(track.getWeather());
            car.updateRacetimeTotal();
        }
        if (isSafetycarDeployed()) {
            safetycar.subtractLapsDeployedLeft();
            if (safetycar.getLapsDeployedLeft() < 1) {
                removeSafetycarFromCarList();
            }
        } else {
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
            if (isSafetycarDeployed()) {
                cars.get(i).updatePosition(i);
            } else {
                cars.get(i).updatePosition(i + 1);
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
        for (Car car : cars) {
            double crashChance = car.getCrashChance();
            double randomValue = Math.random() * 100;
            if (crashChance >= randomValue || car.getFuel() == 0) {
                crashedCarsThisLap.add(car);
                crashedCars.add(car);
            }
        }
        if (crashedCarsThisLap.size() > 0) deploySafetycar();

        for (Car car : crashedCarsThisLap) {
            cars.remove(car);
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

    public ArrayList<Car> getCrashedCars() {
        return crashedCars;
    }

    public ArrayList<Car> getCrashedCarsThisLap() {
        return crashedCarsThisLap;
    }

    public ArrayList<Double> getDeltaList() {
        return deltaList;
    }

    public Track getTrack() {
        return track;
    }
}
