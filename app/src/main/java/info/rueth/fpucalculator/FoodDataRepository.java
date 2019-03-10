package info.rueth.fpucalculator;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class FoodDataRepository {
    private FoodDao foodDao;
    private LiveData<List<Food>> allFood;
    private static volatile FoodDataRepository INSTANCE;

    static FoodDataRepository getInstance(Application application) {
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

    LiveData<List<FoodViewModel>> getAllFood(boolean favorite) {
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

    Food getFoodByName(String foodName) {
        List<Food> foods = allFood.getValue();
        for (Food food : foods) {
            if (food.getName().equals(foodName)) return food;
        }
        return null;
    }
    
    Food getFoodByID(int id) {
        List<Food> foods = allFood.getValue();
        for (Food food : foods) {
            if (food.getId() == id) return food;
        }
        return null;
    }


    public void insert(Food food) {
        new insertAsyncTask(foodDao).execute(food);
    }

    public void delete(int id) {
        new deleteAsyncTask(foodDao).execute(id);
    }

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

    private static class findByNameAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        findByNameAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            mAsyncTaskDao.findByName(params[0].getName());
            return null;
        }
    }

}
