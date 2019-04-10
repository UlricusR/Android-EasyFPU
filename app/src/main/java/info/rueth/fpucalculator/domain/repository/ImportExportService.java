package info.rueth.fpucalculator.domain.repository;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public abstract class ImportExportService extends IntentService {

    public ImportExportService(String name) {
        super(name);
    }

    void createChannel(String notificationChannel, String notificationTitle, String notificationDescription) {
        // Version branch
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Retrieve OS service for notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Define group
            NotificationChannel channel = new NotificationChannel(
                    notificationChannel, // Unique name of the group
                    notificationTitle, // Titel of the group
                    NotificationManager.IMPORTANCE_DEFAULT); // Importance
            // Description of channel
            channel.setDescription(notificationDescription);
            // Set visibility on lock screen
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PRIVATE);
            // Create group
            manager.createNotificationChannel(channel);
        }
    }

    NotificationCompat.Builder createNotification(String notificationChannel, String notificationTitle, String notificationMessage, int icon) {
        // Create notification
        return new NotificationCompat.Builder(getApplicationContext(), notificationChannel)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSmallIcon(icon)
                .setAutoCancel(true);
    }
}
