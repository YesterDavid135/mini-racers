package backend.tyre;

public class SoftCompound extends Tyre {

    public SoftCompound() {
        super(TyreType.SOFT);
        setCompoundInfluence(1);
    }

    public void updateTyreCondition(double driverSkill) {
        double updatedTyreCondition = getTyreCondition() - 0.05  - ((1 - driverSkill) / 100);
        if (updatedTyreCondition > 0) {
            setTyreCondition(updatedTyreCondition);
        } else {
            setTyreCondition(0);
        }
    }
}
