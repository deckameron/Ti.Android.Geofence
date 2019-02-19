First you need to require the module
```javascript
var geofence = require("ti.android.geofence");
```

You need to ask for Geolocation permissions in order to work with this module
```javascript
if (!Titanium.Geolocation.hasLocationPermissions(Titanium.Geolocation.AUTHORIZATION_ALWAYS)) {
	Titanium.API.warn('Location permissions not granted! Asking now...');
	Titanium.Geolocation.requestLocationPermissions(Titanium.Geolocation.AUTHORIZATION_ALWAYS, function(e) {
		if (!e.success) {
			Titanium.API.error('Location permissions declined!');
		} else {
			Titanium.API.info('Location permissions ready');
			//Initialize monitoring here
		}
	});
} else {
	Titanium.API.warn('Location permissions already granted!');
	//Initialize monitoring here
}
```

Then you need to add the fences
```javascript
geofence.addGeofences({
	clearExistingFences : false,
	fences : [
		{
			"id" : "google",
			"latitude" : 37.422196,
			"longitude" : -122.084004,
			"radius" : 1000,
			"transitions" : [
				geofence.GEOFENCE_TRANSITION_ENTER
			],
			//Notification 
			"title" : "Google - Mountain View",
			"sound" : "notification",
			"accentColor" : "#E65100",
			"type" : geofence.TYPE_PLACE_FENCE,
			"showGooglePlaceBigImage" : true,
			//"bigImage" : "https://lh3.googleusercontent.com/jOsYWBsr1muoRiMQFW9EU-ZqSCtfLBibu6S2g4nIbihP0SYL4Em6VD20WuieL1h5bBzbSrnIYVQZy5lhjUSR"
		},
		{
			"id" : "tesla",
			"latitude" : 37.394834,
			"longitude" : -122.150046,
			"radius" : 700,
			"transitions" : [
				geofence.GEOFENCE_TRANSITION_ENTER,
				geofence.GEOFENCE_TRANSITION_DWELL
			],
			"dwellTime" : 5 * 60 * 1000,
			//Notification 
			"title" : "Tesla HQ",
			"sound" : "notification",
			"accentColor" : "#E65100",
			"type" : geofence.TYPE_PLACE_FENCE,
			"showGooglePlaceBigImage" : true,
			//"bigImage" : "https://static.wixstatic.com/media/92734c_31512f187c9241149ba53ee30e7ca7f7~mv2.jpg_256"
		},
		{
			"id" : "apple",
			"latitude" : 37.331829,
			"longitude" : -122.029749,
			"radius" : 1000,
			"transitions" : [
				geofence.GEOFENCE_TRANSITION_DWELL
			],
			"dwellTime" : 3 * 60 * 1000,
			//Notification 
			"title" : "Apple Infinite Loop",
			"sound" : "notification",
			"accentColor" : "#E65100",
			"type" : geofence.TYPE_PLACE_FENCE,
			"showGooglePlaceBigImage" : true,
			//"bigImage" : "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F38585705%2F47533216579%2F1%2Foriginal.jpg?h=512&w=512&auto=compress&rect=0%2C40%2C500%2C250&s=c87e485256c42bd0c9f181eb9c371a3f"
		}
	]
});
```
Now you are good to go!
```javascript
geofence.startMonitoring();
```

