package info.rueth.fpucalculator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel mFoodViewModel;
    private boolean mIsFavorite;
    public static final String FOODNAME = "info.rueth.fpucalculator.FoodName";
    public static final String INTENT_FOODLIST = "info.rueth.fpucalculator.FoodList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Favorite button
        /**
        ToggleButton favoriteButton = findViewById(R.id.button_favorite);
        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsFavorite = true;
                    mDataViewModel.get
                } else {
                    mIsFavorite = false;
                }
            }
        });**/

        // Get the persisted FoodViewModel
        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        mIsFavorite = false;

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_main);
        final FoodListAdapter adapter = new FoodListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create swipe controller and attach to recycler view
        final SwipeController swipeController = new SwipeController(getResources(), new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditFoodActivity.class);
                String foodName = adapter.getFood(position).getName();
                intent.putExtra(FOODNAME, foodName);
                startActivity(intent);
            }

            @Override
            public void onRightClicked(int position) {
                adapter.deleteFood(position);
                new FoodDelete(getApplication()).execute(mFoodViewModel);
                //mDataViewModel.delete(food);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        // Attach data observer
        FoodDataRepository.getInstance(getApplication()).getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> allFood) {
                // Update the cached copy of the food items in the adapter
                adapter.setAllFood(allFood);
            }
        });

        // New food FAB
        FloatingActionButton fabAdd = findViewById(R.id.fab_new);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewFoodActivity.class);
                startActivity(intent);
            }
        });

        // New meal FAB
        FloatingActionButton fabMeal = findViewById(R.id.fab_meal);
        fabMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if any food is selected
                if (!adapter.isAtLeastOneSelected()) {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.err_select_at_least_one_food),
                            Toast.LENGTH_LONG).show();
                } else {
                    // Pass all selected food to NewMealActivity
                    Intent intent = new Intent(MainActivity.this, NewMealActivity.class);
                    intent.putParcelableArrayListExtra(INTENT_FOODLIST, adapter.getSelectedFood());
                    startActivity(intent);
                }
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
