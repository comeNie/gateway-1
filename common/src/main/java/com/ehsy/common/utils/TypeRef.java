package com.ehsy.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhuangmg on 6/30/16.
 */
public class TypeRef<T> {
    private final Type type;

    protected TypeRef(){
        Type superClass = getClass().getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

    public final static Type LIST_STRING = new TypeRef<List<String>>() {}.getType();
}
