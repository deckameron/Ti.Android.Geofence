package ti.android.geofence;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;

/**
 * Listener for geofence transition changes.
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition. Creates a notification
 * as the output.
 */
public class GeofenceTransitionsJobIntentService extends JobIntentService {

    private static final int JOB_ID = 573;

    private static final String TAG = "GeofenceTransitionsIS";

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {
    	Log.d(TAG, "enqueueWork...");
        enqueueWork(context, GeofenceTransitionsJobIntentService.class, JOB_ID, intent);
    }

    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleWork(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this, geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }
        
        String eventName = null;
        
        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        
        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || 
        	geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT || 
        	geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            
            // Get the transition details as a String.
            //String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition, triggeringGeofences);
            ArrayList<Object> getGeofenceTransitionArray = getGeofenceTransitionArray(triggeringGeofences);
            GeofenceModule.lastestFiredGeofenceTransitionData = getGeofenceTransitionArray;
            
            // Send notification and log the transition details.
            
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            	eventName = Constants.GEOFENCES_ENTERED;
			}
			if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
				eventName = Constants.GEOFENCES_EXITED;
			}
			if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
				eventName = Constants.GEOFENCES_DWELL;
			}
			
			GeofenceModule.lastestFiredGeofenceTransitionEvent = eventName;
			
			Log.i(TAG, "Firing " + eventName + "...");
            GeofenceModule.fireProxyEvent(eventName, getGeofenceTransitionArray, getApplicationContext());
            
        } else {
            // Log the error.
        	int geofence_transition_invalid_type = -1;
			try {
				geofence_transition_invalid_type = TiRHelper.getResource("string.geofence_transition_invalid_type");
			} catch (ResourceNotFoundException e) {
				Log.e(TAG, "Resourses not found");
				e.printStackTrace();
			}
            Log.e(TAG, getString(geofence_transition_invalid_type, geofenceTransition));
        }
    }

    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geofenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as String.
     */
    @SuppressWarnings("unused")
	private String getGeofenceTransitionDetails( int geofenceTransition, List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<String>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

        return geofenceTransitionString + " : " + triggeringGeofencesIdsString;
    }
    
    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as Array
     */
    private ArrayList<Object> getGeofenceTransitionArray( List<Geofence> triggeringGeofences) {

        // Get the Ids of each geofence that was triggered.
        ArrayList<Object> triggeringGeofencesIdsList = new ArrayList<Object>();
       
        for (Geofence geofence : triggeringGeofences) {
        	Log.d(TAG, geofence.toString());
        	
        	String[] firstStep = geofence.toString().split("transitions:");
            String[] secondStep = firstStep[1].split(" ");
            
            String latitude = secondStep[1].split(",")[0];
            String longitude = secondStep[2].split(",")[0];
        	
            HashMap<String,String> gfs = new HashMap<String,String>();
            
            gfs.put("id", geofence.getRequestId());
            gfs.put("latitude", latitude);
            gfs.put("longitude", longitude);
            
            triggeringGeofencesIdsList.add((Object)gfs);
        }

        return triggeringGeofencesIdsList;
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
    	
    	int geofence_transition_entered = -1;
    	int geofence_transition_exited = -1;
    	int unknown_geofence_transition = -1;
    	
		try {
			geofence_transition_entered = TiRHelper.getResource("string.geofence_transition_entered");
			geofence_transition_exited = TiRHelper.getResource("string.geofence_transition_exited");
			unknown_geofence_transition = TiRHelper.getResource("string.unknown_geofence_transition");
		} catch (ResourceNotFoundException e) {
			Log.e(TAG, "Resourses not found");
			e.printStackTrace();
		}
    	
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(geofence_transition_exited);
            default:
                return getString(unknown_geofence_transition);
        }
    }
}