# Ti.Android.Geofence ![gittio](http://img.shields.io/badge/gittio-3.0.1-00B4CC.svg)

Appcelerator Android module wrapping Geofence functionalities

![Google Geofence](https://developer.android.com/images/training/geofence.png)

## Overview

This module adds support for using Geofence in Titanium Apps.
It can monitor all geofence transitions and fire a local notification as well. You can customize the notification in order to fit your needs.
**This module works when the app is in foreground, background, closed, after phone restart, App data cleared and if location providers get changed.**

#### Titanium SDK
Will work only with **7+**

### Download

Download the Ti.Android.Geofence module through the  [here](https://github.com/deckameron/Ti.Android.Geofence/blob/master/android/dist/ti.android.geofence-1.0.zip).

### Looking for an iOS solution?
Try [this module](https://github.com/deckameron/ci.geofencing).

## How to use it

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
			"longitude" :  -122.084004,
			"radius" : 1000,
			"transitions" :  [
				geofence.GEOFENCE_TRANSITION_ENTER
			],
			//Notification
			"title" :  "Google - Mountain View",
			"sound" :  "notification",
			"accentColor" :  "#E65100",
			"type" :  geofence.TYPE_PLACE_FENCE,
			"showGooglePlaceBigImage" :  true,
			//"bigImage" :"https://lh3.googleusercontent.com/jOsYWBsr1muoRiMQFW9EU-ZqSCtfLBibu6S2g4nIbihP0SYL4Em6VD20WuieL1h5bBzbSrnIYVQZy5lhjUSR"
		},
		{
			"id" : "tesla",
			"latitude" :  37.394834,
			"longitude" :  -122.150046,
			"radius" :  700,
			"transitions" : [
				geofence.GEOFENCE_TRANSITION_ENTER,
				geofence.GEOFENCE_TRANSITION_DWELL
			],
			//Notification
			"title" : "Tesla HQ",
			"sound" :  "notification",
			"accentColor" :  "#E65100",
			"type" :  geofence.TYPE_PLACE_FENCE,
			"showGooglePlaceBigImage" :  true,
			//"bigImage" : "https://static.wixstatic.com/media/92734c_31512f187c9241149ba53ee30e7ca7f7~mv2.jpg_256"
		},
		{
			"id" : "apple",
			"latitude" :  37.331829,
			"longitude" :  -122.029749,
			"radius" :  1000,
			"transitions" :  [
				geofence.GEOFENCE_TRANSITION_EXIT
			],
			//Notification
			"title" :  "Apple Infinite Loop",
			"sound" :  "notification",
			"accentColor" :  "#E65100",
			"type" :  geofence.TYPE_PLACE_FENCE,
			"showGooglePlaceBigImage" :  true,
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


# Necessary Libs

### Google Play Services
You can extract them from [Ti.PlayServices](https://github.com/appcelerator-modules/ti.playservices)

google-play-services-maps.jar
google-play-services-location.jar
google-play-services-basement.jar
google-play-services-tasks.jar
google-play-services-base.jar

### Android Support
These can be found at "/Library/Application Support/Titanium/mobilesdk/osx/7.0.0.GA/android"

android-support-v7-appcompat.jar
android-support-design.jar
android-support-annotations.jar
android-support-compat.jar
