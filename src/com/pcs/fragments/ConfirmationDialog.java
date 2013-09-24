package com.pcs.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.pcs.communicator.R;

public class ConfirmationDialog extends DialogFragment {

	private ConfirmationActions confirmationActions;
	private Object onPositivArgument;

	public interface ConfirmationActions {
		public void onPositiveConfirmation(Object onPositivArgument);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.deleteConfirmation)
				.setPositiveButton(R.string.yesString,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								confirmationActions.onPositiveConfirmation(onPositivArgument);
							}
						}).setNegativeButton(R.string.noString, null);
		return builder.create();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public ConfirmationActions getConfirmationActions() {
		return confirmationActions;
	}

	public void setConfirmationActions(ConfirmationActions confirmationActions) {
		this.confirmationActions = confirmationActions;
	}

	public Object getOnPositivArgument() {
		return onPositivArgument;
	}

	public void setOnPositivArgument(Object onPositivArgument) {
		this.onPositivArgument = onPositivArgument;
	}

}
