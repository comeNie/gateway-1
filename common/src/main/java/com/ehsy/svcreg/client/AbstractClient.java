package com.ehsy.svcreg.client;

import com.ehsy.svcreg.utils.SvcRegConst;
import com.ehsy.svcreg.utils.SvcUtils;

/**
 * Created by zhuangmg on 5/30/16.
 */
public abstract class AbstractClient implements SvcRegClient {

    protected String getCheckUrl(int serverPort, String projectContext){
        String protocol = SvcRegConst.DEFAULT_PROTOCOL;
        String host = SvcUtils.getLocalIP();
        if(host == null){
            host = SvcRegConst.DEFAULT_HOST;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(protocol).append("://");
        buffer.append(host).append(":");
        buffer.append(serverPort).append("/");
        buffer.append(projectContext).append("/");
        buffer.append("health");
        return buffer.toString();
    }

}
