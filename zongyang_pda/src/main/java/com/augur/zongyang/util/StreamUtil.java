package com.augur.zongyang.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by yunhu on 2017-12-11.
 */

public class StreamUtil {public static void closeStream(Closeable paramCloseable) {
    if (paramCloseable != null) {
        try {
            paramCloseable.close();
            return;
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
}
