package info.rueth.fpucalculator;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

    LiveData<List<Food>> getAllFood() {
        return allFood;
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
            if (food.getID().equals(id)) return food;
        }
        return null;
    }


    public void insert(Food food) {
        new insertAsyncTask(foodDao).execute(food);
    }

    public void delete(String foodName) {
        new deleteAsyncTask(foodDao).execute(foodName);
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

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private FoodDao mAsyncTaskDao;

        deleteAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final String... params) {
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
