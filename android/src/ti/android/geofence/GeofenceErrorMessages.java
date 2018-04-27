package ti.android.geofence;

import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.GeofenceStatusCodes;

/**
 * Geofence error codes mapped to error messages.
 */
class GeofenceErrorMessages {
	
	private static final String TAG = "Ti.Android.Geofence";
	static int geofence_not_available = -1;
	static int geofence_too_many_geofences = -1;
	static int geofence_too_many_pending_intents = -1;
	static int unknown_geofence_error = -1;
	
    /**
     * Prevents instantiation.
     */
    private GeofenceErrorMessages() {}
    /**
     * Returns the error string for a geofencing exception.
     */
    public static String getErrorString(Context context, Exception e) {
        if (e instanceof ApiException) {
            return getErrorString(context, ((ApiException) e).getStatusCode());
        } else {
        	prepareResoursesStrings();
            return context.getResources().getString(unknown_geofence_error);
        }
    }

    /**
     * Returns the error string for a geofencing error code.
     */
    public static String getErrorString(Context context, int errorCode) {
    	
    	prepareResoursesStrings();
    	
        Resources mResources = context.getResources();
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return mResources.getString(geofence_not_available);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return mResources.getString(geofence_too_many_geofences);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return mResources.getString(geofence_too_many_pending_intents);
            default:
                return mResources.getString(unknown_geofence_error);
        }
    }
    
    public static void prepareResoursesStrings(){
    	try {
			geofence_not_available = TiRHelper.getResource("string.geofence_not_available");
			geofence_too_many_geofences = TiRHelper.getResource("string.geofence_too_many_geofences");
	    	geofence_too_many_pending_intents = TiRHelper.getResource("string.geofence_too_many_pending_intents");
	    	unknown_geofence_error = TiRHelper.getResource("string.unknown_geofence_error");
		} catch (ResourceNotFoundException e) {
			Log.e(TAG, "Resourses not found");
			e.printStackTrace();
		}
    }
}