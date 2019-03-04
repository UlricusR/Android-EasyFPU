package info.rueth.fpucalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NewMealActivity extends AppCompatActivity {

    public static final String INTENT_FOODCALC = "FoodCalc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_newmeal);
        final FoodCalcAdapter adapter = new FoodCalcAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass selected food to adapter
        List<Food> selectedFood = getIntent().getParcelableArrayListExtra(MainActivity.INTENT_FOODLIST);
        adapter.setSelectedFood(selectedFood);

        // Floating action button to calculate meal
        FloatingActionButton fabCalc = findViewById(R.id.fab_calcmeal);
        fabCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewMealActivity.this, CalcMealActivity.class);

                // Get all selected food, each weighted with its amount
                ArrayList<Food> weightedFood = new ArrayList<Food>(adapter.getSelectedFood());

                // Set to intent
                Intent weightedFoodList = intent.putParcelableArrayListExtra(INTENT_FOODCALC, weightedFood);

                // Start activity
                startActivity(intent);
            }
        });
    }
}
