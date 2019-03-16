package info.rueth.fpucalculator;

class AbsorptionBlockViewModel {
    private int maxFPU;
    private int absorptionTime;

    /**
     * Constructs a new AbsorptionBlock.
     *
     * @param maxFPU         The maximum amount of FPU.
     * @param absorptionTime The absorption time for the maximum amount of FPU in hours.
     */
    AbsorptionBlockViewModel(int maxFPU, int absorptionTime) {
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
