package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;

import info.rueth.fpucalculator.usecases.FoodEdit;
import info.rueth.fpucalculator.usecases.FoodUpdate;

public class EditFoodActivity extends FoodActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set use case to updating food
        useCase = new FoodUpdate(getApplication());

        // Fill view model data - we need to set the food name first to enable view model to find the food in the DB
        viewModel.setId(getIntent().getIntExtra(FoodListAdapter.FOOD_ID, -1));
        new FoodEdit(getApplication()).execute(viewModel);

        // Get data from view model
        String name = viewModel.getName();
        boolean favorite = viewModel.isFavorite();
        double caloriesPer100g = viewModel.getCaloriesPer100g();
        double carbsPer100g = viewModel.getCarbsPer100g();
        int amountSmall = viewModel.getAmountSmall();
        int amountMedium = viewModel.getAmountMedium();
        int amountLarge = viewModel.getAmountLarge();
        String commentSmall = viewModel.getCommentSmall();
        String commentMedium = viewModel.getCommentMedium();
        String commentLarge = viewModel.getCommentLarge();

        mFoodName.setText(name);
        mFavorite.setChecked(favorite);
        mCalories.setText(String.valueOf(caloriesPer100g));
        mCarbs.setText(String.valueOf(carbsPer100g));
        mAmountSmall.setText(String.valueOf(amountSmall));
        mAmountMedium.setText(String.valueOf(amountMedium));
        mAmountLarge.setText(String.valueOf(amountLarge));
        mCommentSmall.setText(commentSmall);
        mCommentMedium.setText(commentMedium);
        mCommentLarge.setText(commentLarge);
    }
}
