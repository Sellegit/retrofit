package retrofit.sharehttp;

import java.io.IOException;
import java.util.concurrent.Semaphore;
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

    private static class Out<T> {
        T obj;
    }

    public Response executeSync(Request request) throws IOException {
        final Semaphore waitSem = new Semaphore(0);
        final Out<Response> responseWrapper = new Out<>();
        final Out<IOException> exWrapper = new Out<>();
        executeAsync(request, new ResponseCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                exWrapper.obj = e;
                waitSem.release();
            }

            @Override
            public void onResponse(Response response) {
                responseWrapper.obj = response;
                waitSem.release();
            }
        });
        try {
            waitSem.acquire();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
        if (exWrapper.obj != null) {
            throw exWrapper.obj;
        }
        return responseWrapper.obj;
    }

    public abstract void executeAsync(Request request, ResponseCallback callback);
}
