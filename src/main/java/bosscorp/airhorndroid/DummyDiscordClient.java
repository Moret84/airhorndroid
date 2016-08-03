package bosscorp.airhorndroid;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DummyDiscordClient
{
	public static final String BASE_URL = "https://discordapp.com/api/";

	private static AsyncHttpClient mClient = new AsyncHttpClient();

	private static HttpEntity createJSonEntity(JSONParam<String, String>... params)
	{
		JSONObject jsonObject = new JSONObject();
		HttpEntity entity = null;

		try
		{
			for(JSONParam<String, String> param : params)
				jsonObject.put(param.key(), param.value());
			entity = new StringEntity(jsonObject.toString());
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return entity;
	}
	private static JsonHttpResponseHandler createJsonHttpResponseHandler(final IResponseHandler responseHandler)
	{
		JsonHttpResponseHandler jSonHttpResponseHandler = new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response)
			{
				responseHandler.onSuccess(statusCode, response);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response)
			{
				responseHandler.onSuccess(statusCode, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response)
			{
				responseHandler.onFailure(statusCode, response);
			}
		};

		return jSonHttpResponseHandler;
	}

	public static void login(String email, String password, IResponseHandler responseHandler)
	{
		HttpEntity entity = createJSonEntity(
				new JSONParam<String, String>("email", email),
				new JSONParam<String, String>("password", password)
		);

		mClient.post(null, BASE_URL + "auth/login", entity, "application/json", createJsonHttpResponseHandler(responseHandler));
	}

	public static void getGuilds(String token, IResponseHandler responseHandler)
	{
		mClient.addHeader("Authorization", token);
		mClient.get(BASE_URL + "users/@me/guilds", createJsonHttpResponseHandler(responseHandler));
	}

	public static void getChannels(String token, String guild, IResponseHandler responseHandler)
	{
		mClient.addHeader("Authorization", token);
		mClient.get(BASE_URL + "guilds/" + guild + "/channels", createJsonHttpResponseHandler(responseHandler));
	}

	public static void sendMessage(String token, String channel, String message, final IResponseHandler responseHandler)
	{
		mClient.addHeader("Authorization", token);

		HttpEntity entity = createJSonEntity(
				new JSONParam<String, String>("content", message)
		);

		mClient.post(
				null,
				BASE_URL + "channels/" + channel + "/messages",
				entity,
				"application/json",
				createJsonHttpResponseHandler(responseHandler)
		);
	}
}
