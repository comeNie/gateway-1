package com.ehsy.svcreg.registry;

import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by zhuangmg on 6/7/16.
 */
public interface SvcDefFactory {

    List<Svc> getProvidedSvcs();

    List<Svc> getConsumedSvcs();

}
