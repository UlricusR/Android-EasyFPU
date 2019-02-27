package info.rueth.fpucalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CheckedTextView foodItemView;

        private FoodViewHolder(View itemView) {
            super(itemView);
            foodItemView = itemView.findViewById(R.id.food_name);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            // Use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {
                foodItemView.setChecked(false);
            } else {
                foodItemView.setChecked(true);
            }
        }

        @Override
        public void onClick(final View view) {
            final int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) { // The food is selected
                foodItemView.setChecked(true);
                itemStateArray.put(adapterPosition, true);

                // Open the AlertDialog to enter the carbs
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the message
                builder.setMessage(R.string.edit_amount_message);

                // Get the layout
                builder.setView(R.layout.edit_amount);

                // Add buttons
                builder.setPositiveButton(R.string.confirm_amount_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Food selectedFood = getFood(adapterPosition);
                        EditText mAmount = view.findViewById(R.id.food_amount);
                        double amount = Double.parseDouble(mAmount.getText().toString());
                        meal.add(selectedFood, amount);
                    }
                });

                builder.setNegativeButton(R.string.cancel_amount_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog, so we need to unset the food checkbox
                        foodItemView.setChecked(false);
                        itemStateArray.put(adapterPosition, false);
                    }
                });

                // Create alert dialog object
                AlertDialog dialog = builder.create();

                // Avoid using the Android back button or clicking outside
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);

                // Show the dialog
                dialog.show();
            } else { // The food is unselected
                foodItemView.setChecked(false);
                itemStateArray.put(adapterPosition, false);

                // We need to remove the food from the meal
                Food unselectedFood = getFood(adapterPosition);
                meal.remove(unselectedFood);
            }
        }
    }

    private final LayoutInflater mInflater;
    private List<Food> allFood; // Cached copy of all food items
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private Context context;
    private Meal meal;

    FoodListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        if (allFood != null) {
            Food current = allFood.get(position);
            holder.foodItemView.setText(current.getName());
            holder.bind(position);
        } else {
            // Covers the case of data not being ready yet.
            holder.foodItemView.setText(mInflater.getContext().getString(R.string.hint_nofoodfound));
        }
    }

    void setAllFood(List<Food> allFood) {
        this.allFood = allFood;
        notifyDataSetChanged();
    }

    Food getFood(int position) {
        return allFood.get(position);
    }

    Food deleteFood(int position) {
        Food foodToDelete = allFood.get(position);
        allFood.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        return foodToDelete;
    }

    boolean isAtLeastOneChecked() {
        for (int i = 0; i < itemStateArray.size(); i++) {
            if (itemStateArray.valueAt(i)) return true;
        }
        return false;
    }

    // getItemCount() is called many times, and when it is first called,
    // allFood has not been updated (means initially, it's null, and we can't return null)
    @Override
    public int getItemCount() {
        if (allFood != null)
            return allFood.size();
        else return 0;
    }
}
