package info.rueth.fpucalculator.domain.repository;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                .setContentTitle(getString(R.string.export_notification_title))
                .setContentText(getString(R.string.export_notification_message))
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setAutoCancel(true);

        return builder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Get system service for notifications
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(getApplicationContext());
        // Create group
        createChannel();
        // Pre-fill notification
        NotificationCompat.Builder builder = createNotification();

        // Get DB path
        String currentDBPath = getBaseContext().getDatabasePath(AppDatabase.DB_NAME).getAbsolutePath();
        try {
            // Define source file
            File source = new File(currentDBPath);

            // Define target directory
            File target = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_DIR);

            // Check if writable
            if (!target.mkdirs()) {
                Log.e(LOG_TAG, getString(R.string.err_directory_not_created) + " " + target.getAbsolutePath());
                builder.setContentText(getString(R.string.err_directory_not_created) + " " + target.getAbsolutePath());
            }

            if (target.canWrite()) {
                // Publish notification
                notifyManager.notify(NOTIFICATION_ID, builder.build());

                // Get backup file
                File backupDB = new File(target, AppDatabase.DB_NAME);

                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            } else {
                Log.e(LOG_TAG, getBaseContext().getString(R.string.backup_cannotwrite) + " " + target.getAbsolutePath());
                builder.setContentText(getBaseContext().getString(R.string.backup_cannotwrite) + " " + target.getAbsolutePath());
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, getBaseContext().getString(R.string.backup_failed));
            builder.setContentText(getBaseContext().getString(R.string.backup_failed));
        }

    }
}
