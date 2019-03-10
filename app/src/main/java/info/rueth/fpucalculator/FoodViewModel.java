package info.rueth.fpucalculator;

import android.arch.lifecycle.ViewModel;
import android.os.Parcel;
import android.os.Parcelable;

public class FoodViewModel extends ViewModel implements Parcelable {
    private int id;
    private boolean selected;
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

    public FoodViewModel() {
        // Intentionally left blank
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    // Implementation of Parcelable
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeBooleanArray(new boolean[] {favorite});
        out.writeDouble(caloriesPer100g);
        out.writeDouble(carbsPer100g);
        out.writeInt(amount);
        out.writeInt(amountSmall);
        out.writeInt(amountMedium);
        out.writeInt(amountLarge);
        out.writeString(commentSmall);
        out.writeString(commentMedium);
        out.writeString(commentLarge);
    }

    public static final Parcelable.Creator<FoodViewModel> CREATOR
            = new Parcelable.Creator<FoodViewModel>() {
        public FoodViewModel createFromParcel(Parcel in) {
            return new FoodViewModel(in);
        }

        public FoodViewModel[] newArray(int size) {
            return new FoodViewModel[size];
        }
    };

    private FoodViewModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        boolean[] bArray = new boolean[1];
        in.readBooleanArray(bArray);
        favorite = bArray[0];
        caloriesPer100g = in.readDouble();
        carbsPer100g = in.readDouble();
        amount = in.readInt();
        amountSmall = in.readInt();
        amountMedium = in.readInt();
        amountLarge = in.readInt();
        commentSmall = in.readString();
        commentMedium = in.readString();
        commentLarge = in.readString();
    }
}