var geofence = require("ti.android.geofence");
var locationAllowed = false;

var window = Titanium.UI.createWindow({
    backgroundColor : '#F2F2F2',
    layout : 'vertical',
    theme : 'Theme.MyTheme'
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
                "id" : "Outback",
                "latitude" : -19.920569,
                "longitude" : -43.921137,
                "radius" : 150,
                "transitions" : [
                    geofence.GEOFENCE_TRANSITION_ENTER
                ],
                //Notification 
                "title" : "Restaurante Outback",
                "sound" : "notification",
                "accentColor" : "#E65100",
                "type" : geofence.TYPE_PLACE_FENCE,
                "showPlaceBigImage" : true,
                //"bigImage" : "https://lh3.googleusercontent.com/jOsYWBsr1muoRiMQFW9EU-ZqSCtfLBibu6S2g4nIbihP0SYL4Em6VD20WuieL1h5bBzbSrnIYVQZy5lhjUSR"
            },
            {
                "id" : "Luiz",
                "latitude" : -19.84160077,
                "longitude" : -43.93884106,
                "radius" : 150,
                "transitions" : [
                    geofence.GEOFENCE_TRANSITION_ENTER
                ],
                //Notification 
                "title" : "Casa do Luiz",
                "sound" : "notification",
                "accentColor" : "#E65100",
                "type" : geofence.TYPE_PLACE_FENCE,
                "showPlaceBigImage" : true,
                //"bigImage" : "https://static.wixstatic.com/media/92734c_31512f187c9241149ba53ee30e7ca7f7~mv2.jpg_256"
            },
            {
                "id" : "Casa",
                "latitude" : -19.89788818,
                "longitude" : -43.92246886,
                "radius" : 150,
                "transitions" : [
                    geofence.GEOFENCE_TRANSITION_DWELL
                ],
                //Notification 
                "title" : "Home Sweet Home",
                "sound" : "notification",
                "accentColor" : "#E65100",
                "type" : geofence.TYPE_PLACE_FENCE,
                "showPlaceBigImage" : true,
                //"bigImage" : "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F38585705%2F47533216579%2F1%2Foriginal.jpg?h=512&w=512&auto=compress&rect=0%2C40%2C500%2C250&s=c87e485256c42bd0c9f181eb9c371a3f"
            },
            {
                "id" : "Tups",
                "latitude" : -19.95899221,
                "longitude" : -43.93085491,
                "radius" : 150,
                "transitions" : [
                    geofence.GEOFENCE_TRANSITION_DWELL,
                    geofence.GEOFENCE_TRANSITION_ENTER
                ],
                //Notification 
                "title" : "Casa do Tups",
                "sound" : "notification",
                "accentColor" : "#E65100",
                "type" : geofence.TYPE_PLACE_FENCE,
                "showPlaceBigImage" : true,
                //"bigImage" : "https://i.pinimg.com/originals/e0/db/87/e0db879bd7f0cfaf10266ce05f4ab926.jpg"
            },
            {
                "id" : "em",
                "latitude" : -19.9334664,
                "longitude" : -43.92671121,
                "radius" : 200,
                "transitions" : [
                    geofence.GEOFENCE_TRANSITION_ENTER
                ],
                //Notification 
                "title" : "Estado de Minas",
                "sound" : "notification",
                "accentColor" : "#E65100",
                "type" : geofence.TYPE_PLACE_FENCE,
                "showPlaceBigImage" : true,
                //"bigImage" : "https://www.cartamaior.com.br/arquivosCartaMaior/FOTO/149/A0B327F4137E11E0619283C75006AAC0528734ED8D09B13C12CF1F6AF22BC5EA.png"
            },
            {
                "id" : "Kasbah",
                "latitude" : -19.93328338,
                "longitude" : -43.92952192,
                "radius" : 150,
                "transitions" : [
                    geofence.GEOFENCE_TRANSITION_ENTER
                ],
                //Notification 
                "title" : "Restaurante Kasbah",
                "sound" : "notification",
                "accentColor" : "#E65100",
                "type" : geofence.TYPE_PLACE_FENCE,
                "showPlaceBigImage" : true,
                //"bigImage" : "https://d2rlg75vf4lcam.cloudfront.net/uploads/restaurant_picture/picture/17769/restaurant_detail_ambiente-.jpg"
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