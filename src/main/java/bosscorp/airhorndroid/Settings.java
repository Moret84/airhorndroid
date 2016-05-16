package bosscorp.airhorndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.LinkedHashMap;

public class Settings
{
	private static Settings mInstance;

	public final static String PREFERENCES = "bosscorp.airhorndroid.PREFERENCES";
	public final static String CURRENT_GUILD_NAME = "bosscorp.airhorndroid.CURRENT_GUILD_NAME";
	public final static String CURRENT_GUILD_NUMBER = "bosscorp.airhorndroid.CURRENT_GUILD_NUMBER";
	public final static String EMAIL = "bosscorp.airhorndroid.EMAIL";
	public final static String PASSWORD = "bosscorp.airhorndroid.PASSWORD";
	public final static String TOKEN = "bosscorp.airhorndroid.TOKEN";
	public final static String KEEP_AWAKE = "bosscorp.airhorndroid.KEEP_AWAKE";

	private String mCurrentGuildName, mCurrentGuildNumber, mEmail, mPassword, mToken;
	private boolean mKeepAwake;
	private LinkedHashMap<String, String> mGuilds;

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
		mCurrentGuildName = mSharedPreferences.getString(CURRENT_GUILD_NAME, "");
		mCurrentGuildNumber = mSharedPreferences.getString(CURRENT_GUILD_NUMBER, "");
		mEmail = mSharedPreferences.getString(EMAIL, "");
		mPassword = mSharedPreferences.getString(PASSWORD, "");
		mToken = mSharedPreferences.getString(TOKEN, "");
		mKeepAwake = mSharedPreferences.getBoolean(KEEP_AWAKE, false);
	}

	private void saveSetting(String name, String value)
	{
		mEditor.putString(name, value);
		mEditor.commit();
	}

	private void saveSetting(String name, boolean value)
	{
		mEditor.putBoolean(name, value);
		mEditor.commit();
	}

	//Getters

	public LinkedHashMap<String, String> getGuildList()
	{
		return mGuilds;
	}

	public String getCurrentGuildName()
	{
		return mCurrentGuildName;
	}

	public String getCurrentGuildNumber()
	{
		return mCurrentGuildNumber;
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

	public boolean getKeepAwake()
	{
		return mKeepAwake;
	}

	//Setters
	public void addGuild(String name, String number)
	{
		mGuilds.put(name, number);
	}

	public void setToken(String token)
	{
		mToken = token;
		saveSetting(TOKEN, mToken);
	}

	public void setCurrentGuild(String guild)
	{
		mCurrentGuildName = guild;
		mCurrentGuildNumber = mGuilds.get(guild);
		saveSetting(CURRENT_GUILD_NAME, mCurrentGuildName);
		saveSetting(CURRENT_GUILD_NUMBER, mCurrentGuildNumber);
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

	public void setKeepAwake(boolean value)
	{
		mKeepAwake = value;
		saveSetting(KEEP_AWAKE, mKeepAwake);
	}
}
