package bosscorp.airhorndroid;

import android.app.Application;

public class Airhorndroid extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Settings.getInstance(getApplicationContext());
	}
}
