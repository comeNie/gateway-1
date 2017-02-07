package com.ehsy.common.utils;

import com.ehsy.common.exception.CMRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuangmg on 6/2/16.
 */
public class CMUtils {

    private static final String DATE_FORMAT_17 = "yyyyMMddHHmmssSSS";

    private static ThreadLocal<DateFormat> threadLocal17 = new ThreadLocal<DateFormat>() {
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT_17);
        }
    };

    public static DateFormat getDateFormat17() {
        return threadLocal17.get();
    }

    public static String formatDate19(Date date) {
        return getDateFormat17().format(date);
    }

    public static void throwException(String... args){
        String msg = "";
        for(String arg : args){
            msg += arg;
        }
        throw new CMRuntimeException(msg);
    }

    public static boolean isNumeric(String num){
        if(num != null)
            return StringUtils.isNumeric(num);
        else
            return false;
    }


}
