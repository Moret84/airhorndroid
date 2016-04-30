package bosscorp.airhorndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.lang.ClassCastException;

public class ChannelDialogFragment extends DialogFragment
{
	private String mTitle, mChannelName, mChannelKey;
	private EditText mChannelNameField, mChannelKeyField;
	private ChannelDialogListener mChannelDialogListener;

	public ChannelDialogFragment(String title)
	{
		mTitle = title;
	}

	public ChannelDialogFragment(String title, String channelName, String channelKey)
	{
		mTitle = title;
		mChannelName = channelName;
		mChannelKey = channelKey;
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

		try
		{
			mChannelDialogListener = (ChannelDialogListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement ChannelDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.dialog, null);
		mChannelNameField = ((EditText) view.findViewById(R.id.channelName));
		mChannelKeyField = ((EditText) view.findViewById(R.id.channelKey));
		builder.setTitle(mTitle).setView(view)

			//Cancel is set to positiveButton to reverse order of them.
			.setNegativeButton(R.string.add, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							mChannelDialogListener.onDialogPositiveClick(
									mChannelNameField.getText().toString(),
									mChannelKeyField.getText().toString()
							);
						}
					})
		.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dismiss();
					}
				});

		mChannelNameField.setText(mChannelName);
		mChannelKeyField.setText(mChannelKey);

		return builder.create();
	}
}
