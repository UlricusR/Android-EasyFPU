package info.rueth.fpucalculator;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    @ColumnInfo(name = "comment_small")
    private String commentSmall;

    @ColumnInfo(name = "comment_medium")
    private String commentMedium;

    @ColumnInfo(name = "comment_large")
    private String commentLarge;

    public Food() {}

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

    public double getAmountSmall() {
        return amountSmall;
    }

    public double getAmountMedium() {
        return amountMedium;
    }

    public double getAmountLarge() {
        return amountLarge;
    }

    public String getCommentSmall() {
        return commentSmall;
    }

    public String getCommentMedium() {
        return commentMedium;
    }

    public String getCommentLarge() {
        return commentLarge;
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

    public void setCommentSmall(String comment) {
        this.commentSmall = comment;
    }

    public void setCommentMedium(String comment) {
        this.commentMedium = comment;
    }

    public void setCommentLarge(String comment) {
        this.commentLarge = comment;
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
