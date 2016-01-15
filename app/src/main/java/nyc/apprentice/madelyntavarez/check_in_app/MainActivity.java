package nyc.apprentice.madelyntavarez.check_in_app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class MainActivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = R.integer.requestCode;
    Intent locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > R.integer.zero
                        && grantResults[R.integer.zero] == PackageManager.PERMISSION_GRANTED) {

                    launchService();
                }
            }
        }
    }

    public void startService(View v) {
        if (Build.VERSION.SDK_INT < R.integer.twenty_three) {
            launchService();
            return;
        }

        requestPermission();
    }

    private void launchService() {

        locationService = new Intent(this, LocationService.class);
        startService(locationService);
    }


    public void endService(View v) {


        if (locationService != null) {
            stopService(locationService);
        }
    }

    public void requestPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission_group.LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }
}
