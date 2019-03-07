package info.rueth.fpucalculator;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DataRepository {
    private FoodDao foodDao;
    private LiveData<List<Food>> allFood;
    private LiveData<List<Food>> favoriteFood;

    DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        foodDao = db.foodDao();
        allFood = foodDao.getAll();
        favoriteFood = foodDao.getFavorites();
    }

    LiveData<List<Food>> getAllFood() {
        return allFood;
    }

    LiveData<List<Food>> getFavoriteFood() {
        return favoriteFood;
    }

    public void insert(Food food) {
        new insertAsyncTask(foodDao).execute(food);
    }

    public void delete(Food food) {
        new deleteAsyncTask(foodDao).execute(food);
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

    private static class deleteAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        deleteAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Food... params) {
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

}
