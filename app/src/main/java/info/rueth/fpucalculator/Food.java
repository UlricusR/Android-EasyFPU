package info.rueth.fpucalculator;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "food_table")
public class Food {
    @Ignore
    private int amount;

    @Ignore
    private boolean selected;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @ColumnInfo(name = "calories")
    private double caloriesPer100g;

    @ColumnInfo(name = "carbs")
    private double carbsPer100g;

    @ColumnInfo(name = "amount_small")
    private int amountSmall;

    @ColumnInfo(name = "amount_medium")
    private int amountMedium;

    @ColumnInfo(name = "amount_large")
    private int amountLarge;

    @ColumnInfo(name = "comment_small")
    private String commentSmall;

    @ColumnInfo(name = "comment_medium")
    private String commentMedium;

    @ColumnInfo(name = "comment_large")
    private String commentLarge;

    public Food() {}

    @Ignore
    public int getAmount() {
        return amount;
    }

    @Ignore
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Ignore
    public boolean isSelected() {
        return selected;
    }

    @Ignore
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @NonNull
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public double getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setCaloriesPer100g(double caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public void setCarbsPer100g(double carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public int getAmountSmall() {
        return amountSmall;
    }

    public int getAmountMedium() {
        return amountMedium;
    }

    public int getAmountLarge() {
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

    public void setAmountSmall(int amount) {
        this.amountSmall = amount;
    }

    public void setAmountMedium(int amount) {
        this.amountMedium = amount;
    }

    public void setAmountLarge(int amount) {
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
}
