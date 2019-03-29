package info.rueth.fpucalculator.domain.repository;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import info.rueth.fpucalculator.R;

public class DatabaseExportService extends IntentService {

    private static final String LOG_TAG = "DatabaseExportService";
    private static final String BACKUP_DIR = "FPU-Calculator";
    private static final String NOTIFICATION_CHANNEL = "Export";
    private static final int NOTIFICATION_ID = 500;

    public DatabaseExportService() {
        super("DatabaseExporter");
    }

    private void createChannel() {
        // Version branch
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Retrieve OS service for notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Define group
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL, // Unique name of the group
                    getString(R.string.export_notification_channel), // Titel of the group
                    NotificationManager.IMPORTANCE_DEFAULT); // Importance
            // Description of channel
            channel.setDescription(getString(R.string.export_notification_channel_description));
            // Set visibility on lock screen
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PRIVATE);
            // Create group
            manager.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder createNotification() {
        // Create notification
        return new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                .setContentTitle(getString(R.string.export_notification_title))
                .setContentText(getString(R.string.export_notification_message))
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setAutoCancel(true);
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
            if (source.exists()) {
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
