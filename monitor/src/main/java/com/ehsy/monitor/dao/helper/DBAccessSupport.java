package com.ehsy.monitor.dao.helper;

import org.mongodb.morphia.DatastoreImpl;

/**
 * Created by zhuangmg on 6/23/16.
 */
public interface DBAccessSupport {

    DatastoreImpl getDatastore();

}
