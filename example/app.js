var geofence = require("ti.android.geofence");
var locationAllowed = false;

var window = Titanium.UI.createWindow({
    backgroundColor : '#F2F2F2',
    layout : 'vertical',
}); 

 window.addEventListener('open', function(){
    if (!Titanium.Geolocation.hasLocationPermissions(Titanium.Geolocation.AUTHORIZATION_ALWAYS)) {
        Titanium.API.warn('Location permissions not granted! Asking now...');
        Titanium.Geolocation.requestLocationPermissions(Titanium.Geolocation.AUTHORIZATION_ALWAYS, function(e) {
            if (!e.success) {
                Titanium.API.error('Location permissions declined!');
            } else {
                Titanium.API.info('Location permissions ready');
                // Initialize monitoring here
                locationAllowed = true;
            }
        });
    } else {
        Titanium.API.warn('Location permissions already granted!'); 
        // Initialize monitoring here
        locationAllowed = true;
    } 
});

var button_add = Titanium.UI.createButton({
    top : 24,
    title : 'Add Fences',
    backgroundColor : '#607D8B',
    color : '#FFF',
    width : 180
});
window.add(button_add);
    
button_add.addEventListener('click', function(){
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
                "dwellTime" : (5 * 60 * 1000).toFixed(2),
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
                "dwellTime" : (3 * 60 * 1000).toFixed(2),
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
});

var button_start = Titanium.UI.createButton({
    top : 24,
    title : 'Start Monitoring',
    backgroundColor : '#009688',
    color : '#FFF',
    width : 180
});
window.add(button_start);

button_start.addEventListener('click', function(){
    if(locationAllowed){
        geofence.startMonitoring();
    }
});

var button_clear = Titanium.UI.createButton({
    top : 24,
    title : 'Clear Fences',
    backgroundColor : '#F44336',
    color : '#FFF',
    width : 180
});
window.add(button_clear);

button_clear.addEventListener('click', function(){
    geofence.clearExistingFences();
});

var button_stop = Titanium.UI.createButton({
    top : 24,
    title : 'Stop Monitoring',
    backgroundColor : '#795548',
    color : '#FFF',
    width : 180
});
window.add(button_stop);

button_stop.addEventListener('click', function(){
    geofence.stopMonitoring();
});

geofence.addEventListener(geofence.ERROR, function(e){
    Titanium.API.error("GEOFENCE ERROR");
    Titanium.API.error(e.message);
});

geofence.addEventListener(geofence.ENTERED, function(e){
    Titanium.API.info("ENTERED");
    Titanium.API.info(JSON.stringify(e));
});

geofence.addEventListener(geofence.EXITED, function(e){
    Titanium.API.info("EXITED");
    Titanium.API.info(JSON.stringify(e));
});

geofence.addEventListener(geofence.DWELL, function(e){
    Titanium.API.info("DWELL");
    Titanium.API.info(JSON.stringify(e));
});

geofence.addEventListener(geofence.STARTED_MONITORING, function(e){
    Titanium.API.info("STARTED_MONITORING");
    Titanium.API.info("Monitoring...");
});

geofence.addEventListener(geofence.STOPPED_MONITORING, function(e){
    Titanium.API.info("STOPPED_MONITORING");
     Titanium.API.info("RIP!");
});

geofence.addEventListener(geofence.GEOFENCES_ADDED, function(e){
    Titanium.API.info("GEOFENCES_ADDED");
    Titanium.API.info("Ready to start monitoring!");
});

geofence.addEventListener(geofence.NOTIFICATION_CLICKED, function(e){
    Titanium.API.info("NOTIFICATION_CLICKED");
    Titanium.API.info(JSON.stringify(e));
});

window.open();
