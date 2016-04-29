package pesance.airhorndroid;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONObject;
import org.json.JSONException;

public class Pesance
{
	public static final String BASE_URL = "https://discordapp.com/api/";

	private static AsyncHttpClient mClient = new AsyncHttpClient();

	public static void login(String email, String password, JsonHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("password", password);
		mClient.post(BASE_URL + "auth/login", params, responseHandler);
	}

	public static void sendMessage(String token, String channel, String message)
	{
		RequestParams params = new RequestParams();
		params.put("content", message);
		mClient.addHeader("Authorization", token);
		mClient.post(BASE_URL + "channels/" + channel + "/messages", params, new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response)
			{
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String res, Throwable  t)
			{
			}
		});
	}
}
