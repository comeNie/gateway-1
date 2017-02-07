package com.ehsy.svccfg.svc.service;

import com.ehsy.common.page.Page;
import com.ehsy.svccfg.model.SvcDescInfo;
import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by ehsy_it on 2016/6/22.
 */
public interface SvcDescInfoService {
    String getDescByCode(String code);

    int insert(SvcDescInfo svcDescInfo);

    List<SvcDescInfo> getAllServices();

    List<Svc> getServicesByCmp(String component);

    int stop(String code);

    SvcDescInfo getSvcDescInfoById(String id);

    SvcDescInfo getSvcDescInfoByCode(String code);

    int postEdit(SvcDescInfo svcDescInfo);

    Page<SvcDescInfo> list(SvcDescInfo svcDescInfo, int pageNo);

    int del(String id);

    boolean checkSvcIsExist(String code, String... ids);

    int updateSelective(SvcDescInfo svcDescInfo);

    List<SvcDescInfo> getDescs();

    boolean checkAliasExist(String alias, String... ids);
}
