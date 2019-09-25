package com.rongdu.loans.loan.option.rong360Model;

import java.util.HashMap;
import java.util.Map;


public class OpenapiRequest{

    private Map<String, Object> params = new HashMap<String, Object>();

    public OpenapiRequest putParam(String param, Object value) {
        this.params.put(param, value);
        return this;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

}