package com.gexingw.mall.auth.service.infra.support.auth;

import org.springframework.jdbc.core.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 12:17
 */
//@Component
@SuppressWarnings("SqlNoDataSourceInspection")
public class DbOAuth2RegisteredClientRepository extends JdbcRegisteredClientRepository {
//    private static final String COLUMN_NAMES = "id, client_id, client_id_issued_at, client_secret, client_secret_expires_at," +
//            " client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings," +
//            "token_settings,client_type";

    // @formatter:off
    private static final String COLUMN_NAMES = "id, "
            + "client_id, "
            + "client_id_issued_at, "
            + "client_secret, "
            + "client_secret_expires_at, "
            + "client_name, "
            + "client_authentication_methods, "
            + "authorization_grant_types, "
            + "redirect_uris, "
            + "post_logout_redirect_uris, "
            + "scopes, "
            + "client_settings,"
            + "token_settings";
    // @formatter:on

    private static final String TABLE_NAME = "oauth2_registered_client";

    private static final String CLIENT_ID_COUNT_SQL = String.format("SELECT COUNT(*) FROM %s WHERE client_id = ?", TABLE_NAME);
    private static final String CLIENT_SECRET_COUNT_SQL = String.format("SELECT COUNT(*) FROM %s WHERE client_secret = ?", TABLE_NAME);
    private static final String CLIENT_INSERT_SQL = String.format("INSERT INTO %s(%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, COLUMN_NAMES);
    private static final String CLIENT_UPDATE_SQL = String.format("UPDATE %s SET client_name = ?, client_authentication_methods = ?, authorization_grant_types = ?, redirect_uris = ?, scopes = ?, client_settings = ?, token_settings = ? WHERE id = ?", TABLE_NAME);

    private final RowMapper<RegisteredClient> registeredClientRowMapper = new RegisteredClientRowMapper();
    private final Function<RegisteredClient, List<SqlParameterValue>> registeredClientParametersMapper = new RegisteredClientParametersMapper();

    private final JdbcOperations jdbcOperations;

    public DbOAuth2RegisteredClientRepository(JdbcOperations jdbcOperations) {
        super(jdbcOperations);
        this.jdbcOperations = jdbcOperations;
    }

    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        RegisteredClient existingRegisteredClient = this.findBy("id = ?", registeredClient.getId());
        if (existingRegisteredClient != null) {
            this.updateRegisteredClient(registeredClient);
        } else {
            this.insertRegisteredClient(registeredClient);
        }

    }

    private void updateRegisteredClient(RegisteredClient registeredClient) {
        List<SqlParameterValue> parameters = new ArrayList((Collection) this.registeredClientParametersMapper.apply(registeredClient));
        SqlParameterValue id = parameters.remove(0);
        parameters.remove(0);
        parameters.remove(0);
        parameters.remove(0);
        parameters.remove(0);
        parameters.add(id);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(CLIENT_UPDATE_SQL, pss);
    }

    private void insertRegisteredClient(RegisteredClient registeredClient) {
        this.assertUniqueIdentifiers(registeredClient);
        List<SqlParameterValue> parameters = this.registeredClientParametersMapper.apply(registeredClient);
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters.toArray());
        this.jdbcOperations.update(CLIENT_INSERT_SQL, pss);
    }

    private void assertUniqueIdentifiers(RegisteredClient registeredClient) {
        Integer count = this.jdbcOperations.queryForObject(CLIENT_ID_COUNT_SQL, Integer.class, registeredClient.getClientId());
        if (count != null && count > 0) {
            throw new IllegalArgumentException("Registered client must be unique. Found duplicate client identifier: " + registeredClient.getClientId());
        } else {
            count = this.jdbcOperations.queryForObject(CLIENT_SECRET_COUNT_SQL, Integer.class, registeredClient.getClientSecret());
            if (count != null && count > 0) {
                throw new IllegalArgumentException("Registered client must be unique. Found duplicate client secret for identifier: " + registeredClient.getId());
            }
        }
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return findBy("client_id = ?", clientId);
    }

    protected RegisteredClient findBy(String filter, Object... args) {
        //noinspection SqlSourceToSinkFlow
        List<RegisteredClient> result = this.jdbcOperations.query(String.format("SELECT %s FROM %s WHERE ", COLUMN_NAMES, TABLE_NAME) + filter, this.registeredClientRowMapper, args);

        return !result.isEmpty() ? result.get(0) : null;
    }

}
