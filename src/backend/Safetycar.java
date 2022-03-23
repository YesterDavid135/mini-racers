package backend;

import backend.tyre.Tyre;

import java.util.ArrayList;

public class Safetycar extends Car {
    private int lapsDeployedLeft;

    public Safetycar(Driver driver, int startPosition, double laptimeReference, double racetimeTotal, ArrayList<Tyre> tyres) {
        super(driver, startPosition, laptimeReference, racetimeTotal, tyres);
        this.lapsDeployedLeft = 0;
    }

    public void deploySafetycar() {
        lapsDeployedLeft = (int) (Math.random() * (6 - 3)) + 3;
    }

    public void subtractLapsDeployedLeft() {
        lapsDeployedLeft--;
    }

    public int getLapsDeployedLeft() {
        return lapsDeployedLeft;
    }
}
