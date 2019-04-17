package info.rueth.fpucalculator.presentation.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import info.rueth.fpucalculator.R;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_container);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preferences_container, new PreferencesFragment())
                .commit();
    }
}
