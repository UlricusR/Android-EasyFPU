package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.regex.Pattern;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;

public class PreferencesFragment extends PreferenceFragmentCompat {
    private static final String LOG_TAG = "PreferencesFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

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
