package nyc.apprentice.madelyntavarez.check_in_app;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by madelyntavarez on 1/14/16.
 */
public class PostToSlack {
    static final String BASE_URL ="https://slack.com/api/chat.postMessage?token=xoxp-2215037996-17491994852-18213079808-ef35e598fb&channel=C064TT9KK";
    String message = "I'm Here";
    boolean postAsSelf = true;
    Context context;

    PostToSlack(Context context){
        this.context = context;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPostAsSelf(boolean postAsSelf) {
        this.postAsSelf = postAsSelf;
        Toast.makeText(context,"You Will Now Post As A Bot",Toast.LENGTH_LONG).show();
    }

    public String getMessage() {
        return message;
    }

    public boolean isPostAsSelf() {
        return postAsSelf;
    }
}
