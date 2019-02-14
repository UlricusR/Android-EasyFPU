package info.rueth.fpucalculator;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "food_table")
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @ColumnInfo(name = "calories")
    private double calories;

    @ColumnInfo(name = "carbs")
    private double carbs;

    public Food(String name, boolean favorite, double calories, double carbs) {
        this.name = name;
        this.favorite = favorite;
        this.calories = calories;
        this.carbs = carbs;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public double getCalories() {
        return calories;
    }

    public double getCarbs() {
        return carbs;
    }
}
