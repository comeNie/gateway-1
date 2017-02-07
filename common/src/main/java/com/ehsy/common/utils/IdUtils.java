package com.ehsy.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by ehsy_it on 2016/7/22.
 */
public class IdUtils {

    /**
     * 创建UUID:yyyyMMddHHmmssSSS+3位随机数
     * @return
     */
    public static synchronized String makeUUID() {
        Date date = new Date();
        StringBuffer s = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date));
        return s.append((new Random().nextInt(900) + 100)).toString();
    }
}
