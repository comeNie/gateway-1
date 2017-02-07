package com.ehsy.monitor.dao;

import com.ehsy.monitor.dao.model.OpLog;

/**
 * Created by zhuangmg on 6/23/16.
 */
public interface LogDao <T> {

    void insert(String collection, T log);

}
