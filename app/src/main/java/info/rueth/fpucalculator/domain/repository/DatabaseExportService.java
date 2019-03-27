package info.rueth.fpucalculator.domain.repository;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import androidx.annotation.Nullable;
import info.rueth.fpucalculator.R;

public class DatabaseExportService extends IntentService {

    private static final String LOG_TAG = "DatabaseExportService";
    private static final String BACKUP_DIR = "FPU-Calculator";

    public DatabaseExportService() {
        super("DatabaseExporter");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String currentDBPath = getBaseContext().getDatabasePath(AppDatabase.DB_NAME).getAbsolutePath();
        try {
            // Define source file
            File source = new File(currentDBPath);

            // Define target directory
            File target = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_DIR);

            // Check if writable
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
                    Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.backup_complete) + ": " + target.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.backup_cannotwrite) + " " + target.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.backup_failed), Toast.LENGTH_SHORT).show();
        }

    }
}
