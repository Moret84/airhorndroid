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
	public final static String PREFERENCES = "pesance.airhorndroid.PREFERENCES";

	public final static String CURRENT_CHANNEL_POSITION = "pesance.airhorndroid.CURRENT_CHANNEL_POSITION";
	public final static String CURRENT_CHANNEL = "pesance.airhorndroid.CURRENT_CHANNEL";
	public final static String CHANNELS = "pesance.airhorndroid.CHANNELS";
	public final static String EMAIL = "pesance.airhorndroid.EMAIL";
	public final static String PASSWORD = "pesance.airhorndroid.PASSWORD";

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

		editor.putString(EMAIL, mEmailField.getText().toString());
		editor.putString(PASSWORD, mPasswordField.getText().toString());
		editor.putString(CHANNELS, mGson.toJson(mChannels));
		editor.putString(CURRENT_CHANNEL, mChannels.get(mCurrentChannel));

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
		mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		mGson = new Gson();
		mChannels = mGson.fromJson(mSharedPreferences.getString(CHANNELS, ""),
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

		mEmailField.setText(mSharedPreferences.getString(EMAIL, ""));
		mPasswordField.setText(mSharedPreferences.getString(PASSWORD, ""));
		mChannelSpinner.setSelection(mCurrentChannelPosition);
	}
}
