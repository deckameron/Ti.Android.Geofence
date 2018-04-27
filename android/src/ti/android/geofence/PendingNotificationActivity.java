package ti.android.geofence;

import java.util.HashMap;

import org.appcelerator.kroll.KrollRuntime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PendingNotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null && !extras.isEmpty() && extras.containsKey(GeofenceModule.PROPERTY_EXTRAS)) {
			extras = extras.getBundle(GeofenceModule.PROPERTY_EXTRAS);

			if (GeofenceModule.hasCallbackListeners() && KrollRuntime.getInstance().getRuntimeState() != KrollRuntime.State.DISPOSED) {
				// Fire notification received delegate
				HashMap<String, Object> data = GeofenceModule.convertBundleToHashMap(extras);
				data.put("prev_state", "background");
				GeofenceModule.sendMessage(data, GeofenceModule.NOTIFICATION_CLICKED);
				intent.removeExtra(GeofenceModule.PROPERTY_EXTRAS);

				// Put app in foreground
				Intent launcherIntent = GeofenceModule.getLauncherIntent(null);
				startActivity(launcherIntent);
			} else {
				// Start app from main activity with extras
				Intent launcherIntent = GeofenceModule.getLauncherIntent(extras);
				startActivity(launcherIntent);
			}
		}

		finish();
	}
}