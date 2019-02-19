package ti.android.geofence;

final class Constants {

	private Constants() {
	}

	private static final String PACKAGE_NAME = "ti.android.geofence";

	static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

	static final String GEOFENCES_ENTERED = "ENTERED";
	static final String GEOFENCES_EXITED = "EXITED";
	static final String GEOFENCES_DWELL = "DWELL";
	static final String GEOFENCES_ERROR = "ERROR";
	static final String STARTED_MONITORING = "STARTED_MONITORING";
	static final String STOPPED_MONITORING = "STOPPED_MONITORING";
	static final String GEOFENCES_ADDED = "GEOFENCES_ADDED";
	static final String NOTIFICATION_CLICKED = "NOTIFICATION_CLICKED";
	static final String NOTIFICATION_FIRED_IN_FOREGROUND = "NOTIFICATION_FIRED_IN_FOREGROUND";
	static final String NOTIFICATION_FIRED_IN_BACKGROUND = "NOTIFICATION_FIRED_IN_BACKGROUND";
	static final String TYPE_MASTER_ZONE = "1";
	static final String TYPE_PLACE_FENCE = "2";

	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

	private static final int GEOFENCE_DWELL_TIME_IN_MINUTES = 5;
	static final int GEOFENCE_DWELL_TIME_IN_MILLISECONDS = GEOFENCE_DWELL_TIME_IN_MINUTES * 60 * 1000;
}