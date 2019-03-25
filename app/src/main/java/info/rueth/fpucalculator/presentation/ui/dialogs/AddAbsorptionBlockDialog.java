package info.rueth.fpucalculator.presentation.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import info.rueth.fpucalculator.R;
import info.rueth.fpucalculator.presentation.viewmodels.AbsorptionBlockViewModel;

public class AddAbsorptionBlockDialog extends AppCompatDialogFragment {

    Spinner maxFPUSpinner;
    Spinner absorptionTimeSpinner;
    public static final String FPU_KEY = "info.rueth.fpucalculator.key.FPU";
    public static final String ABSORPTIONTIME_KEY = "info.rueth.fpucalculator.key.ABSORPTIONTIME";
    ArrayList<Integer> fpuValues;
    ArrayList<Integer> absorptionTimeValues;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        // Read FPU and absorption time values
        fpuValues = args.getIntegerArrayList(FPU_KEY);
        absorptionTimeValues = args.getIntegerArrayList(ABSORPTIONTIME_KEY);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Save FPU and absorption time values in case screen is rotated
        outState.putIntegerArrayList(FPU_KEY, fpuValues);
        outState.putIntegerArrayList(ABSORPTIONTIME_KEY, absorptionTimeValues);

        super.onSaveInstanceState(outState);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Re-create FPU and absorption time values in case screen has been rotated
        if (savedInstanceState != null && savedInstanceState.containsKey(FPU_KEY)) {
            fpuValues = savedInstanceState.getIntegerArrayList(FPU_KEY);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(ABSORPTIONTIME_KEY)) {
            absorptionTimeValues = savedInstanceState.getIntegerArrayList(ABSORPTIONTIME_KEY);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_newabsorptionblock, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because it's going in the dialog layout
        builder.setView(view)
                .setTitle(R.string.dialog_title_newabsorptionblock)
                .setMessage(R.string.dialog_message_newabsorptionblock)
                .setNegativeButton(R.string.button_cancel, (dialog, which) -> {
                    // Dialog schließen
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.button_add, (dialog, which) -> {
                    // Create new absorption block
                    int maxFPU = Integer.parseInt(maxFPUSpinner.getSelectedItem().toString());
                    int absorptionTime = Integer.parseInt(absorptionTimeSpinner.getSelectedItem().toString());
                    AbsorptionBlockViewModel absorptionBlock = new AbsorptionBlockViewModel(maxFPU, absorptionTime);

                    // Call add function in activity
                    if (getActivity() instanceof IAddAbsorptionBlockListener) {
                        ((IAddAbsorptionBlockListener) getActivity()).addAbsorptionBlock(absorptionBlock);
                    }

                    // Dialog schließen
                    dialog.dismiss();
                });

        Dialog dialog = builder.create();

        // Get the spinners
        maxFPUSpinner = view.findViewById(R.id.absorptionblock_fpu_spinner);
        absorptionTimeSpinner = view.findViewById(R.id.absorptionblock_absorptiontime_spinner);

        // Set the values via spinner adapters
        ArrayAdapter<Integer> fpuAdapter = new ArrayAdapter<>(inflater.getContext(), R.layout.support_simple_spinner_dropdown_item, fpuValues);
        ArrayAdapter<Integer> absorptionTimeAdapter = new ArrayAdapter<>(inflater.getContext(), R.layout.support_simple_spinner_dropdown_item, absorptionTimeValues);
        maxFPUSpinner.setAdapter(fpuAdapter);
        absorptionTimeSpinner.setAdapter(absorptionTimeAdapter);

        // Return dialog
        return dialog;
    }
}
