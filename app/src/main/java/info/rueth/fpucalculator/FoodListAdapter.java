package info.rueth.fpucalculator;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
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
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                foodItemView.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            } else {
                foodItemView.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }

            // Check if at least one is checked
            if (isAtLeastOneChecked()) {
                FloatingActionButton fabMeal = parent.getRootView().findViewById(R.id.fab_meal);
                FloatingActionButton fabNew = parent.getRootView().findViewById(R.id.fab_add);
                fabNew.setVisibility(View.GONE);
                fabMeal.setVisibility(View.VISIBLE);
            }
        }
    }

    private final LayoutInflater mInflater;
    private List<Food> allFood; // Cached copy of all food items
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private ViewGroup parent;

    FoodListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
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
