package info.rueth.fpucalculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.ArrayList;
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
            if (!allFood.get(position).isSelected()) {
                foodItemView.setChecked(false);
            } else {
                foodItemView.setChecked(true);
            }
        }

        @Override
        public void onClick(final View view) {
            FoodViewModel food = allFood.get(getAdapterPosition());
            if (!food.isSelected()) {
                // The food was not selected and is now being selected
                foodItemView.setChecked(true);
                food.setSelected(true);
            } else {
                // The food was selected and is now being unselected
                foodItemView.setChecked(false);
                food.setSelected(false);
            }
        }
    }

    private final LayoutInflater mInflater;
    private List<FoodViewModel> allFood; // Cached copy of all food items

    FoodListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.food_list_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (allFood != null) {
            FoodViewModel current = allFood.get(position);
            holder.foodItemView.setText(current.getName());
            holder.bind(position);
        } else {
            // Covers the case of data not being ready yet.
            holder.foodItemView.setText(mInflater.getContext().getString(R.string.hint_nofoodfound));
        }
    }

    void setAllFood(List<FoodViewModel> allFood) {
        this.allFood = allFood;
        notifyDataSetChanged();
    }

    FoodViewModel getFood(int position) {
        return allFood.get(position);
    }

    void deleteFood(int position) {
        allFood.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    boolean isAtLeastOneSelected() {
        for (FoodViewModel food : allFood) {
            if (food.isSelected()) return true;
        }
        return false;
    }

    ArrayList<FoodViewModel> getSelectedFood() {
        ArrayList<FoodViewModel> selectedFood = new ArrayList<>();
        for (FoodViewModel food : allFood) {
            if (food.isSelected()) selectedFood.add(food);
        }
        return selectedFood;
    }

    int[] getSelectedFoodIds() {
        ArrayList<FoodViewModel> foodArrayList = getSelectedFood();
        int[] foodIds = new int[foodArrayList.size()];
        for (int i = 0; i < foodArrayList.size(); i++) {
            foodIds[i] = foodArrayList.get(i).getId();
        }
        return foodIds;
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
