package pesance.airhorndroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.LinkedHashMap;

public class SettingsActivity extends FragmentActivity
{
	public final static String PREFERENCES = "pesance.airhorndroid.PREFERENCES";

	public final static String CHANNEL = "pesance.airhorndroid.CHANNEL";
	public final static String CHANNELS = "pesance.airhorndroid.CHANNELS";
	public final static String EMAIL = "pesance.airhorndroid.EMAIL";
	public final static String PASSWORD = "pesance.airhorndroid.PASSWORD";

	private EditText channelField;
	private EditText mEmailField;
	private EditText mPasswordField;
	private Spinner mChannelSpinner;
	private SharedPreferences mSharedPreferences;
	private Map<String, String> mChannels;
	private ArrayAdapter<CharSequence> mAdapter;
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

		editor.putString(CHANNEL, channelField.getText().toString());
		editor.putString(EMAIL, mEmailField.getText().toString());
		editor.putString(PASSWORD, mPasswordField.getText().toString());
		editor.putString(CHANNELS, mGson.toJson(mChannels));

		editor.commit();
	}

	private void init()
	{
		channelField = (EditText) findViewById(R.id.channelField);
		mEmailField = (EditText) findViewById(R.id.emailField);
		mPasswordField = (EditText) findViewById(R.id.passwordField);
		mChannelSpinner = (Spinner) findViewById(R.id.channelSpinner);
		mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
		mGson = new Gson();
		mChannels = mGson.fromJson(mSharedPreferences.getString(CHANNELS, ""),
				new TypeToken<LinkedHashMap<String, String>>(){}.getType());
		// mAdapter = ArrayAdapter.createFromResource(this, R.array.size, android.R.layout.simple_spinner_item);
		// mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// mChannelSpinner.setAdapter(mAdapter);

		((Button) findViewById(R.id.save)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				saveSettings();
				finish();
			}
		});

		((Button) findViewById(R.id.addChannel)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new ChannelDialogFragment(getResources().getString(R.string.addChannel))
					.show(getFragmentManager(), "ChannelDialogFragment");
			}
		});

		((Button) findViewById(R.id.editChannel)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new ChannelDialogFragment(getResources().getString(R.string.editChannel))
					.show(getFragmentManager(), "ChannelDialogFragment");
			}
		});

		channelField.setText(mSharedPreferences.getString(CHANNEL, ""));
		mEmailField.setText(mSharedPreferences.getString(EMAIL, ""));
		mPasswordField.setText(mSharedPreferences.getString(PASSWORD, ""));
	}

	private void showChannelDialog()
	{
	}
}
