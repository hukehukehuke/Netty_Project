package com.com.gupao.vip.mvc.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GPApplicationContext {

    private static final String LOCATION = "contextConfigLocation";
    private Map<String,Object>  instanceMapping = new ConcurrentHashMap<>();

    public Object getBean(){
        return null;
    }
    public Map<String,Object> getInstanceMapping(){
        return instanceMapping;
    }
}
