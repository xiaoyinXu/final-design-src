package com.xxywebsite.mynote.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    private Map<String, String> map;
    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    public MyHttpServletRequestWrapper(HttpServletRequest request, Map<String, String> map) {
        super(request);
        this.map = map;
    }

    @Override
    public String getParameter(String name) {
        return map.get(name);
    }

}
