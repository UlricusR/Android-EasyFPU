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
    private int amount;

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

    public void setName(@NonNull String name) {
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

    // Implementation of Parcelable
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeBooleanArray(new boolean[] {favorite});
        out.writeDouble(calories);
        out.writeDouble(carbs);
        out.writeInt(amount);
        out.writeInt(amountSmall);
        out.writeInt(amountMedium);
        out.writeInt(amountLarge);
        out.writeString(commentSmall);
        out.writeString(commentMedium);
        out.writeString(commentLarge);
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
        id = in.readInt();
        name = in.readString();
        boolean[] bArray = new boolean[1];
        in.readBooleanArray(bArray);
        favorite = bArray[0];
        calories = in.readDouble();
        carbs = in.readDouble();
        amount = in.readInt();
        amountSmall = in.readInt();
        amountMedium = in.readInt();
        amountLarge = in.readInt();
        commentSmall = in.readString();
        commentMedium = in.readString();
        commentLarge = in.readString();
    }
}
