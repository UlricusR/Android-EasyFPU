package info.rueth.fpucalculator;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "food_table")
public class Food implements Parcelable {
    @Ignore
    private int mData;

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

    @ColumnInfo(name = "amount_small")
    private double amountSmall;

    @ColumnInfo(name = "amount_medium")
    private double amountMedium;

    @ColumnInfo(name = "amount_large")
    private double amountLarge;

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

    public double getAmountSmall() {
        return amountSmall;
    }

    public double getAmountMedium() {
        return amountMedium;
    }

    public double getAmountLarge() {
        return amountLarge;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setAmountSmall(double amount) {
        this.amountSmall = amount;
    }

    public void setAmountMedium(double amount) {
        this.amountMedium = amount;
    }

    public void setAmountLarge(double amount) {
        this.amountLarge = amount;
    }

    // Implementation of Parcelable
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<Food> CREATOR
            = new Parcelable.Creator<Food>() {
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    private Food(Parcel in) {
        mData = in.readInt();
    }
}
