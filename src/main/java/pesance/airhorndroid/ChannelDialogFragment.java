package pesance.airhorndroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class ChannelDialogFragment extends DialogFragment
{
	private String mTitle;

	public ChannelDialogFragment(String title)
	{
		mTitle = title;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setTitle(mTitle).setView(R.layout.dialog)

		//Cancel is set to positiveButton to reverse order of them.
		.setNegativeButton(R.string.add, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				dismiss();
			}
		})
		.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
			}
		});

		return builder.create();
	}
}
