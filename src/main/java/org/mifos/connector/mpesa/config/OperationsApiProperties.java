package org.mifos.connector.mpesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The operations API this connector asks whether a Safaricom error code is worth retrying.
 *
 * <p>
 * Named {@code OperationsApiProperties} because {@code camel.config.OperationsProperties} already exists and holds query-parameter
 * constants, not configuration.
 * </p>
 */
@ConfigurationProperties(prefix = "operations")
public record OperationsApiProperties(String host, String baseUrl, String filterPath) {

    /** The full URL the error-code routes call. */
    public String filterUrl() {
        return host + baseUrl + filterPath;
    }
}
