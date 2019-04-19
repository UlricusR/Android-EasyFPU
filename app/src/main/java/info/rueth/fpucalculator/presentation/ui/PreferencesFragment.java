package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.regex.Pattern;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;
import info.rueth.fpucalculator.domain.repository.DatabaseJsonExportService;
import info.rueth.fpucalculator.domain.repository.DatabaseJsonImportService;
import info.rueth.fpucalculator.domain.repository.DatabaseRawExportService;

import static android.app.Activity.RESULT_OK;

public class PreferencesFragment extends PreferenceFragmentCompat {
    private static final String LOG_TAG = "PreferencesFragment";
    private static final int SAF_CREATE_RAW_EXPORT_FILE = 200;
    private static final int SAF_CREATE_JSON_EXPORT_FILE = 300;
    private static final int SAF_CREATE_JSON_IMPORT_FILE = 400;

    private static final String SERVICE_IMPORTJSON = "info.rueth.fpucalculator.ImportJson";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Add onClickListener for editing absorption scheme
        Preference editAbsorptionSchemePref = findPreference(getString(R.string.preference_absorptionscheme_edit_key));
        editAbsorptionSchemePref.setOnPreferenceClickListener(preference -> {
            // Edit absorption scheme
            startActivity(new Intent(getContext(), AbsorptionSchemeActivity.class));
            return true;
        });

        // Add text watcher to avoid that the user enters other than numeric values;
        // this is a workaround because androidx.preference.PreferenceScreen
        // currently ignores android:inputType="numberDecimal" (seems to be a bug)
        EditTextPreference maxFPUPref = findPreference(getString(R.string.preference_maxfpu_key));
        EditTextPreference maxAbsorptionTimePref = findPreference(getString(R.string.preference_maxabsorptiontime_key));

        try {
            maxFPUPref.setOnPreferenceChangeListener(new NonNegativeIntegerChecker(AbsorptionSchemeRepository.getInstance(getContext()).getAbsorptionScheme().getMaximumFPUs()));
            maxAbsorptionTimePref.setOnPreferenceChangeListener(new NonNegativeIntegerChecker(AbsorptionSchemeRepository.getInstance(getContext()).getAbsorptionScheme().getMaximumAbsorptionTime()));
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
            Toast.makeText(getContext(), R.string.err_cannotcreatepreferences + ": " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        // Get the database maintenance preferences and set onClickListener
        Preference dbExportJsonPref = findPreference(getString(R.string.preference_dbexport_json_key));
        dbExportJsonPref.setOnPreferenceClickListener(preference -> {
            // Export database
            Intent jsonFileExportIntent = selectFileForExport("fpu_calculator_database.json", "application/json");
            // Start activity
            startActivityForResult(jsonFileExportIntent, SAF_CREATE_JSON_EXPORT_FILE);
            return true;
        });

        Preference dbImportJsonPref = findPreference(getString(R.string.preference_dbimport_json_key));
        dbImportJsonPref.setOnPreferenceClickListener(preference -> {
            // Import database
            Intent jsonFileImportIntent = selectFileForImport("application/json");
            // Start activity
            startActivityForResult(jsonFileImportIntent, SAF_CREATE_JSON_IMPORT_FILE);
            return true;
        });

        Preference dbExportRawPref = findPreference(getString(R.string.preference_dbexport_raw_key));
        dbExportRawPref.setOnPreferenceClickListener(preference -> {
            // Export database
            Intent rawFileIntent = selectFileForExport("fpu_calculator_database", "*/*");
            // Start activity
            startActivityForResult(rawFileIntent, SAF_CREATE_RAW_EXPORT_FILE);
            return true;
        });
    }

    private Intent selectFileForExport(String fileDefaultName, String mimeType) {
        // Activity to select file for export
        Intent fileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // Category to be able to open file
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        // Set mime type
        fileIntent.setType(mimeType);

        // Proposed name of file
        fileIntent.putExtra(Intent.EXTRA_TITLE, fileDefaultName);

        return fileIntent;
    }

    private Intent selectFileForImport(String mimeType) {
        // Activity to select file for import
        Intent fileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Category to be able to open file
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        // Set mime type
        fileIntent.setType("*/*"); // Unfortunately no way to specify *.json in Storage Access Framework :-(

        return fileIntent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == SAF_CREATE_RAW_EXPORT_FILE) {
            // Path to file (as Content Provider URI)
            Uri fileUri = data.getData();
            exportRawDatabase(fileUri);
        } else if (resultCode == RESULT_OK && data != null && requestCode == SAF_CREATE_JSON_EXPORT_FILE) {
            // Path to file (as Content Provider URI)
            Uri fileUri = data.getData();
            exportJsonDatabase(fileUri);
        } else if (resultCode == RESULT_OK && data != null && requestCode == SAF_CREATE_JSON_IMPORT_FILE) {
            // Path to file (as Content Provider URI)
            Uri fileUri = data.getData();
            importJsonDatabase(fileUri);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void exportRawDatabase(final Uri fileUri) {
        // Initialize export service
        Intent exportService = new Intent(getContext(), DatabaseRawExportService.class);

        // Set export file uri to service
        exportService.setData(fileUri);

        // Start service
        getActivity().startService(exportService);
    }

    private void exportJsonDatabase(final Uri fileUri) {
        // Initialize export service
        Intent exportService = new Intent(getContext(), DatabaseJsonExportService.class);

        // Set export file uri to service
        exportService.setData(fileUri);

        // Start service
        getActivity().startService(exportService);
    }

    private void importJsonDatabase(final Uri fileUri) {
        // Initialize import service
        Intent importService = new Intent(getContext(), DatabaseJsonImportService.class);

        // Set import file uri to service
        importService.setData(fileUri);

        // Ask user for replace or append mode
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        // Set title and message
        alert.setTitle(R.string.dbimport_dialog_mode_title);
        alert.setMessage(R.string.dbimport_dialog_mode_message);

        // Set positive button = append
        alert.setPositiveButton(R.string.dbimport_dialog_mode_button_append, (dialog, which) -> {
            // Set append mode to true (means to append the data)
            importService.putExtra(SERVICE_IMPORTJSON, true);

            // Start service
            getActivity().startService(importService);
        });

        // Set negative button = replace
        alert.setNegativeButton(R.string.dbimport_dialog_mode_button_replace, (dialog, which) -> {
            // Set append mode to false (means replace the data)
            importService.putExtra(SERVICE_IMPORTJSON, false);

            // Start service
            getActivity().startService(importService);
        });

        // Show
        alert.create().show();
    }

    private class NonNegativeIntegerChecker implements Preference.OnPreferenceChangeListener {
        private int maxValueUsed;

        NonNegativeIntegerChecker(int maxValueUsed) {
            this.maxValueUsed = maxValueUsed;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String newValueString = (String) newValue;
            if (!Pattern.matches("^[1-9]\\d*$", newValueString)) {
                // This is not a non negative integer number
                Toast.makeText(getContext(), R.string.err_nononnegativeinteger, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (Integer.parseInt(newValueString) < maxValueUsed) {
                // The value is smaller than the actually used maximum value
                Toast.makeText(getContext(), R.string.err_maxvaluelessthanusedvalue, Toast.LENGTH_SHORT).show();
                return false;
            }

            // Everything is fine, all checks done
            return true;
        }
    }
}
