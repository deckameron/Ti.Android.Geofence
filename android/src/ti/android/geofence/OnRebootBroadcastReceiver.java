package ti.android.geofence;

import java.util.HashMap;

import org.appcelerator.kroll.common.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OnRebootBroadcastReceiver extends BroadcastReceiver implements OnCompleteListener<Void> {

	static Context _context;

	private static final String LCAT = "GeofenceModule BroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i(LCAT, "onReceive - OnRebootBroadcastReceiver for Geofence : " + intent.getAction());
		
		// put module init code that needs to run when the application is
		// created
		_context = context;
		
		Gson gson = new Gson();
		SharedPreferences prefs = context.getSharedPreferences("fences",Context.MODE_PRIVATE);
		String storedHashMapString = prefs.getString("fencesHashString","oopsDintWork");
		java.lang.reflect.Type type = new TypeToken<HashMap<String, HashMap<String, Object>>>() {}.getType();
		HashMap<String, HashMap<String, Object>> fencesHashMap = gson.fromJson(storedHashMapString, type);
		
		//Or whatever action your receiver accepts
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	    if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
	    	
	    	Log.i(LCAT, "ACTION_BOOT_COMPLETED");
	    	
	        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	            context.registerReceiver(this, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
	        }
	        else{
	        	GeofenceModule.populateGeofenceList(fencesHashMap);
	        	GeofenceModule.startMonitoringFencesFromBroadcastReceiver(this);
	        }
	    }

	    if("android.location.PROVIDERS_CHANGED".equals(intent.getAction())){
	    	
	    	Log.i(LCAT, "PROVIDERS_CHANGED_ACTION");
	    	
	        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	        	try {
	        		context.unregisterReceiver(this);
				} catch (Exception e) {
					Log.e(LCAT, e.toString());
				}
	        	
	        	GeofenceModule.populateGeofenceList(fencesHashMap);
	        	GeofenceModule.startMonitoringFencesFromBroadcastReceiver(this);
	        }
	    }
	    
	    if("android.location.MODE_CHANGED".equals(intent.getAction())){
	    	
	    	Log.i(LCAT, "MODE_CHANGED");
	    	
	        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	        	try {
	        		context.unregisterReceiver(this);
				} catch (Exception e) {
					Log.e(LCAT, e.toString());
				}
	        	
	        	GeofenceModule.populateGeofenceList(fencesHashMap);
	        	GeofenceModule.startMonitoringFencesFromBroadcastReceiver(this);
	        }
	    }
	}

	@Override
	public void onComplete(Task<Void> task) {
		// TODO Auto-generated method stub
		if (task.isSuccessful()) {
			updateGeofencesAdded(!getGeofencesAdded());
			Log.i(LCAT, "Added Geofences Successfully! It should be working...");
		} else {
			// Get the status code for the error and log it using a
			// user-friendly message.
			//String errorMessage = GeofenceErrorMessages.getErrorString( _context, task.getException());
			Log.e(LCAT, task.getException().getMessage());
		}
	}

	/**
	 * Returns true if geofences were added, otherwise false.
	 */
	private boolean getGeofencesAdded() {
		return PreferenceManager.getDefaultSharedPreferences(_context).getBoolean(Constants.GEOFENCES_ADDED_KEY, false);
	}

	/**
	 * Stores whether geofences were added or removed in
	 * {@link SharedPreferences};
	 * 
	 * @param added
	 * Whether geofences were added or removed.
	 */
	private void updateGeofencesAdded(boolean added) {
		Log.i(LCAT, "updateGeofencesAdded()");
		PreferenceManager.getDefaultSharedPreferences(_context).edit().putBoolean(Constants.GEOFENCES_ADDED_KEY, added).apply();
	}
}
