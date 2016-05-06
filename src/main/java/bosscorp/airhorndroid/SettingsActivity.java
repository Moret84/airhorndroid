package bosscorp.airhorndroid;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class SettingsActivity extends FragmentActivity implements OnItemSelectedListener, OnClickListener
{
	private Spinner mGuildSpinner, mChannelSpinner;
	private LinkedHashMapAdapter mGuildAdapter, mChannelAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		init();
		populateGuildSpinner();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
	{
		switch(parent.getId())
		{
			case R.id.guildSpinner:
				Settings.getInstance().setCurrentGuild(
						((Entry<String, String>) parent.getSelectedItem()).getKey()
				);
				populateChannelSpinner();
				break;
			case R.id.channelSpinner:
				Settings.getInstance().setCurrentChannel(
						((Entry<String, String>) parent.getSelectedItem()).getKey()
				);
				break;
		}
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
			case R.id.ok:
				finish();
				break;
		}
	}

	private void populateGuildSpinner()
	{
		DummyDiscordClient.getGuilds(Settings.getInstance().getToken(),
			new JsonHttpResponseHandler()
			{
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONArray response)
				{
					for(int i=0; i < response.length(); ++i)
					{
						try
						{
							JSONObject current = response.getJSONObject(i);
							Settings.getInstance().addGuild(current.getString("name"), current.getString("id"));
						}
						catch(JSONException e)
						{
							e.printStackTrace();
						}
					}

					mGuildAdapter.notifyDataSetChanged();
					mGuildSpinner.setSelection(mGuildAdapter.getPosition(Settings.getInstance().getCurrentGuildName()));
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response)
				{
					Toast.makeText(getApplicationContext(),
						"Couldn't get guilds. Error code " + statusCode,
						Toast.LENGTH_LONG).show();
				}
			}
		);
	}

	private void populateChannelSpinner()
	{
		Settings.getInstance().getChannelList().clear();
		DummyDiscordClient.getChannels(Settings.getInstance().getToken(),
			Settings.getInstance().getCurrentGuildNumber(),
			new JsonHttpResponseHandler()
			{
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONArray response)
				{
					for(int i=0; i < response.length(); ++i)
					{
						try
						{
							JSONObject current = response.getJSONObject(i);
							if(current.getString("type").equals("text"))
								Settings.getInstance().addChannel(current.getString("name"), current.getString("id"));
						}
						catch(JSONException e)
						{
							e.printStackTrace();
						}
					}

					mChannelAdapter.notifyDataSetChanged();
					mChannelSpinner.setSelection(mChannelAdapter.getPosition(Settings.getInstance().getCurrentChannelName()));
				}
			}
		);
	}

	private void init()
	{
		((Button) findViewById(R.id.ok)).setOnClickListener(this);
		//Setting up Spinner
		mGuildSpinner = (Spinner) findViewById(R.id.guildSpinner);
		mChannelSpinner = (Spinner) findViewById(R.id.channelSpinner);

		mGuildSpinner.setOnItemSelectedListener(this);
		mChannelSpinner.setOnItemSelectedListener(this);

		mGuildAdapter = new LinkedHashMapAdapter<String, String>(this,
				android.R.layout.simple_spinner_item,
				Settings.getInstance().getGuildList()
		);
		mGuildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGuildSpinner.setAdapter(mGuildAdapter);

		mChannelAdapter = new LinkedHashMapAdapter<String, String>(this,
				android.R.layout.simple_spinner_item,
				Settings.getInstance().getChannelList()
		);
		mChannelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mChannelSpinner.setAdapter(mChannelAdapter);
	}
}
