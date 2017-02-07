package com.ehsy.monitor.dao.helper;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.springframework.util.StringUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuangmg on 6/23/16.
 */
public class MongoDBAccessSupport implements DBAccessSupport {

    private DatastoreImpl datastore = null;

    private Lock lock = new ReentrantLock();

    private String host;
    private String replSet;
    private String database;

    public void setHost(String host) {
        this.host = host;
    }

    public void setReplSet(String replSet) {
        this.replSet = replSet;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public DatastoreImpl getDatastore() {
        if(this.datastore == null){
            try{
                lock.lock();
                if(this.datastore == null){
                    //构建uri
                    String uri;
                    if(StringUtils.isEmpty(this.replSet)){
                        uri = "mongodb://" + this.host;
                    }else{
                        uri = "mongodb://" + this.host + "/?replicaSet=" + this.replSet;
                    }
                    MongoClient client = new MongoClient(new MongoClientURI(uri));
                    Morphia morphia = new Morphia();
                    morphia.mapPackage("com.morphia.domain", true);
                    //设置datastore
                    this.datastore = new DatastoreImpl(morphia, client, this.database);
                }
            }finally {
                lock.unlock();
            }
        }
        return this.datastore;
    }

}
