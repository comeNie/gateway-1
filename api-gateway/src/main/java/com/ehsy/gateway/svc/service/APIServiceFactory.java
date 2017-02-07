package com.ehsy.gateway.svc.service;

/**
 * Created by zhuangmg on 6/16/16.
 */
public interface APIServiceFactory {

    APIService getService(String category);

}
