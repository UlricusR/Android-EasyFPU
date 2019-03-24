package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.domain.repository.MaintenanceUtils;
import info.rueth.fpucalculator.presentation.adapter.FoodListAdapter;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;
import info.rueth.fpucalculator.usecases.FoodDelete;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private FloatingActionButton fabMeal;

    private boolean mFavorite;
    private FoodListAdapter adapter;

    public static final String FOOD_ID = "info.rueth.fpucalculator.FoodID";
    public static final String INTENT_FOODLIST = "info.rueth.fpucalculator.FoodList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set favorite to false initially
        mFavorite = false;

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main);
        adapter = new FoodListAdapter(this, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Favorite button
        ToggleButton favoriteButton = findViewById(R.id.button_favorite);
        favoriteButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mFavorite = isChecked;
            FoodDataRepository.getInstance(getApplication()).getAllFood(mFavorite).observe(this, adapter::setAllFood);
        });

        // Attach data observer
        // Update the cached copy of the food items in the adapter
        FoodDataRepository.getInstance(getApplication()).getAllFood(mFavorite).observe(this, adapter::setAllFood);

        // New food and meal calc FABs
        fabAdd = findViewById(R.id.fab_new);
        fabMeal = findViewById(R.id.fab_meal);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Add onClickListener to fabAdd
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewFoodActivity.class);
            startActivity(intent);
        });

        // Add onClickListener to fabMeal
        fabMeal.setOnClickListener(view -> {
            // Check if any food is selected
            if (!adapter.isAtLeastOneSelected()) {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.err_select_at_least_one_food),
                        Toast.LENGTH_LONG).show();
            } else {
                // Pass all selected food to NewMealActivity
                Intent intent = new Intent(MainActivity.this, NewMealActivity.class);
                intent.putExtra(INTENT_FOODLIST, adapter.getSelectedFoodIds());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // De-register FAB onClick listeners
        fabAdd.setOnClickListener(null);
        fabMeal.setOnClickListener(null);
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
            case R.id.action_exportdb:
                // Export database
                MaintenanceUtils.backupDatabase(getApplicationContext());
                return true;
            /** TODO Import database
            case R.id.action_importdb:
                // Import database
                MaintenanceUtils.loadDatabase(getApplicationContext());
                return true;
            */
            case R.id.action_editabsorptionscheme:
                // Edit absorption scheme
                startActivity(new Intent(this, AbsorptionSchemeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);            
        }
    }
}
