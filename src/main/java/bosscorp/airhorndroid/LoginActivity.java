package bosscorp.airhorndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class LoginActivity extends Activity implements OnClickListener
{
	private EditText mEmailField, mPasswordField;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mEmailField = (EditText) findViewById(R.id.emailField);
		mPasswordField = (EditText) findViewById(R.id.passwordField);

		mEmailField.setText(Settings.getInstance().getEmail());
		mPasswordField.setText(Settings.getInstance().getPassword());

		((Button) findViewById(R.id.login)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.login:
				String email = mEmailField.getText().toString();
				String password = mPasswordField.getText().toString();
				Settings.getInstance().setEmail(email);
				Settings.getInstance().setPassword(password);

				DummyDiscordClient.login(email, password, new DummyDiscordResponseHandler()
				{
					@Override
					public void onSuccess(int statusCode, JSONObject response)
					{
						if(response.has("token"))
						{
							try
							{
								Settings.getInstance().setToken(response.getString("token"));
								Toast.makeText(getApplicationContext(),
									R.string.connectionSuccess,
									Toast.LENGTH_LONG
								).show();
								Intent intent = new Intent(LoginActivity.this, AirhornActivity.class);
								startActivity(intent);
							}
							catch(JSONException e)
							{
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onFailure(int statusCode, JSONObject response)
					{
						Toast.makeText(getApplicationContext(),
							"Error occured during Connection. Status code: " + statusCode,
							Toast.LENGTH_LONG
						).show();
						Log.e("GUEZ", response.toString());
					}
				});
				break;
		}
	}
}
