package retrofit.sharehttp;

import java.io.IOException;

/**
 * Created by dementrock on 3/20/15.
 */
public interface ResponseCallback {

    void onFailure(Request request, IOException e);

    void onResponse(Response response);

}
