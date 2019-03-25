package info.rueth.fpucalculator.presentation.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.presentation.ui.EditFoodActivity;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;
import info.rueth.fpucalculator.usecases.FoodDelete;
import info.rueth.fpucalculator.usecases.FoodUpdate;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    class FoodViewHolder extends RecyclerView.ViewHolder {
        private final CheckedTextView foodItemView;
        private final TextView moreIcon;

        private FoodViewHolder(View itemView) {
            super(itemView);

            // The food item
            foodItemView = itemView.findViewById(R.id.food_name);

            // The more icon
            moreIcon = itemView.findViewById(R.id.more_icon);
        }

        void bind(int position) {
            // Check / uncheck
            if (!allFood.get(position).isSelected()) {
                foodItemView.setChecked(false);
            } else {
                foodItemView.setChecked(true);
            }
        }
    }

    private Context mContext;
    private Application mApplication;
    private LayoutInflater mInflater;
    private List<FoodViewModel> allFood; // Cached copy of all food items
    public static final String FOOD_ID = "info.rueth.fpucalculator.FoodID";

    public FoodListAdapter(Context context, Application application) {
        mContext = context;
        mApplication = application;
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
        // Set the food name to the food item view
        if (allFood != null) {
            FoodViewModel current = allFood.get(position);
            holder.foodItemView.setText(current.getName());
            holder.bind(position);
        } else {
            // Covers the case of data not being ready yet.
            holder.foodItemView.setText(mInflater.getContext().getString(R.string.hint_nofoodfound));
        }

        // Set an onClickListener to the food item view
        holder.foodItemView.setOnClickListener(view -> {
            FoodViewModel food = allFood.get(position);
            if (!food.isSelected()) {
                // The food was not selected and is now being selected
                ((CheckedTextView) view).setChecked(true);
                food.setSelected(true);
            } else {
                // The food was selected and is now being unselected
                ((CheckedTextView) view).setChecked(false);
                food.setSelected(false);
            }
        });

        // Set an onClickListener to the more icon
        holder.moreIcon.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(mContext, holder.moreIcon);

            // Inflate the menu
            menu.inflate(R.menu.context_menu_foodlist);

            // Set the correct text to the favorite menu item
            if (allFood.get(position).isFavorite()) {
                menu.getMenu().findItem(R.id.menu_favorite).setTitle(R.string.menu_favorite_remove);
            } else {
                menu.getMenu().findItem(R.id.menu_favorite).setTitle(R.string.menu_favorite_add);
            }

            // Set listener
            menu.setOnMenuItemClickListener(new OnMenuItemClicked(position));

            // Show menu
            menu.show();
        });
    }

    public void setAllFood(List<FoodViewModel> allFood) {
        this.allFood = allFood;
        notifyDataSetChanged();
    }

    private FoodViewModel getFood(int position) {
        return allFood.get(position);
    }

    private void deleteFood(int position) {
        allFood.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public boolean isAtLeastOneSelected() {
        for (FoodViewModel food : allFood) {
            if (food.isSelected()) return true;
        }
        return false;
    }

    private ArrayList<FoodViewModel> getSelectedFood() {
        ArrayList<FoodViewModel> selectedFood = new ArrayList<>();
        for (FoodViewModel food : allFood) {
            if (food.isSelected()) selectedFood.add(food);
        }
        return selectedFood;
    }

    public int[] getSelectedFoodIds() {
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

    class OnMenuItemClicked implements PopupMenu.OnMenuItemClickListener {

        private final int mPosition;

        OnMenuItemClicked(int position) {
            mPosition = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_favorite:
                    FoodViewModel food = allFood.get(mPosition);
                    if (food.isFavorite()) {
                        // The food is already a favorite, so remove
                        food.setFavorite(false);
                        // Change the menu item text to adding it back as favorite
                        item.setTitle(R.string.menu_favorite_add);
                    } else {
                        // The food is no favorite, so make it one
                        food.setFavorite(true);
                        // Change the menu item text to removing it as favorite
                        item.setTitle(R.string.menu_favorite_remove);
                    }
                    // Update food in repository
                    new FoodUpdate(mApplication).execute(food);
                    return true;
                case R.id.menu_edit_food:
                    Intent intent = new Intent(mContext, EditFoodActivity.class);
                    int id = getFood(mPosition).getId();
                    intent.putExtra(FOOD_ID, id);
                    mContext.startActivity(intent);
                    return true;
                case R.id.menu_delete_food:
                    FoodViewModel viewModel = getFood(mPosition);
                    new FoodDelete(mApplication).execute(viewModel);
                    deleteFood(mPosition);
                    return true;
                default:
                    return false;
            }
        }
    }
}
