package com.ehsy.common.sign;

import com.ehsy.common.msg.response.ResponseMsg;
import com.ehsy.common.utils.Constants;
import com.ehsy.common.utils.TypeRef;
import com.ehsy.svccfg.model.SvcSign;
import com.ehsy.svcreg.utils.SvcRegConst;
import com.ehsy.transport.SvcExecutor;
import com.ehsy.transport.data.SvcResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehsy_it on 2016/7/18.
 */
public class Sign implements SignFactory{

    private SvcExecutor svcExecutor;

    public void setSvcExecutor(SvcExecutor svcExecutor) {
        this.svcExecutor = svcExecutor;
    }

    @Override
    public SvcSign getSvcSignByAppID(String appID) {
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("appID", appID);
        SvcResult<SvcSign> svcResult = this.svcExecutor.execute(SvcRegConst.CMP_CFG_SVC_SIGN_BY_APPID, pathParams, null, null, new TypeRef<ResponseMsg<SvcSign>>(){});
        return svcResult.getData().get(Constants.SVC_SIGN_BY_APPID_KEY);
    }
}
