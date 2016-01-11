package nyc.apprentice.madelyntavarez.check_in_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by madelyntavarez on 1/10/16.
 * Receive Notification click and send message to slack channel
 */
public class LocationReceiver extends Activity {
    //token: xoxp-2215037996-17491994852-18213079808-ef35e598fb
    private static final String SEND_MESSAGE = "https://slack.com/api/chat.postMessage?token=xoxp-2215037996-17491994852-18213079808-ef35e598fb&channel=C064TT9KK&text=Here!&as_user=madey&pretty=1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_receiver_activity);


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Log.d("ToTry", "");
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(SEND_MESSAGE);
                try {
                    httpclient.execute(httppost);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

        }.execute();

    }
}
