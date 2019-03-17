package info.rueth.fpucalculator.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.domain.repository.AbsorptionSchemeRepository;
import info.rueth.fpucalculator.presentation.adapter.AbsorptionSchemeAdapter;

public class AbsorptionSchemeActivity extends AppCompatActivity {

    private AbsorptionSchemeAdapter adapter;

    private Button buttonAdd;
    private Button buttonReset;
    private FloatingActionButton fabDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editabsorptionscheme);

        // Create recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_absorptionblock);
        adapter = new AbsorptionSchemeAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach data observer
        try {
            AbsorptionSchemeRepository.getInstance(getApplication()).getAbsorptionBlockViewModel().observe(this, adapter::setAbsorptionBlocks);
        } catch (IOException e) {
            Toast.makeText(this, R.string.err_absorptionschemefilenotfound, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Add and reset buttons
        buttonAdd = findViewById(R.id.button_absorptionblock_add);
        buttonReset = findViewById(R.id.button_absorptionscheme_reset);

        // Floating action button if done editing
        fabDone = findViewById(R.id.fab_done);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set onClickListener to add button
        buttonAdd.setOnClickListener(v -> {
            // TODO
        });

        // Set onClickListener to reset button
        buttonReset.setOnClickListener(v -> {
            try {
                AbsorptionSchemeRepository.getInstance(getApplicationContext()).reset().observe(this, adapter::setAbsorptionBlocks);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), R.string.err_absorptionschemefilenotfound, Toast.LENGTH_SHORT).show();
            }
        });

        // Set onClickListener to fabDone
        fabDone.setOnClickListener(view -> {
            Intent intent = new Intent(AbsorptionSchemeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // De-register buttons
        buttonAdd.setOnClickListener(null);
        buttonReset.setOnClickListener(null);
        fabDone.setOnClickListener(null);
    }
}
