package com.ehsy.svcreg.discovery;

import com.ehsy.svcreg.model.Svc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhuangmg on 6/7/16.
 */
public class SvcLocalCache {

    private static ConcurrentHashMap<String, List<Svc>> svcMaps = new ConcurrentHashMap<>();

    public static void initCache(List<Svc> svcs){
        if(svcs != null){
            for(Svc svc : svcs){
                String svcName = svc.getSvcName();
                if(!svcMaps.containsKey(svcName)){
                    svcMaps.put(svcName, new ArrayList<Svc>());
                }
                svcMaps.get(svcName).add(svc);
            }
        }
    }

    public static List<Svc> getSvcList(String svcName){
        if(svcMaps.containsKey(svcName)){
            return svcMaps.get(svcName);
        }
        return null;
    }

    public static void setSvcList(String svcName, List<Svc> svcList){
        svcMaps.put(svcName, svcList);
    }

    public static void delSvcList(String svcName){
        svcMaps.remove(svcName);
    }

    public static ConcurrentHashMap<String, List<Svc>> getSvcMaps() {
        return svcMaps;
    }
}
