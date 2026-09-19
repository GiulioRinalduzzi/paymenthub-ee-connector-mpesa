package org.mifos.connector.mpesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/** When enabled, the workers pretend Safaricom answered instead of calling it. Used for demos and local runs. */
@ConfigurationProperties(prefix = "skip")
public record SkipProperties(@DefaultValue("false") boolean enabled) {}
