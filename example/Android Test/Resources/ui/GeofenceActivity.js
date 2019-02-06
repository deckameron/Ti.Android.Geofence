function MainActivity() {

    var geofence = require("ti.android.geofence");
    var locationAllowed = false;
    
    var window = Titanium.UI.createWindow({
        backgroundColor : '#F2F2F2',
        layout : 'vertical'
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
            service:'com.douglas.androidtest.LocationServiceService', 
            clearExistingFences : false,
            fences : [ 
                {
                    "id" : "office",
                    "latitude" : 28.41259,
                    "longitude" : 77.0425996,
                    "radius" : 500,
                    "transitions" : [ 
                        geofence.GEOFENCE_TRANSITION_ENTER,
                        geofence.GEOFENCE_TRANSITION_DWELL, 
                        geofence.GEOFENCE_TRANSITION_EXIT
                    ],
                    'dwellTime' : 5 * 60 * 1000,
                    
                    //Notification
                    'canNotify' : false, //No autoNotify, I will fireNotification if needed
                    "title" : "Office Address",
                    'message' : 'I am at office address now.',
                    "accentColor" : "#00AEE6",
                    'smallIcon' : 'push',
                    "type" : geofence.TYPE_PLACE_FENCE,
                    "showGooglePlaceBigImage" : true
                },
                {
                    "id" : "huda_metro",
                    "latitude" : 28.4615863,
                    "longitude" : 77.062657,
                    "radius" : 500,
                    "transitions" : [ 
                        geofence.GEOFENCE_TRANSITION_ENTER,
                        geofence.GEOFENCE_TRANSITION_DWELL, 
                        geofence.GEOFENCE_TRANSITION_EXIT
                    ],
                    'dwellTime' : 5 * 60 * 1000,
                    
                    //Notification
                    'canNotify' : false, //No autoNotify, I will fireNotification if needed
                    "title" : "Huda City Centre",
                    'message' : 'I am at Huda City Centre now.',
                    "accentColor" : "#2AC635",
                    'smallIcon' : 'push',
                    "type" : geofence.TYPE_PLACE_FENCE,
                    "showGooglePlaceBigImage" : true
                },
                {
                    "id" : "home",
                    "latitude" : 28.4947681,
                    "longitude" : 77.158355,
                    "radius" : 500,
                    "transitions" : [ 
                        geofence.GEOFENCE_TRANSITION_ENTER,
                        geofence.GEOFENCE_TRANSITION_DWELL, 
                        geofence.GEOFENCE_TRANSITION_EXIT
                    ],
                    'dwellTime' : 5 * 60 * 1000,
                    
                    //Notification
                    'canNotify' : false, //No autoNotify, I will fireNotification if needed
                    "title" : "My Home",
                    'message' : 'I am at HOME now.',
                    "accentColor" : "#D52C7D",
                    'smallIcon' : 'push',
                    "type" : geofence.TYPE_PLACE_FENCE,
                    "showGooglePlaceBigImage" : true
                }
            ]
        });
        
        alert("Added!");
    });
    
    var button_start = Titanium.UI.createButton({
        top : 24,
        title : 'Start Monitoring',
        backgroundColor : '#009688',
        touchFeedback : true,
        color : '#FFF',
        width : 180
    });
    window.add(button_start);
    
    button_start.addEventListener('click', function(){
        if(locationAllowed){
            alert("Monitoring!");
            geofence.startMonitoring();
        }
    });
    
    var button_clear = Titanium.UI.createButton({
        top : 24,
        title : 'Clear Fences',
        backgroundColor : '#F44336',
        touchFeedback : true,
        color : '#FFF',
        width : 180
    });
    window.add(button_clear);
    
    button_clear.addEventListener('click', function(){
        alert("Cleared fences!");
        geofence.clearExistingFences();
    });
    
    var button_stop = Titanium.UI.createButton({
        top : 24,
        title : 'Stop Monitoring',
        backgroundColor : '#795548',
        touchFeedback : true,
        color : '#FFF',
        width : 180
    });
    window.add(button_stop);
    
    button_stop.addEventListener('click', function(){
        alert("Stopped!");
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
    
    return window;
};
module.exports = MainActivity;