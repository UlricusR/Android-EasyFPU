package info.rueth.fpucalculator;

import java.util.ArrayList;
import java.util.List;

public class Meal {

    private List<Food> allFood;

    public Meal() {
        allFood = new ArrayList<Food>();
    }

    public void add(Food food, double amount) {
        allFood.add(food);
    }

    public void remove(Food food) {
        allFood.remove(food);
    }
}
