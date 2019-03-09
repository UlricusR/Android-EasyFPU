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

public class EditFoodActivity extends AppCompatActivity {

    private FoodViewModel viewModel;

    private EditText mFoodName;
    private Switch mFavorite;
    private EditText mCalories;
    private EditText mCarbs;
    private EditText mAmountSmall;
    private EditText mAmountMedium;
    private EditText mAmountLarge;
    private EditText mCommentSmall;
    private EditText mCommentMedium;
    private EditText mCommentLarge;

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

        // Fill view model data - we need to set the food name first to enable view model to find the food in the DB
        viewModel.setID(getIntent().getIntExtra(MainActivity.FOOD_ID, -1));
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
        
        // Save button
        final Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    boolean favorite = mFavorite.isChecked();
                    double caloriesPer100g = Double.parseDouble(mCalories.getText().toString());
                    double carbsPer100g = Double.parseDouble(mCarbs.getText().toString());
                    int amoutSmall = !TextUtils.isEmpty(mAmountSmall.getText()) ? Integer.parseInt(mAmountSmall.getText().toString()) : 0;
                    int amoutMedium = !TextUtils.isEmpty(mAmountMedium.getText()) ? Integer.parseInt(mAmountMedium.getText().toString()) : 0;
                    int amoutLarge = !TextUtils.isEmpty(mAmountLarge.getText()) ? Integer.parseInt(mAmountLarge.getText().toString()) : 0;
                    String commentSmall = mCommentSmall.getText().toString();
                    String commentMedium = mCommentMedium.getText().toString();
                    String commentLarge = mCommentLarge.getText().toString();

                    // Check if all numbers are positive
                    if (caloriesPer100g < 0 || carbsPer100g < 0) {
                        String errMsg = getResources().getString(R.string.newfood_error_carbsorcalbelowzero) + foodName;
                        Toast.makeText(
                                getApplicationContext(),
                                errMsg,
                                Toast.LENGTH_LONG).show();
                    } else if (carbsPer100g * 4 > caloriesPer100g) {
                        // Do a consistency check: Calories from carbs cannot be more than
                        // total calories; 1g of carbs has 4 kcal
                        String errMsg =
                                getResources().getString(R.string.newfood_error_calslargercarbs1)
                                + foodName
                                + getResources().getString(R.string.newfood_error_calslargercarbs2)
                                + (carbsPer100g * 4) + getResources().getString(R.string.newfood_error_calslargercarbs3)
                                + caloriesPer100g;
                        Toast.makeText(
                                getApplicationContext(),
                                errMsg,
                                Toast.LENGTH_LONG).show();
                    } else {
                        // Fill food
                        viewModel.setName(foodName);
                        viewModel.setFavorite(favorite);
                        viewModel.setCaloriesPer100g(caloriesPer100g);
                        viewModel.setCarbsPer100g(carbsPer100g);
                        viewModel.setAmountSmall(amoutSmall);
                        viewModel.setAmountMedium(amoutMedium);
                        viewModel.setAmountLarge(amoutLarge);
                        viewModel.setCommentSmall(commentSmall);
                        viewModel.setCommentMedium(commentMedium);
                        viewModel.setCommentLarge(commentLarge);

                        // Save or update food
                        new FoodUpdate(getApplication()).execute(viewModel);

                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }
                }
            }
        });
        
        // Cancel button
        final Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, PreferencesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
