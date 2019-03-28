package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import info.rueth.fpucalculator.R;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
