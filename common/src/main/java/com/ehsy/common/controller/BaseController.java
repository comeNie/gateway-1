package com.ehsy.common.controller;

import org.springframework.http.HttpHeaders;

/**
 * 基础控制器
 */
public abstract class BaseController {
    protected static HttpHeaders headers = new HttpHeaders();
    static {
        headers.add("Content-Type", "application/json; charset=utf-8");
    }
}
