package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.FoodDataRepository;
import info.rueth.fpucalculator.presentation.adapter.FoodListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int SAF_CREATE_RAW_EXPORT_FILE = 200;
    private static final int SAF_CREATE_JSON_EXPORT_FILE = 300;
    private static final int SAF_CREATE_JSON_IMPORT_FILE = 400;
    private static final String ACCEPTED_DISCLAIMER_PREF_NAME = "info.rueth.fpucalculator.disclaimer_accepted";
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabMeal;

    private boolean mFavorite;
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
            case R.id.action_editabsorptionscheme:
                // Edit absorption scheme
                startActivity(new Intent(this, AbsorptionSchemeActivity.class));
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
}
