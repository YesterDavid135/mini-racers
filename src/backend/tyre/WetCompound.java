package backend.tyre;

public class WetCompound extends Tyre {

    public WetCompound() {
        super(TyreType.WET);
        setCompoundInfluence(1.05);
    }

    public void updateTyreCondition(double driverSkill) {
        double updatedTyreCondition = getTyreCondition() - 0.03  - ((1 - driverSkill) / 100);
        if (updatedTyreCondition > 0) {
            setTyreCondition(updatedTyreCondition);
        } else {
            setTyreCondition(0);
        }
    }
}
