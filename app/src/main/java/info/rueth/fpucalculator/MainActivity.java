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
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataViewModel mDataViewModel;
    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_FOOD_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get data model
        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
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
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_CALORIES, food.getCalories());
                intent.putExtra(NewFoodActivity.EXTRA_REPLY_CARBS, food.getCarbs());
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
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
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
                Intent intent = new Intent(MainActivity.this, NewMealActivity.class);
                startActivity(intent);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewFoodActivity.EXTRA_REPLY_NAME);
            boolean favorite = data.getBooleanExtra(NewFoodActivity.EXTRA_REPLY_FAVORITE, false);
            double calories = data.getDoubleExtra(NewFoodActivity.EXTRA_REPLY_CALORIES, 0);
            double carbs = data.getDoubleExtra(NewFoodActivity.EXTRA_REPLY_CARBS, 0);
            Food food = new Food(name, favorite, calories, carbs);
            mDataViewModel.insert(food);
        } else if (requestCode == EDIT_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int position = data.getIntExtra(NewFoodActivity.FOOD_POSITION, -1);
            String name = data.getStringExtra(NewFoodActivity.EXTRA_REPLY_NAME);
            boolean favorite = data.getBooleanExtra(NewFoodActivity.EXTRA_REPLY_FAVORITE, false);
            double calories = data.getDoubleExtra(NewFoodActivity.EXTRA_REPLY_CALORIES, 0);
            double carbs = data.getDoubleExtra(NewFoodActivity.EXTRA_REPLY_CARBS, 0);
            Food food = mDataViewModel.getFood(position);
            food.setName(name);
            food.setFavorite(favorite);
            food.setCalories(calories);
            food.setCarbs(carbs);
            mDataViewModel.update(food);
        }
    }
}
