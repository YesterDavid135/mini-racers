package backend.tyre;

public class HardCompound extends Tyre {

    /**
     * Constructor for Hard Tyre
     */
    public HardCompound() {
        super(TyreType.HARD);
        setCompoundInfluence(1.01);
    }

    /**
     * Updates tyre Condition
     *
     * @param driverSkill driverSkill: Higher Skill -> Lower loss
     */
    public void updateTyreCondition(double driverSkill) {
        double randomValue = Math.random() / 100;
        double updatedTyreCondition = getTyreCondition() - 0.02 - randomValue - ((1 - driverSkill) / 100);
        if (updatedTyreCondition > 0) {
            setTyreCondition(updatedTyreCondition);
        } else {
            setTyreCondition(0);
        }
    }
}
