package info.rueth.fpucalculator.domain.model;

/**
 * An absorption block associates a maximum number of FPUs
 * to a certain absorption time, e.g.:
 * up to 1 FPU the absorption time shall be 3 hours.
 */
public class AbsorptionBlock {
    private int maxFPU;
    private int absorptionTime;

    /**
     * Constructs a new AbsorptionBlock.
     *
     * @param maxFPU         The maximum amount of FPU.
     * @param absorptionTime The absorption time for the maximum amount of FPU in hours.
     */
    public AbsorptionBlock(int maxFPU, int absorptionTime) {
        this.maxFPU = maxFPU;
        this.absorptionTime = absorptionTime;
    }

    public int getMaxFPU() {
        return maxFPU;
    }

    public int getAbsorptionTime() {
        return absorptionTime;
    }
}
