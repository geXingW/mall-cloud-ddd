package com.gexingw.mall.auth.service.infra.support.auth.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 11:53
 */
public class ServletRequestJsonParamsWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> params = new HashMap<>();

    public ServletRequestJsonParamsWrapper(HttpServletRequest request) {
        super(request);
        params.putAll(request.getParameterMap());
    }

    public void setParams(Map<String, Object> params) {
        params.forEach(this::setParam);
    }

    public void setParam(String key, Object value) {
        if (value == null) {
            return;
        }

        if (value instanceof String[]) {
            params.put(key, (String[]) value);
        } else if (value instanceof String) {
            params.put(key, new String[]{(String) value});
        } else {
            params.put(key, new String[]{String.valueOf(value)});
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> nameSet = params.keySet();

        return new Vector<>(nameSet).elements();
    }

    @Override
    public String getParameter(String name) {
        String[] paramValue = params.get(name);
        if (paramValue == null || paramValue.length == 0) {
            return null;
        }

        return paramValue[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

}
