package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import info.rueth.fpucalculator.R;

public class PreferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
