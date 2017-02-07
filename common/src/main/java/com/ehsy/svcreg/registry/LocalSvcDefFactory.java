package com.ehsy.svcreg.registry;

import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by zhuangmg on 6/7/16.
 */
public class LocalSvcDefFactory implements SvcDefFactory {

    private List<Svc> providedSvcs;
    private List<Svc> consumedSvcs;

    public void setProvidedSvcs(List<Svc> providedSvcs) {
        this.providedSvcs = providedSvcs;
    }
    public void setConsumedSvcs(List<Svc> consumedSvcs) {
        this.consumedSvcs = consumedSvcs;
    }

    @Override
    public List<Svc> getProvidedSvcs() {
        return providedSvcs;
    }

    @Override
    public List<Svc> getConsumedSvcs() {
        return consumedSvcs;
    }
}
