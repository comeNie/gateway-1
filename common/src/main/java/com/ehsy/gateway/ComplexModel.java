package com.ehsy.gateway;

import java.util.List;

/**
 * Created by ehsy_it on 2016/6/8.
 */
public class ComplexModel extends SimpleModel {
    private List<ComplexModel> fields;
    private ComplexModel array;

    public ComplexModel getArray() {
        return array;
    }

    public void setArray(ComplexModel array) {
        this.array = array;
    }

    public List<ComplexModel> getFields() {
        return fields;
    }

    public void setFields(List<ComplexModel> fields) {
        this.fields = fields;
    }
}
