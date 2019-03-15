package info.rueth.fpucalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NewMealActivity extends AppCompatActivity {

    private FloatingActionButton fabCalc;

    private FoodCalcAdapter adapter;
    private int[] selectedFoodIds;

    public static final String INTENT_MEALCALC = "info.rueth.fpucalculator.MealCalc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_newmeal);
        adapter = new FoodCalcAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Floating action button to calculate meal
        fabCalc = findViewById(R.id.fab_calcmeal);

        // Get food IDs from intent, retrieve respective food from repository and set to adapter
        selectedFoodIds = getIntent().getIntArrayExtra(MainActivity.INTENT_FOODLIST);
        adapter.setSelectedFood(FoodDataRepository.getInstance(getApplication()).getFoodByIDs(selectedFoodIds));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Add onClick listener to fab
        fabCalc.setOnClickListener(view -> {
            Intent intent = new Intent(NewMealActivity.this, CalcMealActivity.class);

            // Pass on ids of selected food
            intent.putExtra(INTENT_MEALCALC, selectedFoodIds);

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
