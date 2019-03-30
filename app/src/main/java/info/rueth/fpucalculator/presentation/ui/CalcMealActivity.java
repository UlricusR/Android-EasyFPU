package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.model.AbsorptionScheme;
import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.adapter.MealCalcAdapter;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;
import info.rueth.fpucalculator.presentation.viewmodels.MealViewModel;

public class CalcMealActivity extends AppCompatActivity {

    // The activity's fields
    private TextView foodnameView;
    private TextView amountView;
    private TextView caloriesView;
    private TextView carbsView;
    private TextView fpuView;
    private TextView eCarbsView;
    private TextView absorptionTimeView;

    private FloatingActionButton fabCalc;

    // Other class variables
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_meal);

        // Get absorption scheme
        AbsorptionScheme absorptionScheme = null;
        try {
            absorptionScheme = AbsorptionSchemeRepository.getInstance(getApplication()).getAbsorptionScheme();
        } catch (IOException e) {
            Toast.makeText(this, getText(R.string.err_absorptionschemefilenotfound), Toast.LENGTH_SHORT).show();
            this.finish();
        }
        
        //
        // Prepare the recycler view with the individual food items of the meal
        //

        // Create adapter using the actual absorption scheme and assign to meal calculation recycler view
        final MealCalcAdapter adapter = new MealCalcAdapter(this, absorptionScheme);
        recyclerView = findViewById(R.id.recyclerview_calc_meal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass selected food to adapter
        int[] selectedFoodIds = getIntent().getIntArrayExtra(NewMealActivity.INTENT_MEALCALC);
        List<FoodViewModel> weightedFood = FoodDataRepository.getInstance(getApplication()).getFoodByIDs(selectedFoodIds);
        adapter.setSelectedFood(weightedFood);
        
        //
        // Prepare the meal, which includes all selected food items.
        // The meal will be displayed on top of the recycler view (outside the recycler).
        //

        // Retrieve the fields
        foodnameView = findViewById(R.id.calcmeal_foodname);
        amountView = findViewById(R.id.calcmeal_amount);
        caloriesView = findViewById(R.id.calcmeal_calories);
        carbsView = findViewById(R.id.calcmeal_carbs);
        fpuView = findViewById(R.id.calcmeal_fpu);
        eCarbsView = findViewById(R.id.calcmeal_ecarbs);
        absorptionTimeView = findViewById(R.id.calcmeal_absorptiontime);

        // Create the meal
        MealViewModel meal = new MealViewModel(getApplicationContext().getString(R.string.meal), weightedFood);

        // Set the meal values
        foodnameView.setText(meal.getName());
        amountView.setText(String.valueOf(meal.getAmount()));
        caloriesView.setText(String.format(Locale.getDefault(), "%.1f", meal.getCalories()));
        carbsView.setText(String.format(Locale.getDefault(), "%.1f", meal.getCarbs()));
        fpuView.setText(String.format(Locale.getDefault(), "%.1f", meal.getFPUs().getFPU()));
        eCarbsView.setText(String.format(Locale.getDefault(), "%.1f", meal.getFPUs().getExtendedCarbs()));
        absorptionTimeView.setText(String.valueOf(meal.getFPUs().getAbsorptionTime(absorptionScheme)));

        // Set background color of the meal card to distinguish it easier from the food cards
        findViewById(R.id.meal_foodcard).setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register floating action button to go back to main activity
        fabCalc = findViewById(R.id.fab_done);
        fabCalc.setOnClickListener(view -> {
            Intent intent = new Intent(CalcMealActivity.this, MainActivity.class);

            // Start activity
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // De-register fab onClick listener
        fabCalc.setOnClickListener(null);
    }
}
