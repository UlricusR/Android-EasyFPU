package info.rueth.fpucalculator.domain.repository;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import info.rueth.fpucalculator.R;

public class DatabaseRawExportService extends ImportExportService {

    private static final String LOG_TAG = "DatabaseRawExportService";
    private static final int NOTIFICATION_ID = 500;

    public DatabaseRawExportService() {
        super("DatabaseRawExporter");
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

        // Get DB path and define source file
        String currentDBPath = getBaseContext().getDatabasePath(AppDatabase.DB_NAME).getAbsolutePath();
        File source = new File(currentDBPath);

        // Create output stream
        OutputStream os = null;

        try {
            // Get input stream
            FileInputStream src = new FileInputStream(source);

            // Initialize output stream to write data
            os = getContentResolver().openOutputStream(exportFile);

            // Copy
            if (source.exists() && os != null) {
                // Transfer bytes from source to output stream
                byte[] buffer = new byte[1024];
                int len;
                while ((len = src.read(buffer)) > 0) {
                    os.write(buffer, 0, len);
                }
                src.close();
                Log.e(LOG_TAG, getBaseContext().getString(R.string.backup_complete));
                builder.setContentText(getBaseContext().getString(R.string.backup_complete));
            } else {
                Log.e(LOG_TAG, getString(R.string.err_database_not_found));
                builder.setContentText(getString(R.string.err_database_not_found));
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, getBaseContext().getString(R.string.backup_failed));
            builder.setContentText(getBaseContext().getString(R.string.backup_failed));
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                Log.i(LOG_TAG, e.getLocalizedMessage());
                builder.setContentText(getBaseContext().getString(R.string.backup_failed));
            }
        }

        // Notify user
        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
