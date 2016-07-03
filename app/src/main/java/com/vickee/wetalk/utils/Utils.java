package com.vickee.wetalk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vickee on 2016/7/3.
 */
public class Utils {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm");

    public static String format(long time) {
        return FORMAT.format(new Date(time));
    }
}
