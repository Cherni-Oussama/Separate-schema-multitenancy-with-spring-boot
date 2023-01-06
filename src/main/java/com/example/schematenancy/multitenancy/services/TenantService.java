package com.example.schematenancy.multitenancy.services;

import com.example.schematenancy.multitenancy.models.Tenant;
import org.springframework.data.repository.query.Param;

public interface TenantService {

    Tenant createTenant(String tenantId, String schema);

    Tenant findByTenantId(@Param("tenantId") String tenantId);
}
