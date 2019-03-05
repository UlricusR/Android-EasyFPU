package info.rueth.fpucalculator;

/**
 * Represents a FPU, including the amount of FPU and the recommended absorption
 * time in hours
 */
public class FPU {

    // The FPU
    private double fpu;

    /**
     * Constructs a FPU.
     *
     * @param fpu            The FPU
     */
    FPU(double fpu) {
        this.fpu = fpu;
    }

    /**
     * @return The FPU
     */
    public double getFPU() {
        return fpu;
    }

    /**
     * @return The extended carbs in g
     */
    public double getExtendedCarbs() {
        return fpu * 10;
    }

    public int getAbsorptionTime(AbsorptionScheme absorptionScheme) {
        return absorptionScheme.getAbsorptionTime(fpu);
    }
}
