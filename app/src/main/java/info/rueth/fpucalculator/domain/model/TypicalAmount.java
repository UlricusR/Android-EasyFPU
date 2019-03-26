package info.rueth.fpucalculator.domain.model;

import androidx.annotation.NonNull;

/**
 * Represents a typical amount of a food
 */
public class TypicalAmount {

    private int mAmount;
    private String mComment;

    @NonNull
    private String mDefaultComment;

    /**
     * Builds a typical amount
     * @param amount The typical amount of the food in g
     * @param comment A comment on the typical amount
     * @param defaultComment The default comment on the typical amount
     */
    public TypicalAmount(int amount, String comment, @NonNull String defaultComment) {
        this.mAmount = amount;
        this.mComment = comment;
        this.mDefaultComment = defaultComment;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getComment() {
        return mComment;
    }

    public String getDefaultComment() {
        return mDefaultComment;
    }

    /**
     * @return The comment if available, otherwise the default comment
     */
    @Override
    @NonNull
    public String toString() {
        if (mComment != null) return mComment;
        return mDefaultComment;
    }
}
