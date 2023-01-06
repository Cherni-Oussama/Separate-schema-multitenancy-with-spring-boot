package com.example.schematenancy.multitenancy.config.tenant.hibernate;

import com.example.schematenancy.exception.TenantNotFoundException;
import com.example.schematenancy.multitenancy.models.Tenant;
import com.example.schematenancy.multitenancy.repositories.TenantRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final transient DataSource datasource;
    private final transient TenantRepository tenantRepository2;

    @Value("${multitenancy.master.schema:#{null}}")
    private String masterSchema;
    @Value("${multitenancy.schema-cache.maximumSize:1000}")
    private Long maximumSize;
    @Value("${multitenancy.schema-cache.expireAfterAccess:10}")
    private Integer expireAfterAccess;

    private transient LoadingCache<String, String> tenantSchemas;

    @PostConstruct
    private void createCache() {
        Caffeine<Object, Object> tenantsCacheBuilder = Caffeine.newBuilder();
        if (maximumSize != null) {
            tenantsCacheBuilder.maximumSize(maximumSize);
        }
        if (expireAfterAccess != null) {
            tenantsCacheBuilder.expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES);
        }
        tenantSchemas = tenantsCacheBuilder.build(
                tenantId -> {
                    if(! tenantId.equals("localhost")){
                        Tenant tenant = tenantRepository2.findByTenantId(tenantId)
                                .orElseThrow(() -> new TenantNotFoundException("TenantId not found : " + tenantId,null, null, HttpStatus.NOT_ACCEPTABLE));
                        return tenant.getSchema();
                    }
                    return "public";
                });
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        log.info("Get connection for tenant {}", tenantIdentifier);
        String tenantSchema = tenantSchemas.get(tenantIdentifier);
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantSchema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        log.info("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(masterSchema);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType) ) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
}
