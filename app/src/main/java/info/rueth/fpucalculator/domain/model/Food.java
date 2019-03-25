package info.rueth.fpucalculator.domain.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    public double getCalories() {
        return amount * caloriesPer100g / 100;
    }

    public double getCarbs() {
        return amount * carbsPer100g / 100;
    }

    /**
     * Calculates the Fat Protein Units of the food.
     *
     * @return The FPU associated with that food
     */
    public FPU getFPU() {
        // 1g carbs has ~4 kcal, so calculate carb portion of calories
        double carbsCal = amount / 100 * carbsPer100g * 4;

        // The carbs from fat and protein is the remainder
        double calFromFP = getCalories() - carbsCal;

        // 100kcal makes 1 FPU
        double fpus = calFromFP / 100;

        // Create and return the FPU object
        return new FPU(fpus);
    }
}
