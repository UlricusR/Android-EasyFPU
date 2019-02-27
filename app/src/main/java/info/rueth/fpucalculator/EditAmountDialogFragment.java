package info.rueth.fpucalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class EditAmountDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.edit_amount_message);

        // Set the message
        builder.setMessage(R.string.edit_amount_message);

        // Get the layout
        builder.setView(R.layout.edit_amount);

        // Add buttons
        builder.setPositiveButton(R.string.confirm_amount_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setNegativeButton(R.string.cancel_amount_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // Create alert dialog object and return it
        return builder.create();
    }
}
