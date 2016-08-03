package bosscorp.airhorndroid;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IResponseHandler
{
	public void onSuccess(int statusCode, JSONObject response);
	public void onSuccess(int statusCode, JSONArray response);
	public void onFailure(int statusCode, JSONObject response);
}
