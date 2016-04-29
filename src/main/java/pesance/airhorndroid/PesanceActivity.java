package pesance.airhorndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONObject;
import org.json.JSONException;

public class PesanceActivity extends ActionBarActivity
{
	private JsonHttpResponseHandler mResponseHandler;
	private String mToken;
	private SharedPreferences mSharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mSharedPreferences = getSharedPreferences(SettingsActivity.PREFERENCES, MODE_PRIVATE);

		mResponseHandler = new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response)
			{
				try
				{
					mToken = response.getString("token");
					Toast.makeText(PesanceActivity.this, mToken, Toast.LENGTH_LONG).show();
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String res, Throwable  t)
			{
			}
		};

		((Button) findViewById(R.id.login)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				login();
			}
		});

		((Button) findViewById(R.id.onetap)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendMessage("default");
			}
		});
		((Button) findViewById(R.id.doubletap)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendMessage("reverb");
			}
		});
		((Button) findViewById(R.id.tripletap)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendMessage("tripletap");
			}
		});
		((Button) findViewById(R.id.fourtap)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendMessage("fourtap");
			}
		});
	}

	private void sendMessage(String arg)
	{
		Pesance.sendMessage(mToken, mSharedPreferences.getString(SettingsActivity.CHANNEL, ""), "!airhorn " + arg);
	}

	private void login()
	{
		Pesance.login(mSharedPreferences.getString(SettingsActivity.EMAIL, ""),
				mSharedPreferences.getString(SettingsActivity.PASSWORD, ""),
				mResponseHandler);
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
