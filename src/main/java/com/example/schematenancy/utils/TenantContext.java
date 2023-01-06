package com.example.schematenancy.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This Class is used to manage the tenantId.
 * Tenant ID is stored in a thread-local variable so that it can be accessed by other components in the application.
 * A thread-local variable is a variable that is stored in a separate instance for each thread,
 * so that different threads do not overwrite each other's values.
 */
@Slf4j
@RequiredArgsConstructor(access= AccessLevel.PRIVATE)
public final class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        log.info("Setting tenantId to " + tenantId);
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.remove();
    }
}