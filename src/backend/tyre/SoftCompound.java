package backend.tyre;

public class SoftCompound extends Tyre {
    /**
     * Constructor for Soft Tyre
     */
    public SoftCompound() {
        super(TyreType.SOFT);
        setCompoundInfluence(1);
    }

    /**
     * Updates tyre Condition
     *
     * @param driverSkill driverSkill: Higher Skill -> Lower loss
     */
    public void updateTyreCondition(double driverSkill) {
        double randomValue = Math.random() / 100;
        double updatedTyreCondition = getTyreCondition() - 0.05 - randomValue - ((1 - driverSkill) / 100);
        if (updatedTyreCondition > 0) {
            setTyreCondition(updatedTyreCondition);
        } else {
            setTyreCondition(0);
        }
    }
}
