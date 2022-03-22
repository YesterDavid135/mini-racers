package backend.tyre;

public class HardCompound extends Tyre {

    public HardCompound() {
        super();
        setCompoundInfluence(1.02);
        setRainTyre(false);
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
