package com.ehsy.common.sign;

import com.ehsy.svccfg.model.SvcSign;

/**
 * Created by ehsy_it on 2016/7/18.
 */
public interface SignFactory {

    SvcSign getSvcSignByAppID(String appID);
}
