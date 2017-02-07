package com.ehsy.svccfg.svc.service;

import com.ehsy.svccfg.svc.model.SvcRelation;

import java.util.List;

/**
 * Created by ehsy_it on 2016/6/22.
 */
public interface SvcRelationService {

    List<SvcRelation> getRelations(String code);

    int setRelation(SvcRelation svcRelation);

    int updateRelation(SvcRelation svcRelation);

    int delRelation(SvcRelation svcRelation);
}
