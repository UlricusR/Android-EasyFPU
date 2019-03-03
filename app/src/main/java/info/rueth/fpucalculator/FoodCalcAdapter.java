package info.rueth.fpucalculator;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodCalcAdapter extends RecyclerView.Adapter<FoodCalcAdapter.FoodViewHolder> implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        TypicalAmount typicalAmount = (TypicalAmount) parent.getItemAtPosition(position);
        EditText amount = view.findViewById(R.id.newmeal_amount);
        amount.setText(String.valueOf(typicalAmount.getAmount()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        
        // The views of the food calc item
        private final TextView foodnameView;
        private final EditText amountView;
        private final Spinner typicalAmountSpinner;
        
        private FoodViewHolder(View itemView) {
            super(itemView);
            foodnameView = itemView.findViewById(R.id.newmeal_foodname);
            amountView = itemView.findViewById(R.id.newmeal_amount);
            typicalAmountSpinner = itemView.findViewById(R.id.newmeal_typicalamountspinner);
        }
    }
    
    private final LayoutInflater mInflater;
    private List<Food> selectedFood; // Cached copy of all selected food items
    
    FoodCalcAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create the food calc item
        View itemView = mInflater.inflate(R.layout.food_calc_item, parent, false);
        return new FoodViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        if (selectedFood != null) {
            // Get the current food item
            Food food = selectedFood.get(position);

            // Set the food name
            holder.foodnameView.setText(food.getName());

            // Create typical amount spinner
            List<TypicalAmount> typicalAmounts = new ArrayList<>();
            
            // First entry should always be an empty field
            TypicalAmount emptyAmout = new TypicalAmount(0, null, mInflater.getContext().getString(R.string.newmeal_customamount));
            typicalAmounts.add(emptyAmout);

            // Append other amounts
            appendTypicalAmount(typicalAmounts, food.getAmountSmall(), food.getCommentSmall(), mInflater.getContext().getString(R.string.amountsmall_label));
            appendTypicalAmount(typicalAmounts, food.getAmountMedium(), food.getCommentMedium(), mInflater.getContext().getString(R.string.amountmedium_label));
            appendTypicalAmount(typicalAmounts, food.getAmountLarge(), food.getCommentLarge(), mInflater.getContext().getString(R.string.amountlarge_label));
            ArrayAdapter<TypicalAmount> typicalAmountAdapter = new ArrayAdapter<>(mInflater.getContext(), R.layout.support_simple_spinner_dropdown_item, typicalAmounts);
            typicalAmountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            holder.typicalAmountSpinner.setOnItemSelectedListener(this);
            holder.typicalAmountSpinner.setAdapter(typicalAmountAdapter);
        } else {
            // Covers the case no food has been selected (should never happen, we check before)
            holder.foodnameView.setText(mInflater.getContext().getString(R.string.newmeal_nofoodselected));
        }
    }

    void setSelectedFood(List<Food> selectedFood) {
        this.selectedFood = selectedFood;
        //notifyDataSetChanged();
    }

    private void appendTypicalAmount(List<TypicalAmount> typicalAmounts, double amount, String comment, String defaultComment) {
        if (amount > 0) {
            typicalAmounts.add(new TypicalAmount(amount, comment, defaultComment));
        }
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
