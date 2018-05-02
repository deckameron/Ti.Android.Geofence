# Methods

All your files are listed in the file explorer. You can switch from one to another by clicking a file in the list.

|Methods                |Description                          |
|----------------|-------------------------------|
|addGeofences			|Passes the fences to the module                       
|startMonitoring    	|Start monitoring the fences for the chosen transitions                   
|clearExistingFences    |Clear all fences and stops monitoring
|stopMonitoring         |Just stops monitoring without clearing the fences


## addGeofences

This method will add all fences you need to the module. Remember that Google Geofence can monitor only 100 fences at a time.

|Attributes             |Description                          |
|----------------|-------------------------------|
|clearExistingFences			|_(Boolean)_ - clear all existing fences before adding new ones                   
|fences    	|_(Array)_  - List of fences objects

### Fences object

You can delete the current file by clicking the **Remove** button in the file explorer. The file will be moved into the **Trash** folder and automatically deleted after 7 days of inactivity.

|Attributes             |Description                          |
|----------------|-------------------------------|
|id					|_(Boolean)_ - clear all existing fences before adding new ones                   
|type    			|_(String)_  - 
|latitude    		|_(Number)_  - latitude of fence center
|longitude    		|_(Number)_  - longitude of fence center
|radius    			|_(Number)_  - fence radius
|dwellTime    		|_(Number)_  - the amount of time the user has to remain inside the fence. The time is MILLISECONDS.
|transitions    	|_(Array)_  - You can monitor different types of transition for different fences. You can monitor all of them at once or only the ones you need. The possibilities are : _geofence.GEOFENCE_TRANSITION_ENTER, geofence.GEOFENCE_TRANSITION_EXIT and geofence.GEOFENCE_TRANSITION_DWELL_
|title    			|_(String)_  - **Notification** - The notification's title
|message    			|_(String)_  - **Notification** - The notification's text content (excluding the title)
|ledColor    		|_(String)_  - **Notification** - Certain Android hardware devices have LED notification lights built-in, allowing notifications to trigger a colored notification light on a device upon receipt. Notification colors are set using ARGB Hex values (Ex: #FFD50000). [Doc here](https://documentation.onesignal.com/docs/android-customizations#section-led-color)
|accentColor    	|_(String)_  - **Notification** - Android Accent Color. Colors are set using ARGB Hex values (Ex: #FFD50000) [Doc here](https://documentation.onesignal.com/docs/android-customizations#section-accent-color)
|smallIcon    		|_(String)_  - **Notification** - Small Icon. It has to be a local image ("@drawable/large_icon").  [Doc here](https://documentation.onesignal.com/docs/customize-notification-icons) 
|largeIcon    		|_(String)_  - **Notification** - Large Icon. Can either be a local image ("@drawable/large_icon") or url ("https://image/large_icon.png") [Doc here]
|bigImage    		|_(String)_  - **Notification** - big image url. [Doc here](https://documentation.onesignal.com/docs/android-customizations#section-big-picture)(https://documentation.onesignal.com/docs/customize-notification-icons)
|showGooglePlaceBigImage  |_(Boolean)_  - **Notification** - If you want to display a big image on the notification but do not have one. This will show a picture of the place taken from Google Places API.

### Transitions

The transition type indicating that the user enters and dwells in geofences for a given period of time. If  _GEOFENCE_TRANSITION_ENTER_ is also specified, this alert will always be sent after the _GEOFENCE_TRANSITION_ENTER_ alert.

|Transitions                |Integer Values                          |
|----------------|-------------------------------|
|_GEOFENCE_TRANSITION_ENTER_	|  	1                    
|_GEOFENCE_TRANSITION_EXIT_    	| 	2
|_GEOFENCE_TRANSITION_DWELL_    |	4

## startMonitoring

This method will check your geofences and start monitoring for the transitions you want. Once you use this method, the fences will be monitored until told otherwise. Your fences will be re-added whenever the user restarts it phone or change the location provider.

## clearExistingFences

This method will clear all existing fences already being monitored. It will also stop the monitoring service.

## stopMonitoring

This method will just stop the monitoring service for the current fences. It will NOT clear the existing fences already being monitored.

# Events

These events can only be monitored when your app is in foreground or in background. They will never fire when you app you closed because the instance of your application does not exist.

|Events                |Description                          |
|----------------|-------------------------------|
|_ENTERED_				|   When user enters a geofence           
|_EXIT_    				| 	When user exits a geofence
|_DWELL_    			|	When user remains inside a geofence for the specified period of time
|_STARTED_MONITORING_	|  	When the Android starts monitoring for your fences  
|_STOPPED_MONITORING_   | 	When the Android stops monitoring for your fences  
|_GEOFENCES_ADDED_    	|	When the fences get added to the module
|_NOTIFICATION_CLICKED_ | 	When user clicks on the notification generated by the _GEOFENCE_TRANSITION_ENTER_, _GEOFENCE_TRANSITION_EXIT_ or _GEOFENCE_TRANSITION_DWELL_ transitions.
|_ERROR_ 				| 	When a error occur on the process
