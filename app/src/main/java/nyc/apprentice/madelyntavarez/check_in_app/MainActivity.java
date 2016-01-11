package nyc.apprentice.madelyntavarez.check_in_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    Intent locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startService(View v){
            locationService = new Intent(this, LocationService.class);
            startService(locationService);
    }

    private void endService(View v){
        if (locationService!=null) {
            stopService(locationService);
        }
    }
}
