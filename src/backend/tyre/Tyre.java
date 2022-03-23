package backend.tyre;

public abstract class Tyre {
    private double tyreCondition; //100% = 1.0, 0% = 0.0
    private double compoundInfluence;
    private boolean isRainTyre;

    public Tyre() {
        this.tyreCondition = 1;
    }

    public abstract void updateTyreCondition(double driverSkill);

    public double getTyreCondition() {
        return tyreCondition;
    }

    public void setTyreCondition(double tyreCondition) {
        this.tyreCondition = tyreCondition;
    }

    public double getCompoundInfluence() {
        return compoundInfluence;
    }

    public void setCompoundInfluence(double compoundInfluence) {
        this.compoundInfluence = compoundInfluence;
    }

    public boolean isRainTyre() {
        return isRainTyre;
    }

    public void setRainTyre(boolean rainTyre) {
        isRainTyre = rainTyre;
    }
}
