package org.mifos.connector.mpesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * The Safaricom endpoints this connector calls, and the callback address it asks Safaricom to call back on.
 *
 * <p>
 * Named {@code MpesaApiProperties} rather than {@code MpesaProperties} because {@code MpesaProps} already exists and binds a different
 * prefix ({@code accounts}), which is confusing enough without a third similar name.
 * </p>
 */
@ConfigurationProperties(prefix = "mpesa")
public record MpesaApiProperties(@DefaultValue("2") int maxRetryCount, @DefaultValue Api api,
        @DefaultValue Local local) {

    /** {@code timeout} is applied to every outgoing Safaricom call, in milliseconds. */
    public record Api(@DefaultValue("60000") int timeout, String lipana, String transactionStatus) {}

    public record Local(String host, String transactionCallback) {}
}
