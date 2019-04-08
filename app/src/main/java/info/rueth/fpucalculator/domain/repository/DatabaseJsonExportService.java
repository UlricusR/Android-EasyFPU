package info.rueth.fpucalculator.domain.repository;

import android.content.Intent;
import android.net.Uri;
import android.util.JsonWriter;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.model.Food;

public class DatabaseJsonExportService extends DatabaseExportService {

    private static final String LOG_TAG = "DatabaseJsonExportService";
    private static final int NOTIFICATION_ID = 600;
    private static final String FOOD_ITEMS = "food_items";
    private static final String FOOD_NAME = "name";
    private static final String CALORIES_PER_100G = "calories_per_100g";
    private static final String CARBS_PER_100G = "carbs_per_100g";
    private static final String AMOUNT_SMALL = "amount_small";
    private static final String AMOUNT_MEDIUM = "amount_medium";
    private static final String AMOUNT_LARGE = "amount_large";
    private static final String COMMENT_SMALL = "comment_small";
    private static final String COMMENT_MEDIUM = "comment_medium";
    private static final String COMMENT_LARGE = "comment_large";

    public DatabaseJsonExportService() {
        super("DatabaseJsonExporter");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Get system service for notifications
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(getApplicationContext());

        // Create group
        createChannel();

        // Pre-fill notification
        NotificationCompat.Builder builder = createNotification();

        // Read file uri from intent and check if it has been set
        if (intent == null) {
            Log.e(LOG_TAG, getString(R.string.err_intentnull));
            builder.setContentText(getText(R.string.err_intentnull));
            notifyManager.notify(NOTIFICATION_ID, builder.build());
            return;
        }
        Uri exportFile = intent.getData();
        if (exportFile == null) {
            Log.e(LOG_TAG, getString(R.string.err_filename_missing));
            builder.setContentText(getString(R.string.err_filename_missing));
            notifyManager.notify(NOTIFICATION_ID, builder.build());
            return;
        }

        // Get all food entries
        List<Food> foodData = FoodDataRepository.getInstance(getApplication()).getAllFood();

        // Prepare output stream
        OutputStream os = null;
        JsonWriter writer = null;

        try {
            // Initialize output stream to write data
            os = getContentResolver().openOutputStream(exportFile);

            // Create JSON writer
            writer = new JsonWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

            // Prepare writing data
            writer.setIndent("  ");
            writeFoodItems(writer, foodData);
        } catch (Exception e) {
            Log.i(LOG_TAG, getBaseContext().getString(R.string.backup_failed));
            builder.setContentText(getBaseContext().getString(R.string.backup_failed));
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
                builder.setContentText(getBaseContext().getString(R.string.backup_complete));
            } catch (IOException e) {
                Log.i(LOG_TAG, e.getLocalizedMessage());
                builder.setContentText(getBaseContext().getString(R.string.backup_failed));
            }
        }

        // Notify user
        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }
    
    private void writeFoodItems() {
        writer.name(FOOD_ITEMS);
        writer.beginArray();
        for (Food food : foodData) {
            writeFood(writer, food);
        }
        writer.endArray();
    }

    private void writeFood(JsonWriter writer, Food food) throws IOException {
        writer.beginObject();
        writer.name(FOOD_NAME).value(food.getName());
        writer.name(CALORIES_PER_100G).value(food.getCaloriesPer100g());
        writer.name(CARBS_PER_100G).value(food.getCarbsPer100g());
        writer.name(AMOUNT_SMALL).value(food.getAmountSmall());
        writer.name(AMOUNT_MEDIUM).value(food.getAmountMedium());
        writer.name(AMOUNT_LARGE).value(food.getAmountLarge());
        if (food.getCommentSmall() != null) writer.name(COMMENT_SMALL).value(food.getCommentSmall());
        if (food.getCommentMedium() != null) writer.name(COMMENT_MEDIUM).value(food.getCommentMedium());
        if (food.getCommentLarge() != null) writer.name(COMMENT_LARGE).value(food.getCommentLarge());
        writer.endObject();
    }
}
