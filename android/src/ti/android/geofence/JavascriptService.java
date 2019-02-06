package ti.android.geofence;

import org.appcelerator.titanium.TiApplication;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;
import android.util.Log;

public class JavascriptService extends JobIntentService {

	public static final int JOB_ID = 541;
	private static final String TAG = "JavascriptService";

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, JavascriptService.class, JOB_ID, intent);
    }
	
	@Override
	protected void onHandleWork(Intent intent) {
		
		Context context = getApplicationContext();
		Log.i(TAG, "In Service Listener");
    	Bundle bundle = intent.getExtras();
        String fullServiceName = bundle.getString("service_name", null);
        boolean forceRestart = true;
        
        if(fullServiceName != null){
	        Log.i(TAG, "Full Service Name: " + fullServiceName);
	        if (this.isServiceRunning(context, fullServiceName)) {        	
	        	if(forceRestart){
	        		Log.i(TAG, "Service is already running, we will stop it then restart");
	              	Intent tempIntent = new Intent();
	              	tempIntent.setClassName(TiApplication.getInstance().getApplicationContext(), fullServiceName);              	
	              	context.stopService(tempIntent);
	              	
	              	Log.i(TAG, "Service has been stopped");
	        	}else{
	        		Log.i(TAG, "Service is already running not need for us to start");
	        		return;
	        	}
	        }
	 
	      	Intent serviceIntent = new Intent();
	      	serviceIntent.setClassName(TiApplication.getInstance().getApplicationContext(), fullServiceName);
	       	
	        serviceIntent.putExtra("customData", bundle.getString("customData","[]"));
	        
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
	        	context.startForegroundService(serviceIntent); 
	        } else {
	        	context.startService(serviceIntent); 
	        }
	        
	        Log.i(TAG, "Service Started");
        }
	}
	
	@SuppressWarnings("deprecation")
	private boolean isServiceRunning(Context context, String serviceName) {
		 
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) 
			if (serviceName.equals(service.service.getClassName()))
				return true;            
		return false;
	}
}
