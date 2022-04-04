package backend.tyre;

public abstract class Tyre {
    private double tyreCondition; //100% = 1.0, 0% = 0.0
    private double compoundInfluence;
    private final TyreType tyreType;

    /**
     * Constructor for Tyre
     *
     * @param tyreType tyreType for new Tyre
     */
    public Tyre(TyreType tyreType) {
        this.tyreCondition = 1;
        this.tyreType = tyreType;
    }

    /**
     * Updates tyre Condition
     *
     * @param driverSkill driverSkill: Higher Skill -> Lower loss
     */
    public abstract void updateTyreCondition(double driverSkill);


    /**
     * Gets Current Tyre Type
     *
     * @return tyreType
     */
    public TyreType getTyreType() {
        return tyreType;
    }

    /**
     * Gets current Tyre Condition
     *
     * @return tyreCondition
     */
    public double getTyreCondition() {
        return tyreCondition;
    }

    /**
     * Sets new Tyre Condition
     *
     * @param tyreCondition new tyreCondition
     */
    public void setTyreCondition(double tyreCondition) {
        this.tyreCondition = tyreCondition;
    }

    /**
     * Gets current Compound  Influence
     *
     * @return compoundInfluence
     */
    public double getCompoundInfluence() {
        return compoundInfluence;
    }

    /**
     * Sets new Compound Influence
     *
     * @param compoundInfluence new compoundInfluence
     */
    public void setCompoundInfluence(double compoundInfluence) {
        this.compoundInfluence = compoundInfluence;
    }


}
