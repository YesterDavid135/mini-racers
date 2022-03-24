package backend.tyre;

public class HardCompound extends Tyre {

    public HardCompound() {
        super(TyreType.HARD);
        setCompoundInfluence(1.02);
    }

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
