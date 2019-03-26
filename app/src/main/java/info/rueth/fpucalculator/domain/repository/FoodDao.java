package info.rueth.fpucalculator.domain.repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import info.rueth.fpucalculator.domain.model.Food;

/**
 * The Data Access Object interface for all food items
 */
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
