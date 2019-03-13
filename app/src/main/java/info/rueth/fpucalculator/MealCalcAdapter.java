package info.rueth.fpucalculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MealCalcAdapter extends RecyclerView.Adapter<MealCalcAdapter.FoodViewHolder> {
    class FoodViewHolder extends RecyclerView.ViewHolder {

        // The views of the food calc item
        private final TextView foodnameView;
        private final TextView amountView;
        private final TextView caloriesView;
        private final TextView carbsView;
        private final TextView fpuView;
        private final TextView eCarbsView;
        private final TextView absorptionTimeView;

        private FoodViewHolder(View itemView) {
            super(itemView);
            foodnameView = itemView.findViewById(R.id.calcmeal_foodname);
            amountView = itemView.findViewById(R.id.calcmeal_amount);
            caloriesView = itemView.findViewById(R.id.calcmeal_calories);
            carbsView = itemView.findViewById(R.id.calcmeal_carbs);
            fpuView = itemView.findViewById(R.id.calcmeal_fpu);
            eCarbsView = itemView.findViewById(R.id.calcmeal_ecarbs);
            absorptionTimeView = itemView.findViewById(R.id.calcmeal_absorptiontime);
        }
   }

    private final LayoutInflater mInflater;
    private AbsorptionScheme mAbsorptionScheme;
    private List<Food> selectedFood; // Cached copy of all selected food items

    MealCalcAdapter(Context context, AbsorptionScheme absorptionScheme) {
        mInflater = LayoutInflater.from(context);
        mAbsorptionScheme = absorptionScheme;
    }

    @Override
    @NonNull
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create the food calc item
        View itemView = mInflater.inflate(R.layout.meal_food_item, parent, false);
        return new FoodViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (selectedFood != null) {
            // Get the current food item
            Food food = selectedFood.get(position);

            // Set food name, amount, resulting calories, carbs, FPUs, eCarbs and absorption time
            holder.foodnameView.setText(food.getName());
            holder.amountView.setText(String.valueOf(food.getAmount()));
            holder.caloriesView.setText(String.format("%.1f", food.getCalories()));
            holder.carbsView.setText(String.format("%.1f", food.getCarbs()));
            holder.fpuView.setText(String.format("%.1f", food.getFPU().getFPU()));
            holder.eCarbsView.setText(String.format("%.1f", food.getFPU().getExtendedCarbs()));
            holder.absorptionTimeView.setText(String.valueOf(food.getFPU().getAbsorptionTime(mAbsorptionScheme)));

        } else {
            // Covers the case no food has been selected (should never happen, we check before)
            holder.foodnameView.setText(mInflater.getContext().getString(R.string.newmeal_nofoodselected));
        }
    }

    void setSelectedFood(List<Food> selectedFood) {
        this.selectedFood = selectedFood;
    }

    List<Food> getSelectedFood() {
        return selectedFood;
    }

    // getItemCount() is called many times, and when it is first called,
    // allFood has not been updated (means initially, it's null, and we can't return null)
    @Override
    public int getItemCount() {
        if (selectedFood != null)
            return selectedFood.size();
        else return 0;
    }
}
