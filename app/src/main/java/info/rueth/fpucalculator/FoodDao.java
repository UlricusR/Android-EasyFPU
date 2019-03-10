package info.rueth.fpucalculator;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food_table ORDER BY name ASC")
    LiveData<List<Food>> getAll();

    @Query("SELECT * FROM food_table WHERE id IN (:foodIds) ORDER BY name ASC")
    List<Food> loadAllByIds(int[] foodIds);

    @Query("SELECT * FROM food_table WHERE name LIKE :foodName")
    List<Food> findByName(String foodName);

    @Insert
    void insertAll(Food... food);

    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Query("DELETE FROM food_table WHERE id LIKE :id")
    void delete(int id);

    @Query("DELETE FROM food_table")
    void deleteAll();
}
