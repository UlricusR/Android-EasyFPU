package info.rueth.fpucalculator;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class EditFoodActivity extends FoodActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set use case to updating food
        useCase = new FoodUpdate(getApplication());

        // Fill view model data - we need to set the food name first to enable view model to find the food in the DB
        viewModel.setId(getIntent().getIntExtra(MainActivity.FOOD_ID, -1));
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
