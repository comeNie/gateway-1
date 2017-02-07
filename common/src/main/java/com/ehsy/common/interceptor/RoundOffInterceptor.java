package com.ehsy.common.interceptor;

import com.ehsy.common.swap.SwapAreaUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhuangmg on 6/30/16.
 */
public class RoundOffInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //清空swaparea
        SwapAreaUtils.releaseSwapArea();

        super.afterCompletion(request, response, handler, ex);
    }
}
