package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.model.AbsorptionScheme;
import info.rueth.fpucalculator.domain.model.AbsorptionSchemeException;
import info.rueth.fpucalculator.domain.model.Food;
import info.rueth.fpucalculator.domain.model.Meal;
import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.adapter.MealCalcAdapter;

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
    private AbsorptionScheme absorptionScheme;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_meal);

        // Create recycler view
        absorptionScheme = null;
        recyclerView = findViewById(R.id.recyclerview_calc_meal);
        try {
            absorptionScheme = AbsorptionSchemeRepository.getInstance(getApplication()).getAbsorptionScheme();
        } catch (IOException e) {
            Toast.makeText(this, getText(R.string.err_absorptionschemefilenotfound), Toast.LENGTH_SHORT).show();
            this.finish();
        } catch (AbsorptionSchemeException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            this.finish();
        }

        final MealCalcAdapter adapter = new MealCalcAdapter(this, absorptionScheme);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass selected food to adapter
        int[] selectedFoodIds = getIntent().getIntArrayExtra(NewMealActivity.INTENT_MEALCALC);
        List<Food> weightedFood = FoodDataRepository.getInstance(getApplication()).getFoodByIDs(selectedFoodIds);
        adapter.setSelectedFood(weightedFood);

        // Retrieve the fields
        foodnameView = findViewById(R.id.calcmeal_foodname);
        amountView = findViewById(R.id.calcmeal_amount);
        caloriesView = findViewById(R.id.calcmeal_calories);
        carbsView = findViewById(R.id.calcmeal_carbs);
        fpuView = findViewById(R.id.calcmeal_fpu);
        eCarbsView = findViewById(R.id.calcmeal_ecarbs);
        absorptionTimeView = findViewById(R.id.calcmeal_absorptiontime);

        // Create the meal
        Meal meal = new Meal(getApplicationContext().getString(R.string.meal), weightedFood);

        // Set the meal values
        foodnameView.setText(meal.getName());
        amountView.setText(String.valueOf(meal.getAmount()));
        caloriesView.setText(String.format("%.1f", meal.getCalories()));
        carbsView.setText(String.format("%.1f", meal.getCarbs()));
        fpuView.setText(String.format("%.1f", meal.getFPUs().getFPU()));
        eCarbsView.setText(String.format("%.1f", meal.getFPUs().getExtendedCarbs()));
        absorptionTimeView.setText(String.valueOf(meal.getFPUs().getAbsorptionTime(absorptionScheme)));

        // Set background color of the meal card to distinguish it easier from the food cards
        findViewById(R.id.meal_foodcard).setBackgroundColor(Color.YELLOW);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register floating action button to go back to main activity
        fabCalc = findViewById(R.id.fab_done);
        fabCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalcMealActivity.this, MainActivity.class);

                // Start activity
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // De-register fab onClick listener
        fabCalc.setOnClickListener(null);
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Start preferences activity
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);            
        }
    }
}
