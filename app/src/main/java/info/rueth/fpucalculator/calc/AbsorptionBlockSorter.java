package info.rueth.fpucalculator.calc;

import java.util.Comparator;

/**
 * Compares two absorption blocks for sorting.
 */
public class AbsorptionBlockSorter implements Comparator<AbsorptionBlock> {

	@Override
	public int compare(AbsorptionBlock arg0, AbsorptionBlock arg1) {
		return arg0.getMaxFPU() - arg1.getMaxFPU();
	}

}
