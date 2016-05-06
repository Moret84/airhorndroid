package bosscorp.airhorndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.LinkedHashMap;

public class Settings
{
	private static Settings mInstance;

	public final static String PREFERENCES = "bosscorp.airhorndroid.PREFERENCES";
	public final static String CURRENT_GUILD = "bosscorp.airhorndroid.CURRENT_GUILD";
	public final static String CURRENT_CHANNEL = "bosscorp.airhorndroid.CURRENT_CHANNEL";
	public final static String EMAIL = "bosscorp.airhorndroid.EMAIL";
	public final static String PASSWORD = "bosscorp.airhorndroid.PASSWORD";
	public final static String TOKEN = "bosscorp.airhorndroid.TOKEN";

	private String mCurrentGuildName, mCurrentChannelName, mEmail, mPassword, mToken;
	private LinkedHashMap<String, String> mGuilds, mChannels;

	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;

	private Settings(Context context)
	{
		mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();
		if(null == mGuilds)
			mGuilds = new LinkedHashMap<String, String>();
		if(null == mChannels)
			mChannels = new LinkedHashMap<String, String>();
		restoreSettings();
	}

	public static Settings getInstance(Context context)
	{
		if(null == mInstance)
		{
			mInstance = new Settings(context);
		}

		return mInstance;
	}

	public static Settings getInstance()
	{
		return mInstance;
	}

	private void restoreSettings()
	{
		mCurrentGuildName = mSharedPreferences.getString(CURRENT_GUILD, "");
		mCurrentChannelName = mSharedPreferences.getString(CURRENT_CHANNEL, "");
		mEmail = mSharedPreferences.getString(EMAIL, "");
		mPassword = mSharedPreferences.getString(PASSWORD, "");
		mToken = mSharedPreferences.getString(TOKEN, "");
	}

	private void saveSetting(String name, String value)
	{
		mEditor.putString(name, value);
		mEditor.commit();
	}

	//Getters

	public LinkedHashMap<String, String> getGuildList()
	{
		return mGuilds;
	}

	public LinkedHashMap<String, String> getChannelList()
	{
		return mChannels;
	}

	public String getCurrentGuildName()
	{
		return mCurrentGuildName;
	}

	public String getCurrentGuildNumber()
	{
		return mGuilds.get(mCurrentGuildName);
	}

	public String getCurrentChannelName()
	{
		return mCurrentGuildName;
	}

	public String getCurrentChannelNumber()
	{
		return mChannels.get(mCurrentChannelName);
	}

	public String getPassword()
	{
		return mPassword;
	}

	public String getEmail()
	{
		return mEmail;
	}

	public String getToken()
	{
		return mToken;
	}

	//Setters

	public void addGuild(String name, String number)
	{
		mGuilds.put(name, number);
	}

	public void addChannel(String name, String number)
	{
		mChannels.put(name, number);
	}

	public void setToken(String token)
	{
		mToken = token;
		saveSetting(TOKEN, mToken);
	}

	public void setCurrentGuild(String guild)
	{
		mCurrentGuildName = guild;
		saveSetting(CURRENT_GUILD, mCurrentGuildName);
	}

	public void setCurrentChannel(String channel)
	{
		mCurrentChannelName = channel;
		saveSetting(CURRENT_CHANNEL, mCurrentChannelName);
	}

	public void setEmail(String email)
	{
		mEmail = email;
		saveSetting(EMAIL, mEmail);
	}

	public void setPassword(String password)
	{
		mPassword = password;
		saveSetting(PASSWORD, mPassword);
	}
}
