package info.rueth.fpucalculator.calc;

/**
 * Represents a certain food.
 */
public class Food {
    // Food name
    private String name;

    // Calories per 100g in kcal
    private double caloriesPer100g;

    // Carbs per 100g in g
    private double carbsPer100g;

    /**
     * Constructs a new Food.
     *
     * @param name            The name of the food
     * @param caloriesPer100g The calories per 100g in kcal
     * @param carbsPer100g    The carbs per 100g in g
     * @throws Exception If calories or carbs are negative and if calories from carbs > total calories
     */
    Food(String name, double caloriesPer100g, double carbsPer100g) throws Exception {
        // Check if all numbers are positive
        if (caloriesPer100g < 0 || carbsPer100g < 0) {
            throw new Exception("Calories as well as carbs must not be negative for food " + name);
        }

        // Do a consistency check: Calories from carbs cannot be more than
        // total calories; 1g of carbs has 4 kcal
        if (carbsPer100g * 4 > caloriesPer100g) {
            throw new Exception("Cannot construct new food " + name
                    + ": Calories from carbs (1g ~ 4kcal) of "
                    + (carbsPer100g * 4) + " larger than total calories of "
                    + caloriesPer100g);
        }

        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.carbsPer100g = carbsPer100g;
    }

    /**
     * @return The name of the food.
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates the Fat Protein Units of the food.
     *
     * @param amount The amount of food eaten in g
     * @return The FPU associated with that food
     */
    public FPU getFPU(double amount, AbsorptionScheme absorptionScheme) {
        // Calculate the total calories
        double totalCal = amount / 100 * caloriesPer100g;

        // 1g carbs has ~4 kcal, so calculate carb portion of calories
        double carbsCal = amount / 100 * carbsPer100g * 4;

        // The carbs from fat and protein is the remainder
        double calFromFP = totalCal - carbsCal;

        // 100kcal makes 1 FPU
        double fpus = calFromFP / 100;

        // Create and return the FPU object
        return new FPU(fpus, absorptionScheme.getAbsorptionTime(fpus));
    }
}
