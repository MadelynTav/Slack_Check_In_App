package nyc.apprentice.madelyntavarez.check_in_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by madelyntavarez on 1/10/16.
 * Receive Notification click and send message to slack channel
 */
public class LocationReceiver extends BroadcastReceiver {
    private static final String SEND_MESSAGE = "https://slack.com/api/chat.postMessage?token=xoxp-2215037996-17491994852-18213079808-ef35e598fb&channel=C064TT9KK&text";

    @Override
    public void onReceive(Context context, Intent intent) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, SEND_MESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("text", "I'm Here");
                params.put("as_user", "true");
                return params;
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);
    }
}
