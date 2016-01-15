package nyc.apprentice.madelyntavarez.check_in_app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;


/**
 * Created by madelyntavarez on 1/10/16.
 */
public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    static final LatLng INTREPID_LAT_LNG = new LatLng(42.367010, -71.080210);
    static final LatLng ROGERS_LAT_LNG = new LatLng(42.366456899999996, -71.0780707);
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int MY_NOTIFICATION_ID = 1;
    private int nextNotification;
    private boolean inRogers = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(900 * 1000)        // 15 minutes, in milliseconds
                .setFastestInterval(10 * 1000); // 1 minute, in milliseconds
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        checkIfNearWorkplace(location);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        checkIfNearWorkplace(location);
    }

    public void checkIfNearWorkplace(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        float[] distanceToThird = new float[1];
        Location.distanceBetween(INTREPID_LAT_LNG.latitude, INTREPID_LAT_LNG.longitude, latitude, longitude, distanceToThird);
        float[] distanceToRogers = new float[1];
        Location.distanceBetween(ROGERS_LAT_LNG.latitude, ROGERS_LAT_LNG.longitude, latitude, longitude, distanceToRogers);

        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
            /*
            //check if current time in milliseconds is greater than the next time we set
            // for the launch of the notificion, if so, build the notification, will always be true
            // the first time the app is launched
            */
        if ((distanceToThird[0] < R.integer.fifty || distanceToRogers[0] < 50) && seconds >= nextNotification) {
            inRogers = true;
            buildNotification();
        }
    }

    private void buildNotification() {
        putNotificationToSleep();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_check_24dp);
        mBuilder.setContentTitle("I have arrived at work, click me!");
        mBuilder.setContentText("I will alert your team members");
        mBuilder.setAutoCancel(true);

        Intent intent = new Intent(this, LocationReceiver.class);
        intent.putExtra("inRogers", inRogers);
        intent.setAction("MyBroadcast");
        PendingIntent resultPendingIntent =
                PendingIntent.getBroadcast(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());

    }

    //rest notification for 12 hours
    private void putNotificationToSleep() {
        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);

        nextNotification = seconds + 43200000;
    }
}
