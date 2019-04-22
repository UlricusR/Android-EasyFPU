package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.adapter.FoodListAdapter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String ACCEPTED_DISCLAIMER_PREF_NAME = "info.rueth.fpucalculator.disclaimer_accepted";
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabMeal;
    private ToggleButton favoriteButton;
    private SearchView searchView;

    private FoodListAdapter adapter;

    public static final String INTENT_FOODLIST = "info.rueth.fpucalculator.FoodList";
    public static final String SERVICE_IMPORTJSON = "info.rueth.fpucalculator.ImportJson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the user opens the app the first time
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean(ACCEPTED_DISCLAIMER_PREF_NAME, false)) {
            // The user hasn't accepted the disclaimer yet, so show it
            // Ask user for replace or append mode
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            // Set title and message
            alert.setTitle(R.string.disclaimer_dialog_title);
            alert.setMessage(R.string.disclaimer_dialog_message);

            // Set positive button = accept
            alert.setPositiveButton(R.string.disclaimer_dialog_button_accept, (dialog, which) -> {
                // Store acceptance in shared preferences
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                sharedPreferencesEditor.putBoolean(ACCEPTED_DISCLAIMER_PREF_NAME, true);
                sharedPreferencesEditor.apply();

                // Continue with execution
            });

            // Set negative button = decline
            alert.setNegativeButton(R.string.disclaimer_dialog_button_decline, (dialog, which) -> {
                // Stop execution of app
                this.finish();
            });

            // Show
            alert.create().show();
        }

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initiate favorite button and set to non-checked
        favoriteButton = findViewById(R.id.button_favorite);

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main);
        adapter = new FoodListAdapter(this, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach data observer, update cached copy of the food items in the adapter
        FoodDataRepository.getInstance(getApplication()).getAllFoodVM().observe(this, foodViewModels -> {
            adapter.setAllFood(foodViewModels, favoriteButton.isChecked());
            
            // Add filter if already initialized
            if (searchView != null) onQueryTextChange(searchView.getQuery().toString());
        });

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

        // Add onCheckedChangeListener to favorite button
        favoriteButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Set to correct favorite state
            adapter.setFavorite(isChecked);

            // Add filter if already initialized
            if (searchView != null) onQueryTextChange(searchView.getQuery().toString());
        });

        // Set food list to right state
        adapter.setFavorite(favoriteButton.isChecked());

        // Get the search query if already initialized
        if (searchView != null) onQueryTextChange(searchView.getQuery().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // De-register FAB onClick listeners
        fabAdd.setOnClickListener(null);
        fabMeal.setOnClickListener(null);
        favoriteButton.setOnCheckedChangeListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Add onQueryTextListener to search view
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);

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
            case R.id.action_about:
                // Show about dialog
                showAboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);            
        }
    }

    private void showAboutDialog() {
        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Create the filter logic for the search view
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Do nothing
        return false;
    }
}
