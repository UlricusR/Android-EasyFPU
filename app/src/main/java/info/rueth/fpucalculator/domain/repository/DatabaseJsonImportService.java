package info.rueth.fpucalculator.domain.repository;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import info.rueth.fpucalculator.R;

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
        Uri importFile = intent.getData();
        if (importFile == null) {
            Log.e(LOG_TAG, getString(R.string.err_filename_missing));
            builder.setContentText(getString(R.string.err_filename_missing));
            notifyManager.notify(NOTIFICATION_ID, builder.build());
            return;
        }

        // Ask user for replace or append mode
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        // Set title and message
        alert.setTitle(R.string.dbimport_dialog_mode_title);
        alert.setMessage(R.string.dbimport_dialog_mode_message);

        // Set positive button = replace
        alert.setPositiveButton(R.string.dbimport_dialog_mode_button_replace, (dialog, which) -> {
            importData(notifyManager, builder, importFile, true);
        });

        // Set negative button = append
        alert.setNegativeButton(R.string.dbimport_dialog_mode_button_append, (dialog, which) -> {
            importData(notifyManager, builder, importFile, false);
        });

        // Show
        alert.create().show();
    }

    private void importData(NotificationManagerCompat notifyManager, NotificationCompat.Builder builder, Uri importFile, boolean replace) {

    }
}
