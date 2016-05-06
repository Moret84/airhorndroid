package bosscorp.airhorndroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;

public class AirhornActivity extends ActionBarActivity implements OnClickListener
{
	private String mToken;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		((Button) findViewById(R.id.onetap)).setOnClickListener(this);
		((Button) findViewById(R.id.doubletap)).setOnClickListener(this);
		((Button) findViewById(R.id.tripletap)).setOnClickListener(this);
		((Button) findViewById(R.id.fourtap)).setOnClickListener(this);
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
		}

		DummyDiscordClient.sendMessage(
				Settings.getInstance().getToken(),
				Settings.getInstance().getCurrentChannelNumber(),
				message,
				new JsonHttpResponseHandler()
				{
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
