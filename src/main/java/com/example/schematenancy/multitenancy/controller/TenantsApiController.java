package com.example.schematenancy.multitenancy.controller;

import com.example.schematenancy.multitenancy.models.Tenant;
import com.example.schematenancy.multitenancy.services.TenantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Tenant")
public class TenantsApiController {

    private final TenantService tenantManagementService;

    @PostMapping("/tenants")
    public ResponseEntity<Tenant> createTenant(@RequestParam String tenantId, @RequestParam String schema) {
        return new ResponseEntity<>(tenantManagementService.createTenant(tenantId, schema),HttpStatus.OK);
    }
}
