package com.ehsy.monitor.dao;

import com.ehsy.monitor.dao.helper.DBAccessSupport;
import org.mongodb.morphia.DatastoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuangmg on 6/23/16.
 */
public class MongoDBLogDao <T> implements LogDao <T> {

    private static Logger LOG = LoggerFactory.getLogger(MongoDBLogDao.class);

    private DBAccessSupport dbAccessSupport;

    public void setDbAccessSupport(DBAccessSupport dbAccessSupport) {
        this.dbAccessSupport = dbAccessSupport;
    }

    @Override
    public void insert(String collection, T log) {
        DatastoreImpl datastore = this.dbAccessSupport.getDatastore();
        try {
            datastore.save(collection, log);
        } catch (Exception e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                LOG.error("{}", e1);
            }
            LOG.error("log save failed, retry again");
            try {
                datastore.save(collection, log);
            } catch (Exception e1) {
                LOG.error("{}", e1);
            }
        }

    }

}
