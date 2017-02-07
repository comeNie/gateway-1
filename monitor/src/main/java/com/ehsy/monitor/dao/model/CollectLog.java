package com.ehsy.monitor.dao.model;

import com.alibaba.fastjson.JSONObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by zhuangmg on 6/27/16.
 */
@Entity
public class CollectLog {
    @Id
    private ObjectId id;
    private JSONObject info;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public JSONObject getInfo() {
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;
    }
}
