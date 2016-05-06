package bosscorp.airhorndroid;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.JsonHttpResponseHandler;

public class DummyDiscordClient
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

	public static void getGuilds(String token, JsonHttpResponseHandler responseHandler)
	{
		mClient.addHeader("Authorization", token);
		mClient.get(BASE_URL + "users/@me/guilds", responseHandler);
	}

	public static void getChannels(String token, String guild, JsonHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();
		mClient.addHeader("Authorization", token);
		mClient.get(BASE_URL + "guilds/" + guild + "/channels", params, responseHandler);
	}

	public static void sendMessage(String token, String channel, String message, JsonHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();
		params.put("content", message);
		mClient.addHeader("Authorization", token);
		mClient.post(BASE_URL + "channels/" + channel + "/messages", params, responseHandler);
	}
}
