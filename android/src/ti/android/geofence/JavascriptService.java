package ti.android.geofence;

import org.appcelerator.titanium.TiApplication;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class JavascriptService extends JobIntentService
{

	public static final int JOB_ID = 541;
	private static final String TAG = "GeofenceModule_JS";

	public static void enqueueWork(Context context, Intent intent)
	{
		enqueueWork(context, JavascriptService.class, JOB_ID, intent);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate()
	{
		super.onCreate();

		String ANDROID_CHANNEL_ID = "geofence_id_01";

		String appName = getApplicationContext().getApplicationInfo().loadLabel(getPackageManager()).toString();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			Log.d(TAG, "Android O or newer");

			NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID, "Geofence Notifications",
																  NotificationManager.IMPORTANCE_DEFAULT);

			((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

			Notification.Builder builder = new Notification.Builder(this, ANDROID_CHANNEL_ID)
											   .setContentTitle(appName)
											   .setContentText("Processing...")
											   .setAutoCancel(true);

			Notification notification = builder.build();
			startForeground(1, notification);

		} else {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
													 .setContentTitle(appName)
													 .setContentText("Processing...")
													 .setPriority(NotificationCompat.PRIORITY_DEFAULT)
													 .setAutoCancel(true);

			Notification notification = builder.build();
			startForeground(1, notification);
		}
	}

	@Override
	protected void onHandleWork(Intent intent)
	{

		Context context = getApplicationContext();
		Bundle bundle = intent.getExtras();
		String fullServiceName = bundle.getString("service_name", null);
		boolean forceRestart = true;

		if (fullServiceName != null) {
			if (this.isServiceRunning(context, fullServiceName)) {
				if (forceRestart) {
					Log.d(TAG, "Service is already running, we will stop it then restart");
					Intent tempIntent = new Intent();
					tempIntent.setClassName(TiApplication.getInstance().getApplicationContext(), fullServiceName);
					context.stopService(tempIntent);
				} else {
					Log.d(TAG, "Service is already running not need for us to start");
					return;
				}
			}

			Intent serviceIntent = new Intent();
			serviceIntent.setClassName(TiApplication.getInstance().getApplicationContext(), fullServiceName);

			serviceIntent.putExtra("customData", bundle.getString("customData", "[]"));

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				context.startForegroundService(serviceIntent);
			} else {
				context.startService(serviceIntent);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean isServiceRunning(Context context, String serviceName)
	{

		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
			if (serviceName.equals(service.service.getClassName()))
				return true;
		return false;
	}
}
