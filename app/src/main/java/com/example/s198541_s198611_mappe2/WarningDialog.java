package com.example.s198541_s198611_mappe2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class WarningDialog extends DialogFragment {
    private DialogClickListener callback;

    public interface DialogClickListener {
        void onYesClick();
        void onCancelClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (DialogClickListener) getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException("The calling class must implement the interface DialogClickListener!");
        }
    }

    public static WarningDialog newInstance(String message) {
        WarningDialog frag = new WarningDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(bundle.getString("message"))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onYesClick();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancelClick();
                    }
                }).create();

        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}