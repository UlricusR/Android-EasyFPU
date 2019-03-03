package info.rueth.fpucalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class NewMealActivity extends AppCompatActivity {

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
    }
}
