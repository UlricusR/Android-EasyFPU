package info.rueth.fpucalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NewFoodActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_NAME = "info.rueth.fpucalculator.foodlistsql.REPLY_NAME";
    public static final String EXTRA_REPLY_FAVORITE = "info.rueth.fpucalculator.foodlistsql.REPLY_FAVORITE";
    public static final String EXTRA_REPLY_CALORIES = "info.rueth.fpucalculator.foodlistsql.REPLY_CALORIES";
    public static final String EXTRA_REPLY_CARBS = "info.rueth.fpucalculator.foodlistsql.REPLY_CARBS";

    private EditText mFoodName;
    private ToggleButton mFavorite;
    private EditText mCalories;
    private EditText mCarbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);

        mFoodName = findViewById(R.id.foodname);
        mFavorite = findViewById(R.id.favorite);
        mCalories = findViewById(R.id.calories);
        mCarbs = findViewById(R.id.carbs);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (
                        TextUtils.isEmpty(mFoodName.getText()) ||
                        TextUtils.isEmpty(mCalories.getText()) ||
                        TextUtils.isEmpty(mCarbs.getText())
                ) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String foodName = mFoodName.getText().toString();
                    boolean favorite = mFavorite.isChecked();
                    double calories = Double.parseDouble(mCalories.getText().toString());
                    double carbs = Double.parseDouble(mCarbs.getText().toString());

                    replyIntent.putExtra(EXTRA_REPLY_NAME, foodName);
                    replyIntent.putExtra(EXTRA_REPLY_FAVORITE, favorite);
                    replyIntent.putExtra(EXTRA_REPLY_CALORIES, calories);
                    replyIntent.putExtra(EXTRA_REPLY_CARBS, carbs);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
