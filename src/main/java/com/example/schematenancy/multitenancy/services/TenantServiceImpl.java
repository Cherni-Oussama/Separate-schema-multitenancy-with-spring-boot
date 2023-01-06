package com.example.schematenancy.multitenancy.services;

import com.example.schematenancy.exception.TenantCreationException;
import com.example.schematenancy.exception.TenantNotFoundException;
import com.example.schematenancy.multitenancy.models.Tenant;
import com.example.schematenancy.multitenancy.repositories.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Service;

import static com.example.schematenancy.utils.Constants.SQL_SCHEMA_POPULATE;


@RequiredArgsConstructor
@Service
public class TenantServiceImpl implements TenantService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantRepository tenantRepository;
    private static final String VALID_SCHEMA_NAME_REGEXP = "[A-Za-z0-9_]*";

    @Override
    public Tenant createTenant(String tenantId, String schema) {

        if (!schema.matches(VALID_SCHEMA_NAME_REGEXP)) {
            throw new TenantCreationException("Invalid schema name: " + schema,null, null, HttpStatus.NOT_ACCEPTABLE);
        }
        createSchema(schema);
        populateSchema(schema);
        Tenant tenant = Tenant.builder()
                .tenantId(tenantId)
                .schema(schema)
                .build();
        tenantRepository.save(tenant);
        return tenant;
    }

    @Override
    public Tenant findByTenantId(String tenantId) {
        return tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("TenantId not found : " + tenantId,null, null, HttpStatus.NOT_ACCEPTABLE));
    }

    private void createSchema(String schema) {
        try{
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA " + schema));
        } catch (BadSqlGrammarException e){
            throw new TenantCreationException("Tenant already exists",null,null,HttpStatus.CONFLICT);
        }
    }

    private void populateSchema(String schema) {
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute(java.text.MessageFormat.format(SQL_SCHEMA_POPULATE, schema)));
    }


}
