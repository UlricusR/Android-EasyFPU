package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;

import info.rueth.fpucalculator.usecases.FoodSave;

public class NewFoodActivity extends FoodActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set use case to saving food
        useCase = new FoodSave(getApplication());
    }
}
