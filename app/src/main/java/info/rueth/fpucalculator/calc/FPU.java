package info.rueth.fpucalculator.calc;

/**
 * Represents a FPU, including the amount of FPU and the recommended absorption
 * time in hours
 */
public class FPU {
	
	// The FPU
	private double fpu;
	
	// The recommended FPU absorption time in hours
	private double absorptionTime;
	
	/**
	 * Constructs a FPU.
	 * 
	 * @param fpu The FPU
	 * @param absorptionTime The recommended FPU absorption time in hours
	 */
	FPU(double fpu, double absorptionTime) {
		this.fpu = fpu;
		this.absorptionTime = absorptionTime;
	}
	
	/**
	 * 
	 * @return The FPU
	 */
	public double getFPU() {
		return fpu;
	}

	/**
	 * 
	 * @return The recommended absorption time in hours
	 */
	public double getAbsorptionTime() {
		return absorptionTime;
	}

    /**
     *
     * @return The extended carbs in g
     */
	public double getExtendedCarbs() { return fpu * 10; }
}
