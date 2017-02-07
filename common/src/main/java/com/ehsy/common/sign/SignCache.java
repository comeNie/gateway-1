package com.ehsy.common.sign;

import com.ehsy.svccfg.model.SvcSign;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ehsy_it on 2016/7/18.
 */
public class SignCache {

    private SignFactory signFactory;

    private static ConcurrentHashMap<String, String> keyMaps = new ConcurrentHashMap<>();

    public void setSignFactory(SignFactory signFactory) {
        this.signFactory = signFactory;
    }

    public String getKeyMaps(String appid) {
        if(keyMaps.containsKey(appid)){
            return keyMaps.get(appid);
        }else{
            SvcSign svcSign = signFactory.getSvcSignByAppID(appid);
            if(null != svcSign){
                keyMaps.put(appid, svcSign.getPrivateKey());
                return keyMaps.get(appid);
            }
        }
        return null;
    }

    public void setCompMaps(String appid, String key) {
        keyMaps.put(appid, key);
    }
}
