package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import androidx.appcompat.app.AppCompatActivity;
import info.rueth.fpucalculator.R;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preferences_container, new PreferencesFragment())
                .commit();
    }
}
