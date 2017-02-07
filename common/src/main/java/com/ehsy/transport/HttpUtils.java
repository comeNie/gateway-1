package com.ehsy.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangmg on 6/6/16.
 */
public class HttpUtils {

    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String pstr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                pstr = pstr + key + "=" + value.toString();
            } else {
                pstr = pstr + key + "=" + value.toString() + "&";
            }
        }

        return pstr;
    }

}
