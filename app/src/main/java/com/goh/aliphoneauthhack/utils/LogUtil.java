package com.goh.aliphoneauthhack.utils;

import android.util.Log;

import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;

public class LogUtil {

    private static final int LOGGER_MAX_LENGTH = 3 * 1024;

    private static final String TAG = "GOH";

    private static int level = VERBOSE;

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            log(tag, msg, VERBOSE);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            log(tag, msg, DEBUG);
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            log(tag, msg, INFO);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            log(tag, msg, WARN);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            log(tag, msg, ERROR);
        }
    }

    /**
     * 对长度进行截断，避免 Log 长度限制 4k 导致显示不全
     */
    private static void log(String tag, String msg, int level) {
        if (tag == null || tag.length() <= 0 || msg == null || msg.length() <= 0) {
            return;
        }
        if (msg.length() > LOGGER_MAX_LENGTH) {
            while (msg.length() > LOGGER_MAX_LENGTH) {
                print(tag, msg.substring(0, LOGGER_MAX_LENGTH), level);
                msg = msg.substring(LOGGER_MAX_LENGTH);
            }
        }
        print(tag, msg, level);
    }

    private static void print(String tag, String msg, int level) {
        switch (level) {
            case VERBOSE:
                Log.v(tag, msg);
                break;
            case DEBUG:
                Log.d(tag, msg);
                break;
            case INFO:
                Log.i(tag, msg);
                break;
            case WARN:
                Log.w(tag, msg);
                break;
            case ERROR:
                Log.e(tag, msg);
                break;
        }
    }
}
