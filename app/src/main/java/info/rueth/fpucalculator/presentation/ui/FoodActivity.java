package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;
import info.rueth.fpucalculator.usecases.FoodUseCase;

public abstract class FoodActivity extends AppCompatActivity {

    protected EditText mFoodName;
    protected Switch mFavorite;
    protected EditText mCalories;
    protected EditText mCarbs;
    protected EditText mAmountSmall;
    protected EditText mAmountMedium;
    protected EditText mAmountLarge;
    protected EditText mCommentSmall;
    protected EditText mCommentMedium;
    protected EditText mCommentLarge;

    protected Button saveButton;
    protected Button cancelButton;

    protected FoodViewModel viewModel;
    protected FoodUseCase useCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);
        this.viewModel = ViewModelProviders.of(this).get(FoodViewModel.class);

        mFoodName = findViewById(R.id.foodname);
        mFavorite = findViewById(R.id.favorite);
        mCarbs = findViewById(R.id.carbs);
        mCalories = findViewById(R.id.calories);
        mAmountSmall = findViewById(R.id.amountsmall);
        mAmountMedium = findViewById(R.id.amountmedium);
        mAmountLarge = findViewById(R.id.amountlarge);
        mCommentSmall = findViewById(R.id.amountsmall_comment);
        mCommentMedium = findViewById(R.id.amountmedium_comment);
        mCommentLarge = findViewById(R.id.amountlarge_comment);

        // Save and cancel buttons
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        // Save button
        saveButton.setOnClickListener(v -> {
            Intent replyIntent = new Intent();

            if (
                    TextUtils.isEmpty(mFoodName.getText()) ||
                    TextUtils.isEmpty(mCalories.getText()) ||
                    TextUtils.isEmpty(mCarbs.getText())
            ) {
                String errMsg = getResources().getString(R.string.newfood_error_datamissing);
                Toast.makeText(
                        getApplicationContext(),
                        errMsg,
                        Toast.LENGTH_LONG).show();
            } else {
                String foodName = mFoodName.getText().toString();
                boolean favorite1 = mFavorite.isChecked();
                double caloriesPer100g1 = Double.parseDouble(mCalories.getText().toString());
                double carbsPer100g1 = Double.parseDouble(mCarbs.getText().toString());
                int amoutSmall = !TextUtils.isEmpty(mAmountSmall.getText()) ? Integer.parseInt(mAmountSmall.getText().toString()) : 0;
                int amoutMedium = !TextUtils.isEmpty(mAmountMedium.getText()) ? Integer.parseInt(mAmountMedium.getText().toString()) : 0;
                int amoutLarge = !TextUtils.isEmpty(mAmountLarge.getText()) ? Integer.parseInt(mAmountLarge.getText().toString()) : 0;
                String commentSmall1 = mCommentSmall.getText().toString();
                String commentMedium1 = mCommentMedium.getText().toString();
                String commentLarge1 = mCommentLarge.getText().toString();

                // Check if all numbers are positive
                if (caloriesPer100g1 < 0 || carbsPer100g1 < 0) {
                    String errMsg = getResources().getString(R.string.newfood_error_carbsorcalbelowzero) + foodName;
                    Toast.makeText(
                            getApplicationContext(),
                            errMsg,
                            Toast.LENGTH_LONG).show();
                } else if (carbsPer100g1 * 4 > caloriesPer100g1) {
                    // Do a consistency check: Calories from carbs cannot be more than
                    // total calories; 1g of carbs has 4 kcal
                    String errMsg =
                            getResources().getString(R.string.newfood_error_calslargercarbs1)
                            + foodName
                            + getResources().getString(R.string.newfood_error_calslargercarbs2)
                            + (carbsPer100g1 * 4) + getResources().getString(R.string.newfood_error_calslargercarbs3)
                            + caloriesPer100g1;
                    Toast.makeText(
                            getApplicationContext(),
                            errMsg,
                            Toast.LENGTH_LONG).show();
                } else {
                    // Fill food
                    viewModel.setName(foodName);
                    viewModel.setFavorite(favorite1);
                    viewModel.setCaloriesPer100g(caloriesPer100g1);
                    viewModel.setCarbsPer100g(carbsPer100g1);
                    viewModel.setAmountSmall(amoutSmall);
                    viewModel.setAmountMedium(amoutMedium);
                    viewModel.setAmountLarge(amoutLarge);
                    viewModel.setCommentSmall(commentSmall1);
                    viewModel.setCommentMedium(commentMedium1);
                    viewModel.setCommentLarge(commentLarge1);

                    // Save or update food
                    useCase.execute(viewModel);

                    // Set result OK and finish activity
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
        
        // Cancel button
        cancelButton.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // De-register save and cancel button listeners
        saveButton.setOnClickListener(null);
        cancelButton.setOnClickListener(null);
    }
}
