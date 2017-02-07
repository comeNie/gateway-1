package com.ehsy.svcreg.utils;

import com.ehsy.common.utils.CMUtils;
import com.ehsy.svcreg.model.Svc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuangmg on 6/3/16.
 */
public class SvcUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SvcUtils.class);

    /**
     * 转化成服务列表
     * @param services
     * @param port
     * @param component
     * @return
     */
    public static List<Svc> convertToSvcs(String services, String port, String component){
        List<Svc> svcs = new ArrayList<>();
        Pattern p = Pattern.compile("(\\w+,\\d+,.*[^\\)$])");
        Matcher m = p.matcher(services);
        while(m.find()){
            String sgroup = m.group();
            String[] svcArr = sgroup.split(",");
            Svc svc = new Svc();
            svc.setHost(getLocalIP());
            if(CMUtils.isNumeric(port)){
                svc.setPort(Integer.valueOf(port));
            }
            svc.setSvcName(svcArr[0]);
            svc.setVersion(svcArr[1]);
            svc.setPath(svcArr[2]);
            svc.setComponent(component);
            svcs.add(svc);
        }
        return svcs;
    }

    /**
     * 获取机器ip
     * 根据当前环境分析,获取的是eth0地址,此地址为内网地址
     * @return
     */
    public static String getLocalIP() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            if(ip != null){
                return ip;
            }
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("{}", e);
        }
        return null;
    }

}
