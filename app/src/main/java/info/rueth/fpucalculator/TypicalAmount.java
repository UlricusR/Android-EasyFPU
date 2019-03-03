package info.rueth.fpucalculator;

import android.support.annotation.NonNull;

public class TypicalAmount {

    private double mAmount;
    private String mComment;

    @NonNull
    private String mDefaultComment;

    public TypicalAmount(double amount, String comment, @NonNull String defaultComment) {
        this.mAmount = amount;
        this.mComment = comment;
        this.mDefaultComment = defaultComment;
    }

    public double getAmount() {
        return mAmount;
    }

    public String getComment() {
        return mComment;
    }

    public String getDefaultComment() {
        return mDefaultComment;
    }

    @Override
    public String toString() {
        if (mComment != null) return mComment;
        return mDefaultComment;
    }
}
