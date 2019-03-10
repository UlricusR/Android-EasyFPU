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

    public static final String INTENT_FOODCALC = "Meal";

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
        List<FoodViewModel> selectedFood = getIntent().getParcelableArrayListExtra(MainActivity.INTENT_FOODLIST);
        adapter.setSelectedFood(selectedFood);

        // Floating action button to calculate meal
        FloatingActionButton fabCalc = findViewById(R.id.fab_calcmeal);
        fabCalc.setOnClickListener(view -> {
            Intent intent = new Intent(NewMealActivity.this, CalcMealActivity.class);

            // Get all selected food, each weighted with its amount
            ArrayList<FoodViewModel> weightedFood = new ArrayList<>(adapter.getSelectedFood());

            // Set to intent
            intent.putParcelableArrayListExtra(INTENT_FOODCALC, weightedFood);

            // Start activity
            startActivity(intent);
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
