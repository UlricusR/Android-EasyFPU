package info.rueth.fpucalculator.presentation.viewmodels;

public class AbsorptionBlockViewModel {
    private int maxFPU;
    private int absorptionTime;

    /**
     * Constructs a new AbsorptionBlock.
     *
     * @param maxFPU         The maximum amount of FPU.
     * @param absorptionTime The absorption time for the maximum amount of FPU in hours.
     */
    public AbsorptionBlockViewModel(int maxFPU, int absorptionTime) {
        this.maxFPU = maxFPU;
        this.absorptionTime = absorptionTime;
    }

    public int getMaxFPU() {
        return maxFPU;
    }

    public int getAbsorptionTime() {
        return absorptionTime;
    }

    public void setMaxFPU(int maxFPU) {
        this.maxFPU = maxFPU;
    }

    public void setAbsorptionTime(int absorptionTime) {
        this.absorptionTime = absorptionTime;
    }
}
