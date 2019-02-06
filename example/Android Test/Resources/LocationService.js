//EXAMPLE
Ti.API.info('IT WORKED! It is a service');
var geofence = require("ti.android.geofence");

var geoTriggers = geofence.getLastestFiredGeofenceTransitionData();

var gLength = geoTriggers.fences.length;
for (var i=0; i < gLength; i++) {
    if(geoTriggers.fences[i].id == "office" && geoTriggers.event == geofence.ENTERED){
        geofence.fireNotification();
    }
};
