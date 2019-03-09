package info.rueth.fpucalculator;

import android.arch.lifecycle.ViewModel;

public class FoodViewModel extends ViewModel {
    private int id;
    private String name;
    private boolean favorite;
    private double caloriesPer100g;
    private double carbsPer100g;
    private int amount;
    private int amountSmall;
    private int amountMedium;
    private int amountLarge;
    private String commentSmall;
    private String commentMedium;
    private String commentLarge;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(double caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public double getCarbsPer100g() {
        return carbsPer100g;
    }

    public void setCarbsPer100g(double carbsPer100g) {
        this.carbsPer100g = carbsPer100g;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountSmall() {
        return amountSmall;
    }

    public void setAmountSmall(int amountSmall) {
        this.amountSmall = amountSmall;
    }

    public int getAmountMedium() {
        return amountMedium;
    }

    public void setAmountMedium(int amountMedium) {
        this.amountMedium = amountMedium;
    }

    public int getAmountLarge() {
        return amountLarge;
    }

    public void setAmountLarge(int amountLarge) {
        this.amountLarge = amountLarge;
    }

    public String getCommentSmall() {
        return commentSmall;
    }

    public void setCommentSmall(String commentSmall) {
        this.commentSmall = commentSmall;
    }

    public String getCommentMedium() {
        return commentMedium;
    }

    public void setCommentMedium(String commentMedium) {
        this.commentMedium = commentMedium;
    }

    public String getCommentLarge() {
        return commentLarge;
    }

    public void setCommentLarge(String commentLarge) {
        this.commentLarge = commentLarge;
    }
}
