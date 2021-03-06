package bosscorp.airhorndroid;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import android.util.Log;

import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class SettingsActivity extends FragmentActivity implements OnClickListener
{
	private Spinner mGuildSpinner;
	private LinkedHashMapAdapter mGuildAdapter;
	private Switch mKeepAwakeSwitch;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		init();
		populateGuildSpinner();
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.ok:
				Settings.getInstance().setCurrentGuild(
						((Entry<String, String>) mGuildSpinner.getSelectedItem()).getKey()
				);
				Settings.getInstance().setKeepAwake(mKeepAwakeSwitch.isChecked());
				finish();
				break;
		}
	}

	private void restoreCurrentGuild()
	{
		String currentGuild = Settings.getInstance().getCurrentGuildName();

		if(null == currentGuild || currentGuild.equals(""))
			mGuildSpinner.setSelection(0);
		else
			mGuildSpinner.setSelection(mGuildAdapter.getPosition(currentGuild));
	}

	private void populateGuildSpinner()
	{
		DummyDiscordClient.getGuilds(Settings.getInstance().getToken(),
			new DummyDiscordResponseHandler()
			{
				@Override
				public void onSuccess(int statusCode, JSONArray response)
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
					restoreCurrentGuild();
				}

				@Override
				public void onFailure(int statusCode, JSONObject response)
				{
					Toast.makeText(getApplicationContext(),
						"Couldn't get guilds. Error code " + statusCode,
						Toast.LENGTH_LONG).show();
				}
			}
		);
	}

	private void init()
	{
		((Button) findViewById(R.id.ok)).setOnClickListener(this);
		mKeepAwakeSwitch = ((Switch) findViewById(R.id.keepAwake));
		mKeepAwakeSwitch.setChecked(Settings.getInstance().getKeepAwake());
		mGuildSpinner = (Spinner) findViewById(R.id.guildSpinner);

		mGuildAdapter = new LinkedHashMapAdapter<String, String>(this,
				android.R.layout.simple_spinner_item,
				Settings.getInstance().getGuildList()
		);
		mGuildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGuildSpinner.setAdapter(mGuildAdapter);
	}
}
