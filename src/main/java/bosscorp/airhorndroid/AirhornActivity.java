package bosscorp.airhorndroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class AirhornActivity extends ActionBarActivity implements OnClickListener
{
	private String mToken;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		((ImageButton) findViewById(R.id.onetap)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.doubletap)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.tripletap)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.fourtap)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.airhornButton)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		String message = "";

		switch(v.getId())
		{
			case R.id.onetap:
				message = "!airhorn default";
				break;
			case R.id.doubletap:
				message = "!airhorn reverb";
				break;
			case R.id.tripletap:
				message = "!airhorn tripletap";
				break;
			case R.id.fourtap:
				message = "!airhorn fourtap";
				break;
			case R.id.airhornButton:
				message = "!airhorn";
				break;
		}

		DummyDiscordClient.sendMessage(
				Settings.getInstance().getToken(),
				Settings.getInstance().getCurrentGuildNumber(),
				message,
				new JsonHttpResponseHandler()
				{
					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response)
					{
						Toast.makeText(getApplicationContext(),
							"Error occured during Connection. Status code: " + statusCode,
							Toast.LENGTH_LONG
						).show();
					}
				}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.pesance_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.action_settings)
		{
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
