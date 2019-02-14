package info.rueth.fpucalculator.calc;

import java.util.Arrays;

/**
 * Holds an absorption scheme, connecting FPUs to recommended absorption times.
 */
public class AbsorptionScheme {
	private AbsorptionBlock[] absorptionBlocks;
	
	AbsorptionScheme() {
		// Create empty array
		absorptionBlocks = new AbsorptionBlock[0];
	}
	
	/**
	 * Adds a new absorption block to the absorption scheme.
	 * @param maxFPU The maximum FPU for the absorption time
	 * @param absorptionTime The absorption time for that FPU.
	 */
	public void addBlock(int maxFPU, int absorptionTime) {
		// Copy existing array into new array with one more empty element at the end ...
		AbsorptionBlock[] newArray = Arrays.copyOf(absorptionBlocks, absorptionBlocks.length + 1);
		
		// ... and put new AbsorptionBlock into that empty element
		newArray[absorptionBlocks.length] = new AbsorptionBlock(maxFPU, absorptionTime);
		
		// Sort by maxFPU and replace object variable
		Arrays.sort(newArray, new AbsorptionBlockSorter());
		absorptionBlocks = newArray;
	}
	
	/**
	 * Picks the absorption time associated to the number of FPUs, e.g.:
	 * <p>absorptionScheme: 0-1 FPU - 3 hours; 1-2 FPU - 4 hours; 2-3 FPUs - 5 hours; 3-4 FPUs - 6 hours; >4 FPUs - 8 hours</p>
	 * <p>The fpu value is commercially rounded to 0 digits, i.e. 2.49 will be rounded to 2, 2.50 will be rounded to 3.</p>
     * <p>If the fpu value is beyond the last scheme block, the time of the last scheme block in the array is returned.</p>
	 * 
	 * @param fpus The calculated FPUs.
	 * @return The associated absorption time.
	 */
	public int getAbsorptionTime(double fpus) {
		// Round up the fpus - it's more secure to get a longer insulin interval
		long roundedFPUs = Math.round(fpus);
		
		// Find associated absorption time
		for (AbsorptionBlock absorptionBlock: absorptionBlocks) {
			if (roundedFPUs <= absorptionBlock.getMaxFPU()) {
				return absorptionBlock.getAbsorptionTime();
			}
		}
		
		// Seems to be beyond the last block, so return time of the last block
		return absorptionBlocks[absorptionBlocks.length - 1].getAbsorptionTime();
	}

	@Override
	public String toString() {
		String returnString = "Absorption Scheme:";
		for (AbsorptionBlock absorptionBlock: absorptionBlocks) {
			returnString = returnString.concat(" (" + absorptionBlock.getMaxFPU() + "FPU -> " + absorptionBlock.getAbsorptionTime() + "h)");
		}
		return returnString;
	}

	@Override
    public boolean equals(Object absorptionScheme) {
	    if (this == absorptionScheme) return true;
	    if (!(absorptionScheme != null && this.getClass() == absorptionScheme.getClass())) return false;
	    return this.toString().equals(absorptionScheme.toString());
    }
}
