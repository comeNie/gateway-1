package com.ehsy.transport;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by zhuangmg on 6/6/16.
 */
public class Requests {

    public static HttpRequest createDefault(int socketTimeout, int connectTimeout){
        return RequestBuilder.create().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

}
