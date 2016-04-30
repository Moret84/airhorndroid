package bosscorp.airhorndroid;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map.Entry;
import java.util.LinkedHashMap;

public class SettingsActivity extends FragmentActivity implements ChannelDialogListener, OnItemSelectedListener, OnClickListener
{

	private EditText mEmailField, mPasswordField;
	private String mCurrentChannel;
	private int mCurrentChannelPosition;
	private Spinner mChannelSpinner;
	private SharedPreferences mSharedPreferences;
	private LinkedHashMap<String, String> mChannels;
	private LinkedHashMapAdapter mAdapter;
	private Gson mGson;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		init();
	}

	private void saveSettings()
	{
		Editor editor = mSharedPreferences.edit();

		editor.putString(Settings.EMAIL, mEmailField.getText().toString());
		editor.putString(Settings.PASSWORD, mPasswordField.getText().toString());
		editor.putString(Settings.CHANNELS, mGson.toJson(mChannels));
		editor.putString(Settings.CURRENT_CHANNEL, mChannels.get(mCurrentChannel));

		editor.commit();
	}

	@Override
	public void onDialogPositiveClick(String name, String key)
	{
		mChannels.put(name, key);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
	{
		mChannelSpinner.setSelection(pos);
		mCurrentChannel = ((Entry<String, String>) parent.getSelectedItem()).getKey();
		mCurrentChannelPosition = pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.save:
				saveSettings();
				finish();
				break;
			case R.id.addChannel:
				new ChannelDialogFragment(getResources().getString(R.string.addChannel))
					.show(getFragmentManager(), "ChannelDialogFragment");
				break;
			case R.id.editChannel:
				new ChannelDialogFragment(getResources().getString(R.string.editChannel),
						mCurrentChannel,
						mChannels.get(mCurrentChannel)
					)
					.show(getFragmentManager(), "ChannelDialogFragment");
				break;
			case R.id.deleteChannel:
				mChannels.remove(mCurrentChannel);
				mAdapter.notifyDataSetChanged();
				break;
		}
	}

	private void init()
	{
		mEmailField = (EditText) findViewById(R.id.emailField);
		mPasswordField = (EditText) findViewById(R.id.passwordField);
		mChannelSpinner = (Spinner) findViewById(R.id.channelSpinner);
		mChannelSpinner.setOnItemSelectedListener(this);
		mSharedPreferences = getSharedPreferences(Settings.PREFERENCES, MODE_PRIVATE);
		mGson = new Gson();
		mChannels = mGson.fromJson(mSharedPreferences.getString(Settings.CHANNELS, ""),
				new TypeToken<LinkedHashMap<String, String>>(){}.getType());

		if(null == mChannels)
			mChannels = new LinkedHashMap<String, String>();
		mAdapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_spinner_item, mChannels);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mChannelSpinner.setAdapter(mAdapter);

		((Button) findViewById(R.id.save)).setOnClickListener(this);
		((Button) findViewById(R.id.addChannel)).setOnClickListener(this);
		((Button) findViewById(R.id.editChannel)).setOnClickListener(this);
		((Button) findViewById(R.id.deleteChannel)).setOnClickListener(this);

		mEmailField.setText(mSharedPreferences.getString(Settings.EMAIL, ""));
		mPasswordField.setText(mSharedPreferences.getString(Settings.PASSWORD, ""));
		mChannelSpinner.setSelection(mCurrentChannelPosition);
		mCurrentChannel = mSharedPreferences.getString(Settings.CURRENT_CHANNEL, "");
	}
}
