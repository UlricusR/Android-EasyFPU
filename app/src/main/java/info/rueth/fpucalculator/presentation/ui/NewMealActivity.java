package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.adapter.FoodCalcAdapter;

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
        adapter = new FoodCalcAdapter(this, getApplication());
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
}
