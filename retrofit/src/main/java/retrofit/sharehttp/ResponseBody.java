package retrofit.sharehttp;

import okio.Source;

/**
 * Created by dementrock on 3/20/15.
 */
public abstract class ResponseBody {

    public abstract MediaType contentType();

    public abstract Source source();

    public abstract long contentLength();


}
