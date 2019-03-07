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

    private DataViewModel mDataViewModel;
    private boolean mIsFavorite;
    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_FOOD_ACTIVITY_REQUEST_CODE = 2;
    public static final String INTENT_FOODLIST = "FoodList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        // Get data model
        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
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
                Food food = adapter.getFood(position);
                Intent intent = new Intent(MainActivity.this, NewFoodActivity.class);
                intent.putExtra(NewFoodActivity.FOOD_POSITION, position);
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_NAME, food.getName());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_FAVORITE, food.isFavorite());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_CALORIES, food.getCaloriesPer100g());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_CARBS, food.getCarbsPer100g());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_AMOUNTSMALL, food.getAmountSmall());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_AMOUNTMEDIUM, food.getAmountMedium());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_AMOUNTLARGE, food.getAmountLarge());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_COMMENTSMALL, food.getCommentSmall());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_COMMENTMEDIUM, food.getCommentMedium());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_COMMENTLARGE, food.getCommentLarge());
                startActivityForResult(intent, EDIT_FOOD_ACTIVITY_REQUEST_CODE);
            }

            @Override
            public void onRightClicked(int position) {
                Food food = adapter.deleteFood(position);
                mDataViewModel.delete(food);
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
        mDataViewModel.getAllFood().observe(this, new Observer<List<Food>>() {
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
                startActivityForResult(intent, NEW_FOOD_ACTIVITY_REQUEST_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Food food = new Food();
            fillFood(food, data);
            mDataViewModel.insert(food);

        } else if (requestCode == EDIT_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra(NewFoodActivity.FOOD_POSITION, -1);
            Food food = mDataViewModel.getFood(position);
            fillFood(food, data);
            mDataViewModel.update(food);
        }
    }

    private void fillFood(Food food, Intent data) {
        String name = data.getStringExtra(NewFoodActivity.EXTRA_REPLY_NAME);
        boolean favorite = data.getBooleanExtra(NewFoodActivity.EXTRA_REPLY_FAVORITE, false);
        double calories = data.getDoubleExtra(NewFoodActivity.EXTRA_REPLY_CALORIES, 0);
        double carbs = data.getDoubleExtra(NewFoodActivity.EXTRA_REPLY_CARBS, 0);
        int amountSmall = data.getIntExtra(NewFoodActivity.EXTRA_REPLY_AMOUNTSMALL, 0);
        int amountMedium = data.getIntExtra(NewFoodActivity.EXTRA_REPLY_AMOUNTMEDIUM, 0);
        int amountLarge = data.getIntExtra(NewFoodActivity.EXTRA_REPLY_AMOUNTLARGE, 0);
        String commentSmall = data.getStringExtra(NewFoodActivity.EXTRA_REPLY_COMMENTSMALL);
        String commentMedium = data.getStringExtra(NewFoodActivity.EXTRA_REPLY_COMMENTMEDIUM);
        String commentLarge = data.getStringExtra(NewFoodActivity.EXTRA_REPLY_COMMENTLARGE);

        food.setName(name);
        food.setFavorite(favorite);
        food.setCaloriesPer100g(calories);
        food.setCarbsPer100g(carbs);
        food.setAmountSmall(amountSmall);
        food.setAmountMedium(amountMedium);
        food.setAmountLarge(amountLarge);
        food.setCommentSmall(commentSmall);
        food.setCommentMedium(commentMedium);
        food.setCommentLarge(commentLarge);
    }
}
