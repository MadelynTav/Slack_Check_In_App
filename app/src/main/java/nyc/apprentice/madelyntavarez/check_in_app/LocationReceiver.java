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
    PostToSlack postToSlack;
    boolean inRogers;

    @Override
    public void onReceive(final Context context, Intent intent) {
        postToSlack = new PostToSlack(context);
        inRogers = intent.getBooleanExtra("inRogers", false);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, PostToSlack.BASE_URL, new Response.Listener<String>() {
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
                if (inRogers) {
                    params.put("text", postToSlack.getMessage() + " at Rogers");
                } else {
                    params.put("text", postToSlack.getMessage() + " at Third Street");
                }
                params.put("as_user", String.valueOf(postToSlack.isPostAsSelf()));
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
