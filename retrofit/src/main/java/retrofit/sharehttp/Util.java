package retrofit.sharehttp;

import java.io.Closeable;
import java.nio.charset.Charset;

/**
 * Created by dementrock on 3/20/15.
 */
public class Util {

  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static void checkOffsetAndCount(long arrayLength, long offset, long count) {
    if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
      throw new ArrayIndexOutOfBoundsException();
    }
  }

  public static void closeQuietly(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (RuntimeException rethrown) {
        throw rethrown;
      } catch (Exception ignored) {
      }
    }
  }
}
