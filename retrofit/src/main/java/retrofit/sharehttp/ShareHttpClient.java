package retrofit.sharehttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by dementrock on 3/20/15.
 */
public abstract class ShareHttpClient {

    private static ShareHttpClientFactory sFactory;

    public static ShareHttpClientFactory getFactory() {
        return sFactory;
    }

    public static void setFactory(ShareHttpClientFactory factory) {
        sFactory = factory;
    }

    public static ShareHttpClient newInstance() {
        if (sFactory != null) {
            return sFactory.newInstance();
        }
        throw new RuntimeException("Need to set Factory before instantiating ShareHttpClient " +
                "instance!");
    }

    public abstract void setConnectTimeout(long timeout, TimeUnit unit);

    public abstract void setReadTimeout(long timeout, TimeUnit unit);

    public abstract void setWriteTimeout(long timeout, TimeUnit unit);

    public abstract Response executeSync(Request request) throws IOException;

    public abstract void executeAsync(Request request, ResponseCallback callback);
}
