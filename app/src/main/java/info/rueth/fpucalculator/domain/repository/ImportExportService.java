package info.rueth.fpucalculator.domain.repository;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import info.rueth.fpucalculator.R;

public abstract class ImportExportService extends IntentService {

    private static final String NOTIFICATION_CHANNEL = "Export";

    public ImportExportService(String name) {
        super(name);
    }

    void createChannel() {
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

    NotificationCompat.Builder createNotification() {
        // Create notification
        return new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL)
                .setContentTitle(getString(R.string.export_notification_title))
                .setContentText(getString(R.string.export_notification_message))
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setAutoCancel(true);
    }
}
