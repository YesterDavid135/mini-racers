package backend.tyre;

public abstract class Tyre {
    private double tyreCondition; //100% = 1.0, 0% = 0.0
    private double compoundInfluence;
    private final TyreType tyreType;

    public Tyre(TyreType tyreType) {
        this.tyreCondition = 1;
        this.tyreType = tyreType;
    }

    public abstract void updateTyreCondition(double driverSkill);


    public TyreType getTyreType() {
        return tyreType;
    }

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


}
