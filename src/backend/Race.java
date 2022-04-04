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

    /**
     * Contructor of Race
     *
     * @param track     Racetrack, which is being used
     * @param cars      cars, which participate in the race
     * @param safetycar safetycar of current race
     */
    public Race(Track track, ArrayList<Car> cars, Safetycar safetycar) {
        this.track = track;
        this.cars = cars;
        this.safetycar = safetycar;
        this.lapsLeft = track.getAmountLaps();
        updateDeltaList();
    }

    /**
     * skip to next lap
     */
    public void nextLap() {
        lapsLeft--;
        this.crashedCarsThisLap = new ArrayList<>();
        updateCarStats();
        updateDeltaList();
    }

    /**
     * update car stats such as fuel, tyres or laptime
     */
    public void updateCarStats() {
        Car lastcar = null;
        for (Car car : cars) {
            car.getDriver().updateStamina();
            car.updateFuel();
            car.updateTyres(car.getDriver().getSkill(), track.getWeather().getWeatherType());
            car.updateLaptime(track.getWeather(), isSafetycarDeployed(), safetycar.getSafetycarLaptimeMultiplier(), lastcar);
            car.updateCrashChance(track.getWeather());
            car.updateRacetimeTotal();
            lastcar = car;
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

    /**
     * update cars list
     */
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

    /**
     * update positions of cars
     */
    public void updateCarPositions() {
        for (int i = 0; i < cars.size(); i++) {
            if (isSafetycarDeployed()) {
                cars.get(i).updatePosition(i);
            } else {
                cars.get(i).updatePosition(i + 1);
            }
        }
    }

    /**
     * update values in deltalist
     */
    public void updateDeltaList() {
        deltaList = new ArrayList<>();
        for (int i = 1; i < cars.size(); i++) {
            double delta = cars.get(i).getRacetimeTotal() - cars.get(i - 1).getRacetimeTotal();
            deltaList.add(delta);
        }
    }

    /**
     * calculate crashes of cars
     */
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

    /**
     * deploy Safetycar on track
     */
    public void deploySafetycar() {
        safetycar.deploySafetycar();
        addSafetycarToCarList();
    }

    /**
     * add safetycar to carlist
     */
    public void addSafetycarToCarList() {
        cars.add(0, safetycar);
    }

    /**
     * remove safetycar from carlist
     */
    public void removeSafetycarFromCarList() {
        cars.remove(0);
    }

    /**
     * is safetycar deployed
     *
     * @return isSafetycarDeployed
     */
    public boolean isSafetycarDeployed() {
        return safetycar.getLapsDeployedLeft() > 0;
    }

    /**
     * Getter of lapsLeft
     *
     * @return racelaps left
     */
    public int getLapsLeft() {
        return lapsLeft;
    }

    /**
     * Getter of cars
     *
     * @return list of cars
     */
    public ArrayList<Car> getCars() {
        return cars;
    }

    /**
     * Getter of crashedCars
     *
     * @return list of crashed cars
     */
    public ArrayList<Car> getCrashedCars() {
        return crashedCars;
    }

    /**
     * Getter of crashedCarsThisLap
     *
     * @return list of cars, which crashed in the current lap
     */
    public ArrayList<Car> getCrashedCarsThisLap() {
        return crashedCarsThisLap;
    }

    /**
     * Getter of deltaList
     *
     * @return list of delta values
     */
    public ArrayList<Double> getDeltaList() {
        return deltaList;
    }

    /**
     * Getter of track
     *
     * @return track
     */
    public Track getTrack() {
        return track;
    }
}
