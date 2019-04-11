package info.rueth.fpucalculator.domain.repository;

import android.content.Intent;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.model.Food;
import info.rueth.fpucalculator.presentation.ui.MainActivity;

public class DatabaseJsonImportService extends ImportExportService {

    private static final String LOG_TAG = "DatabaseJsonImportService";
    private static final int NOTIFICATION_ID = 700;

    public DatabaseJsonImportService() {
        super("DatabaseJsonImporter");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Get system service for notifications
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(getApplicationContext());

        String notificationChannel = "JSONImport";

        // Create group
        createChannel(
                notificationChannel,
                getString(R.string.import_notification_channel_title),
                getString(R.string.import_notification_channel_description)
        );

        // Pre-fill notification
        NotificationCompat.Builder builder = createNotification(
                notificationChannel,
                getString(R.string.import_notification_title),
                getString(R.string.import_notification_message),
                R.drawable.ic_file_upload_black_24dp
        );

        // Read file uri from intent and check if it has been set
        if (intent == null) {
            Log.e(LOG_TAG, getString(R.string.err_intentnull));
            builder.setContentText(getText(R.string.err_intentnull));
            notifyManager.notify(NOTIFICATION_ID, builder.build());
            return;
        }
        Uri importFile = intent.getData();
        if (importFile == null) {
            Log.e(LOG_TAG, getString(R.string.err_filename_missing));
            builder.setContentText(getString(R.string.err_filename_missing));
            notifyManager.notify(NOTIFICATION_ID, builder.build());
            return;
        }

        // Get append mode, default is true to avoid loss of data
        boolean appendData = intent.getBooleanExtra(MainActivity.SERVICE_IMPORTJSON, true);

        // Prepare input stream and create JSON reader
        InputStream is =  null;

        try {
            // Initialize input stream to read data
            is = getContentResolver().openInputStream(importFile);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
            builder.setContentText(getBaseContext().getString(R.string.import_failed) + ": " + e.getLocalizedMessage());
            notifyManager.notify(NOTIFICATION_ID, builder.build());
            return;
        }

        // Create JSON reader
        JsonReader reader = new JsonReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        // Prepare reading data
        readData(reader, appendData, notifyManager, builder);

        try {
            reader.close();
            if (is != null) is.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
            builder.setContentText(getBaseContext().getString(R.string.import_failed) + ": " + e.getLocalizedMessage());
            notifyManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void readData(JsonReader reader, boolean appendData, NotificationManagerCompat notifyManager, NotificationCompat.Builder builder) {
        // Get food data repository
        FoodDataRepository repository = FoodDataRepository.getInstance(getApplication());

        // Prepare imported food list
        List<Food> importedFood = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(DatabaseJsonExportService.FOOD_ITEMS)) {
                    importedFood = readFoodItems(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            builder.setContentText(getBaseContext().getString(R.string.import_complete));
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());

            // Set imported food to null, so that we recover existing food in the finally clause
            importedFood = null;

            // Notify user
            builder.setContentText(getBaseContext().getString(R.string.import_failed) + ": " + e.getLocalizedMessage());
        } finally {
            if (importedFood != null) {
                // We have imported food, so check if it should be replaced
                if (!appendData) repository.deleteAll();

                // Now insert imported food
                for (Food food : importedFood) {
                    repository.insert(food);
                }
            }

            // Notify user
            notifyManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private List<Food> readFoodItems(JsonReader reader) throws IOException {
        // Create list of food items
        List<Food> importedFoodItems = new ArrayList<>();

        // Iterate through food items
        reader.beginArray();
        while (reader.hasNext()) {
            importedFoodItems.add(readFoodItem(reader));
        }
        reader.endArray();

        // Return imported food items
        return importedFoodItems;
    }

    private Food readFoodItem(JsonReader reader) throws IOException {
        // Create food
        Food food = new Food();

        // Read food item and insert into food
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case DatabaseJsonExportService.FOOD_NAME:
                    food.setName(reader.nextString());
                    break;
                case DatabaseJsonExportService.FAVORITE:
                    food.setFavorite(reader.nextBoolean());
                    break;
                case DatabaseJsonExportService.CALORIES_PER_100G:
                    food.setCaloriesPer100g(reader.nextInt());
                    break;
                case DatabaseJsonExportService.CARBS_PER_100G:
                    food.setCarbsPer100g(reader.nextInt());
                    break;
                case DatabaseJsonExportService.AMOUNT_SMALL:
                    food.setAmountSmall(reader.nextInt());
                    break;
                case DatabaseJsonExportService.AMOUNT_MEDIUM:
                    food.setAmountMedium(reader.nextInt());
                    break;
                case DatabaseJsonExportService.AMOUNT_LARGE:
                    food.setAmountLarge(reader.nextInt());
                    break;
                case DatabaseJsonExportService.COMMENT_SMALL:
                    food.setCommentSmall(reader.nextString());
                    break;
                case DatabaseJsonExportService.COMMENT_MEDIUM:
                    food.setCommentMedium(reader.nextString());
                    break;
                case DatabaseJsonExportService.COMMENT_LARGE:
                    food.setCommentLarge(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return food;
    }
}
