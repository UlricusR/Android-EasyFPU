package info.rueth.fpucalculator.domain.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import info.rueth.fpucalculator.R;

public class MaintenanceUtils {

    private static final String LOG_TAG = "MaintenanceUtils";
    private static final String BACKUP_DIR = "FPU-Calculator";

    /**
     * Backs up the database
     * @param context The context called from
     */
    public static void backupDatabase(Context context) {
        String currentDBPath = context.getDatabasePath(AppDatabase.DB_NAME).getAbsolutePath();
        try {
            File source = new File(currentDBPath);
            File target = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_DIR);
            if (!target.mkdirs()) {
                Log.e(LOG_TAG, "Directory not created: " + target.getAbsolutePath());
            }

            if (target.canWrite()) {
                File backupDB = new File(target, AppDatabase.DB_NAME);

                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(context, context.getString(R.string.backup_complete) + ": " + target.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, context.getString(R.string.backup_cannotwrite) + " " + target.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.backup_failed), Toast.LENGTH_SHORT).show();
        }
    }

    public static void loadDatabase(Context context) {
        // TODO
    }
}
