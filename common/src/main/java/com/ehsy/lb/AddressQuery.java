package com.ehsy.lb;

import com.ehsy.svcreg.model.Svc;

import java.util.List;

/**
 * Created by zhuangmg on 6/12/16.
 */
public interface AddressQuery {

    Svc select(List<Svc> svcs);

}
