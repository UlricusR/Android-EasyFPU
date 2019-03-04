package info.rueth.fpucalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class CalcMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_meal);

        // Get weighted food from intent
        List<Food> weightedFood = getIntent().getParcelableArrayListExtra(NewMealActivity.INTENT_FOODCALC);

    }
}
