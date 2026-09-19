package org.mifos.connector.mpesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Where the Zeebe broker is, how many threads talk to it, and the two timers this connector uses.
 *
 * <p>
 * {@code zeebe.client.evenly-allocated-max-jobs} is deliberately not here: it is a SpEL expression in application.yml
 * ({@code "#{${zeebe.client.max-execution-threads} / ${zeebe.client.number-of-workers}}"}) and only {@code @Value} evaluates SpEL. Same for
 * {@code zeebe.client.number-of-workers}, which exists only to feed that expression.
 * </p>
 */
@ConfigurationProperties(prefix = "zeebe")
public record ZeebeProperties(@DefaultValue Broker broker, @DefaultValue Client client,
        @DefaultValue InitTransfer initTransfer) {

    public record Broker(String contactpoint) {}

    /** {@code ttl} is how long a published Zeebe message stays correlatable, in milliseconds. */
    public record Client(@DefaultValue("100") int maxExecutionThreads, @DefaultValue("30000") int ttl) {}

    /** How long the init-transfer worker waits before calling Safaricom, in seconds. */
    public record InitTransfer(@DefaultValue("5") int waitTimer) {}
}
