package info.rueth.fpucalculator.domain.model;

import java.util.List;

/**
 * Represents a meal.
 */
public class Meal {
    // Meal name
    private String name;

    // The list of all food of the meal
    private List<Food> food;

    private double calories = 0;
    private double carbs = 0;
    private int amount = 0;
    private FPU fpus;

    /**
     * Constructs a new meal.
     *
     * @param name The name of the meal
     * @param food A list of all food of the meal
     */
    public Meal(String name, List<Food> food) {
        this.name = name;
        this.food = food;

        double tempFPUs = 0;

        // Calculate calories, carbs and FPUs
        for (int i = 0; i <  food.size(); i++) {
            calories += food.get(i).getCalories();
            carbs += food.get(i).getCarbs();
            amount += food.get(i).getAmount();
            tempFPUs += food.get(i).getFPU().getFPU();
        }

        this.fpus = new FPU(tempFPUs);
    }

    /**
     * @return The name of the meal.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The calories of the meal in kcal
     */
    public double getCalories() {
        return calories;
    }

    /**
     * @return The carbs of the meal in g
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * @return The amount of the meal in g
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return The FPUs of the meal
     */
    public FPU getFPUs() {
        return fpus;
    }
}
