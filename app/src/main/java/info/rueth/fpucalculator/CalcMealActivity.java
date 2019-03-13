package info.rueth.fpucalculator;

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

public class CalcMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_meal);

        // Create recycler view
        AbsorptionScheme absorptionScheme = null;
        RecyclerView recyclerView = findViewById(R.id.recyclerview_calc_meal);
        try {
            absorptionScheme = AbsorptionScheme.getInstance(this);
        } catch (IOException e) {
            Toast.makeText(this, getText(R.string.err_absorptionschemefilenotfound), Toast.LENGTH_SHORT).show();
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
        TextView foodnameView = findViewById(R.id.calcmeal_foodname);
        TextView amountView = findViewById(R.id.calcmeal_amount);
        TextView caloriesView = findViewById(R.id.calcmeal_calories);
        TextView carbsView = findViewById(R.id.calcmeal_carbs);
        TextView fpuView = findViewById(R.id.calcmeal_fpu);
        TextView eCarbsView = findViewById(R.id.calcmeal_ecarbs);
        TextView absorptionTimeView = findViewById(R.id.calcmeal_absorptiontime);

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

        // Floating action button to go back to main activity
        FloatingActionButton fabCalc = findViewById(R.id.fab_done);
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
