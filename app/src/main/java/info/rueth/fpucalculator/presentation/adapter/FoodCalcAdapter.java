package info.rueth.fpucalculator.presentation.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.model.Food;
import info.rueth.fpucalculator.domain.model.TypicalAmount;

public class FoodCalcAdapter extends RecyclerView.Adapter<FoodCalcAdapter.FoodViewHolder> {
    class FoodViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        
        // The views of the food calc item
        private final TextView foodnameView;
        private final EditText amountView;
        private final Spinner typicalAmountSpinner;
        
        private FoodViewHolder(View itemView) {
            super(itemView);
            foodnameView = itemView.findViewById(R.id.newmeal_foodname);
            typicalAmountSpinner = itemView.findViewById(R.id.newmeal_typicalamountspinner);
            amountView = itemView.findViewById(R.id.newmeal_amount);
            amountView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        int amount = Integer.valueOf(s.toString());
                        storeAmount(amount);
                    } else {
                        // Set default value of 0
                        storeAmount(0);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Do nothing
                }
            });
        }

        private void storeAmount(int amount) {
            // Store the amount in the first spinner item
            TypicalAmount typicalAmount = (TypicalAmount) typicalAmountSpinner.getItemAtPosition(0);
            typicalAmount.setAmount(amount);

            // ... and store it in the food
            selectedFood.get(getAdapterPosition()).setAmount(amount);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item, write amount to amount view ...
            TypicalAmount typicalAmount = (TypicalAmount) parent.getItemAtPosition(position);
            amountView.setText(String.valueOf(typicalAmount.getAmount()));

            // ... and store it as amount in the food
            selectedFood.get(getAdapterPosition()).setAmount(typicalAmount.getAmount());

            // If a typical amount (position > 0) is selected, we disable entering text,
            // if custom amount (position == 0) is selected, we enable entering text
            if (position == 0) {
                amountView.setEnabled(true);
                amountView.selectAll();
            } else {
                amountView.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Nothing to do here
        }
    }
    
    private final LayoutInflater mInflater;
    private List<Food> selectedFood; // Cached copy of all selected food items
    
    public FoodCalcAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    @Override
    @NonNull
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create the food calc item
        View itemView = mInflater.inflate(R.layout.food_calc_item, parent, false);
        return new FoodViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (selectedFood != null) {
            // Get the current food item
            Food food = selectedFood.get(position);

            // Set the food name
            holder.foodnameView.setText(food.getName());

            // Create typical amount spinner
            List<TypicalAmount> typicalAmounts = new ArrayList<>();
            
            // First entry should always contain the custom amount, which is by default 0 when first used here
            typicalAmounts.add(new TypicalAmount(food.getAmount(), null, mInflater.getContext().getString(R.string.newmeal_customamount)));

            // Append typical amounts
            appendTypicalAmount(typicalAmounts, food.getAmountSmall(), food.getCommentSmall(), mInflater.getContext().getString(R.string.amountsmall_label));
            appendTypicalAmount(typicalAmounts, food.getAmountMedium(), food.getCommentMedium(), mInflater.getContext().getString(R.string.amountmedium_label));
            appendTypicalAmount(typicalAmounts, food.getAmountLarge(), food.getCommentLarge(), mInflater.getContext().getString(R.string.amountlarge_label));

            // Create adapter and add to spinner
            ArrayAdapter<TypicalAmount> typicalAmountAdapter = new ArrayAdapter<>(mInflater.getContext(), R.layout.support_simple_spinner_dropdown_item, typicalAmounts);
            typicalAmountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            holder.typicalAmountSpinner.setOnItemSelectedListener(holder);
            holder.typicalAmountSpinner.setAdapter(typicalAmountAdapter);

            // Highlight custom amount text to make it easier to edit it
            holder.amountView.selectAll();
        } else {
            // Covers the case no food has been selected (should never happen, we check before)
            holder.foodnameView.setText(mInflater.getContext().getString(R.string.newmeal_nofoodselected));
        }
    }

    public void setSelectedFood(List<Food> selectedFood) {
        this.selectedFood = selectedFood;
    }

    public List<Food> getSelectedFood() {
        return selectedFood;
    }

    private void appendTypicalAmount(List<TypicalAmount> typicalAmounts, int amount, String comment, String defaultComment) {
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