#### Full example:
Please take a look at the full example of the [module being used](https://github.com/deckameron/Ti.Android.Geofence/blob/master/example/app.js).
 
# Methods

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
|fences    		|_(Array)_  - List of fences objects
|service    	|_(String)_  - This is javascript written service you would like to run when a Geofence transition event gets fired. Example : com.myproject.geofence.myServiveService

### Fences object

|Attributes             |Description                          |
|----------------|-------------------------------|
|id					|_(String)_ - an unique id of your choosing                   
|type    			|_(String)_ - the type is a free string in case you want to have more control of your fences. For example : "standard", "master_fence" or "fence_updater"       
|latitude    		|_(Number)_  - latitude of fence center
|longitude    		|_(Number)_  - longitude of fence center
|radius    			|_(Number)_  - fence radius
|dwellTime    		|_(Number)_  - the amount of time the user has to remain inside the fence. The time is MILLISECONDS.
|canNotify    		|_(Boolean)_  - If you want the notication to be shown immediately when Geofence events get fired. Note : If you want to fire the notification yourself (via the method *fireNotification()*), set *canNotify* to false, but don't forget to fill all notification attributes when creating the fence, otherwise the module will not be able to show the notification.
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

Clears all existing fences already being monitored. **It will also stop the monitoring service**.

## stopMonitoring

Just stops the monitoring service for the current fences. It will NOT clear the existing fences already being monitored.

## getLastestFiredGeofenceTransitionData
Returns the data from the latest fired event in *json* like this. 

```javascript
 {
 	"fences":[
 		{
 			"latitude":"28.41259",
 			"id":"huda_metro",
 			"longitude":"77.0425996"
 		}
 	],
 	"event":"ENTERED"
 }
```
## fireNotification
Fires a local notification with fence information if there is a Geofence transition waiting to be fired.

# Events

These events can only be monitored when your app is in foreground or in background. They will never fire when your app is closed because the instance of your application does not exist.

|Events                |Description                          |
|----------------|-------------------------------|
|_ENTERED_				|   When user enters a geofence           
|_EXIT_    				| 	When user exits a geofence
|_DWELL_    			|	When user remains inside a geofence for the specified period of time
|_STARTED_MONITORING_	|  	When the Android starts monitoring for your fences  
|_STOPPED_MONITORING_   | 	When the Android stops monitoring for your fences  
|_GEOFENCES_ADDED_    	|	When the fences get added to the module
|_NOTIFICATION_CLICKED_ | 	When user clicks on the notification generated by the _GEOFENCE_TRANSITION_ENTER_, _GEOFENCE_TRANSITION_EXIT_ or _GEOFENCE_TRANSITION_DWELL_ transitions.
|_NOTIFICATION_FIRED_IN_BACKGROUND_ 				| 	When the app is in background (not killed) when the notification gets fired
|_NOTIFICATION_FIRED_IN_FOREGROUND_ 				| 	When the app is in foreground (not killed) when the notification gets fired
|_ERROR_ 				| 	When a error occur on the process

# Background Services in JS

If you want to execute a javascript code whenever the ENTERED, EXIT or DWELL events get fired. This is how:

#### 1 ) Create a file name myService.js and write your backgroundService code. Place it in Resources folder.
```javascript
//EXAMPLE
Ti.API.info('IT WORKED! It is a service');
var geofence = require("ti.android.geofence");

var geoTriggers = geofence.getLastestFiredGeofenceTransitionData();

if(geoTriggers.fences) {
    var gLength = geoTriggers.fences.length;
    for (var i=0; i < gLength; i++) {
        if(geoTriggers.fences[i].id == "huda_metro" && geoTriggers.event == geofence.ENTERED){
            geofence.fireNotification();
        }
    };
} else {
    Titanium.API.warn("No fences for this event!");
};
```

#### 2 ) Add the tag services to you android tag on tiapp.xml, like below:
```xml
<android xmlns:android="http://schemas.android.com/apk/res/android">
    <manifest>
	 <!-- YOUR MANIFEST CODES HERE -->
    </manifest>
    <services>
        <service type="interval" url="myService.js"/>
    </services>
</android>
```

#### 3) Compile the project

Before moving on and start using your service you will need to re-compile your project. After recompiling your project, open your "YOUR_PROJECT_FOLDER"/build/android/AndroidManifest.xml. Look for your service name and you will find its full name, something like _"com.myproject.geofence.myServiveService"_. This is important as Titanium generates this name.
To learn more about Android Services please read the documentation [here](https://docs.appcelerator.com/platform/latest/#!/guide/Android_Services).

#### 4) Add the service key to addGeofences method 
```javascript
geofence.addGeofences({
	clearExistingFences : false,
	fences : ["YOUR_FENCES"],
	service : "com.myproject.geofence.myServiveService" //THIS IS IT!
});
```

# Necessary Libs

### Google Play Services
You can extract them from [Ti.PlayServices](https://github.com/appcelerator-modules/ti.playservices/tree/master/android/lib)

#### google-play-services-maps.jar
#### google-play-services-location.jar
#### google-play-services-basement.jar
#### google-play-services-tasks.jar
#### google-play-services-base.jar

### Android Support
These can be found at "/Library/Application Support/Titanium/mobilesdk/osx/7.0.0.GA/android"

#### android-support-v7-appcompat.jar
#### android-support-design.jar
#### android-support-annotations.jar
#### android-support-compat.jar