package com.example.schematenancy.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Component
public class TenantInterceptor implements HandlerInterceptor {

    private final String defaultTenant;

    /**
     * This method will be called before the request is passed to the handler method (i.e., the controller method).
     * @param request : represents a request made over the web
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId;
        if (request.getHeader("X-TENANT-ID") != null) {
            tenantId = request.getHeader("X-TENANT-ID");
        } else {
            tenantId = defaultTenant;
        }
        TenantContext.setTenantId(tenantId);
        return true;
    }

    /**
     * This method is called after the handler method (i.e., the controller method) has been executed,
     * but before the view is rendered.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        TenantContext.clear();
    }

    /**
     * This method is called after the handler method (i.e., the controller method) has been executed
     * and the view has been rendered.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // NOOP
    }

}
