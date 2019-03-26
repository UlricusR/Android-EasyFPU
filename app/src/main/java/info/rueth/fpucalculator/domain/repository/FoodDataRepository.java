package info.rueth.fpucalculator.domain.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import info.rueth.fpucalculator.domain.model.Food;
import info.rueth.fpucalculator.presentation.viewmodels.FoodViewModel;

/**
 * Holds a cached copy of all currently available food items and interacts with the database
 */
public class FoodDataRepository {
    private FoodDao foodDao;
    private LiveData<List<Food>> allFood;
    private static volatile FoodDataRepository INSTANCE;

    /**
     * Singleton implementation to retrieve the food items
     * @param application
     * @return
     */
    public static FoodDataRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (FoodDataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FoodDataRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private FoodDataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        foodDao = db.foodDao();
        allFood = foodDao.getAll();
    }

    /**
     * @param favorite Determines whether of not to return all food items or only the favorite ones
     * @return A LiveData List of the food items
     */
    public LiveData<List<FoodViewModel>> getAllFood(boolean favorite) {
        return Transformations.map(allFood, newData -> createFoodViewModel(newData, favorite));
    }

    private List<FoodViewModel> createFoodViewModel(List<Food> list, boolean favorite) {
        List<FoodViewModel> foodVM = new ArrayList<>();
        FoodViewModel foodViewModel;
        for (Food item : list) {
            if (favorite) {
                if (item.isFavorite()) {
                    foodViewModel = createViewModel(item);
                    foodVM.add(foodViewModel);
                }
            } else {
                foodViewModel = createViewModel(item);
                foodVM.add(foodViewModel);
            }
        }
        return foodVM;
    }

    private FoodViewModel createViewModel(Food food) {
        FoodViewModel viewModel = new FoodViewModel();
        viewModel.setId(food.getId());
        viewModel.setSelected(food.isSelected());
        viewModel.setName(food.getName());
        viewModel.setFavorite(food.isFavorite());
        viewModel.setCaloriesPer100g(food.getCaloriesPer100g());
        viewModel.setCarbsPer100g(food.getCarbsPer100g());
        viewModel.setAmount(food.getAmount());
        viewModel.setAmountSmall(food.getAmountSmall());
        viewModel.setAmountMedium(food.getAmountMedium());
        viewModel.setAmountLarge(food.getAmountLarge());
        viewModel.setCommentSmall(food.getCommentSmall());
        viewModel.setCommentMedium(food.getCommentMedium());
        viewModel.setCommentLarge(food.getCommentLarge());
        return viewModel;
    }

    /**
     * @param foodName The name of the food to find
     * @return The food matching the name (in case of duplicate names the first one)
     */
    public Food getFoodByName(String foodName) {
        List<Food> foods = allFood.getValue();
        if (foods == null) return null;
        for (Food food : foods) {
            if (food.getName().equals(foodName)) return food;
        }
        return null;
    }

    /**
     * @param id The id of the food item
     * @return The food item
     */
    public Food getFoodByID(int id) {
        List<Food> foods = allFood.getValue();
        if (foods == null) return null;
        for (Food food : foods) {
            if (food.getId() == id) return food;
        }
        return null;
    }

    /**
     * @param foodIds The IDs of the food items to return
     * @return All food items with the specified IDs
     */
    public List<Food> getFoodByIDs(int[] foodIds) {
        List<Food> foods = new ArrayList<>();
        for (int foodId : foodIds) {
            foods.add(getFoodByID(foodId));
        }
        return foods;
    }


    /**
     * @param food Inserts a Food into the repository and database
     */
    public void insert(Food food) {
        new insertAsyncTask(foodDao).execute(food);
    }

    /**
     * Deletes a food from the repository and database
     * @param id The ID of the food to delete
     */
    public void delete(int id) {
        new deleteAsyncTask(foodDao).execute(id);
    }

    /**
     * Updates the food in the repository/database
     * @param food The food to update
     */
    public void update(Food food) {
        new updateAsyncTask(foodDao).execute(food);
    }

    private static class insertAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private FoodDao mAsyncTaskDao;

        deleteAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Integer... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        updateAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class findByIdAsyncTask extends AsyncTask<Integer, Void, List<Food>> {

        private FoodDao mAsyncTaskDao;

        findByIdAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Food> doInBackground(Integer... ids) {
            int[] foodIds = new int[ids.length];
            for (int i = 0; i < ids.length; i++) {
                foodIds[i] = ids[i];
            }
            return mAsyncTaskDao.loadAllByIds(foodIds);
        }
    }

}
