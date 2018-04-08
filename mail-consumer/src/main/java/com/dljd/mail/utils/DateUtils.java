package com.dljd.mail.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by jiaozhiguang on 2018/3/15.
 */
public class DateUtils {

    public static String format(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }

}
