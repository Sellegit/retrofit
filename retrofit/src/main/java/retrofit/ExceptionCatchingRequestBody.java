package retrofit;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit.sharehttp.MediaType;
import retrofit.sharehttp.ResponseBody;

class ExceptionCatchingRequestBody extends ResponseBody {
  private final ResponseBody delegate;
  private IOException thrownException;

  ExceptionCatchingRequestBody(ResponseBody delegate) {
    this.delegate = delegate;
  }

  @Override public MediaType contentType() {
    return delegate.contentType();
  }

  @Override public long contentLength() throws IOException {
    return delegate.contentLength();
  }

  @Override public BufferedSource source() throws IOException {
    return Okio.buffer(new ForwardingSource(delegate.source()) {
        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            try {
                return super.read(sink, byteCount);
            } catch (IOException e) {
                thrownException = e;
                throw e;
            }
        }
    });
  }

  IOException getThrownException() {
    return thrownException;
  }

  boolean threwException() {
    return thrownException != null;
  }
}
