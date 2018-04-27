package ti.android.geofence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.appcelerator.kroll.common.Log;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OnRebootBroadcastReceiver extends BroadcastReceiver implements OnCompleteListener<Void> {

	static Context _context;
	private static ArrayList<Geofence> mGeofenceList;
	private static GeofencingClient mGeofencingClient;
	private static PendingIntent mGeofencePendingIntent;

	private static final String LCAT = "GeofenceModule BroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i(LCAT, "onReceive - OnRebootBroadcastReceiver for Geofence : " + intent.getAction());
		
		// put module init code that needs to run when the application is
		// created
		mGeofenceList = new ArrayList<Geofence>();
		mGeofencingClient = LocationServices.getGeofencingClient(context);
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
	        	//populateGeofenceList(fencesHashMap);
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
	            //populateGeofenceList(fencesHashMap);
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
	            //populateGeofenceList(fencesHashMap);
	        }
	    }
	}
	
	/**
	 * This sample hard codes geofence data. A real app might dynamically create
	 * geofences based on the user's location.
	 */
	public void populateGeofenceList( HashMap<String, HashMap<String, Object>> fences) {
		
		Log.i(LCAT, "OnRebootBroadcastReceiver for Geofence - Populating Geofence List :");

		for (Entry<String, HashMap<String, Object>> entry : fences.entrySet()) {
			
			Log.i(LCAT, entry.getKey());
			
			mGeofenceList.add(new Geofence.Builder()
			// Set the request ID of the geofence. This is a string to identify
			// this
			// geofence.
					.setRequestId(entry.getKey())

					// Set the circular region of this geofence.
					.setCircularRegion(
							(Double) entry.getValue().get("latitude"),
							(Double) entry.getValue().get("longitude"),
							((Double) entry.getValue().get("radius")).floatValue())

					// Set the expiration duration of the geofence. This
					// geofence gets automatically
					// removed after this period of time.
					.setExpirationDuration(Geofence.NEVER_EXPIRE)

					// Set the period of time the user neeeds to be inside the
					// fence.
					.setLoiteringDelay(Constants.GEOFENCE_DWELL_TIME_IN_MILLISECONDS)

					// Set the transition types of interest. Alerts are only
					// generated for these
					// transition. We track entry and exit transitions in this
					// sample.
					.setTransitionTypes(((Double)entry.getValue().get("transitions")).intValue())

					// Create the geofence.
					.build());
		} 

		mGeofencingClient.addGeofences(getGeofencingRequest(),getGeofencePendingIntent()).addOnCompleteListener(this);
	}

	/**
	 * Builds and returns a GeofencingRequest. Specifies the list of geofences
	 * to be monitored. Also specifies how the geofence notifications are
	 * initially triggered.
	 */
	private GeofencingRequest getGeofencingRequest() {

		Log.i(LCAT, "getGeofencingRequest..");

		GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

		// The INITIAL_TRIGGER_ENTER flag indicates that geofencing service
		// should trigger a
		// GEOFENCE_TRANSITION_ENTER notification when the geofence is added and
		// if the device
		// is already inside that geofence.
		builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

		// Add the geofences to be monitored by geofencing service.
		builder.addGeofences(mGeofenceList);

		// Return a GeofencingRequest.
		return builder.build();
	}

	/**
	 * Gets a PendingIntent to send with the request to add or remove Geofences.
	 * Location Services issues the Intent inside this PendingIntent whenever a
	 * geofence transition occurs for the current list of geofences.
	 * 
	 * @return A PendingIntent for the IntentService that handles geofence
	 *         transitions.
	 */
	private PendingIntent getGeofencePendingIntent() {
		// Reuse the PendingIntent if we already have it.
		if (mGeofencePendingIntent != null) {
			return mGeofencePendingIntent;
		}

		Log.i(LCAT, "ATTEMPTING TO start service");

		Intent intent = new Intent(_context, OnRebootBroadcastReceiver.class);
		// We use FLAG_UPDATE_CURRENT so that we get the same pending intent
		// back when calling
		// addGeofences() and removeGeofences().
		mGeofencePendingIntent = PendingIntent.getBroadcast(_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		return mGeofencePendingIntent;
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
