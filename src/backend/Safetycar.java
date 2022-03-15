package backend;

public class Safetycar extends Car {
    private int amountLapsDeployed;

    public Safetycar(Driver driver, int startPosition, double laptimeReference, double racetimeTotal) {
        super(driver, startPosition, laptimeReference, racetimeTotal);
    }

    public int getAmountLapsDeployed() {
        return amountLapsDeployed;
    }
}
