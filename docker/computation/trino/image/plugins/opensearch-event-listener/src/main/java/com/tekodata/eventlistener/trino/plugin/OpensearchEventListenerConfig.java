/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tekodata.eventlistener.trino.plugin;

import com.google.common.collect.ImmutableMap;
import io.airlift.configuration.Config;
import io.airlift.configuration.ConfigDescription;
import io.airlift.units.Duration;

import javax.validation.constraints.Min;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpensearchEventListenerConfig
{
    private int retryCount;
    private Duration retryDelay = Duration.valueOf("1s");
    private double backoffBase = 2.0;
    private Duration maxDelay = Duration.valueOf("1m");
    private final EnumSet<OpensearchEventListenerEventType> loggedEvents = EnumSet.noneOf(OpensearchEventListenerEventType.class);
    private String host;
    private int port;
    private String index;
    private int eventBulkSize = 100;
    private long eventBulkTimeoutMs = 1000;
    private boolean tlsEnabled;
    private Map<String, String> httpHeaders = ImmutableMap.of();

    @Config("opensearch-event-listener.log-created")
    @ConfigDescription("Will log io.trino.spi.eventlistener.QueryCreatedEvent")
    public OpensearchEventListenerConfig setLogCreated(boolean logCreated) {
        if (logCreated) {
            loggedEvents.add(OpensearchEventListenerEventType.QUERY_CREATED);
        }
        return this;
    }

    public boolean getLogCreated() {
        return loggedEvents.contains(OpensearchEventListenerEventType.QUERY_CREATED);
    }

    @Config("opensearch-event-listener.log-completed")
    @ConfigDescription("Will log io.trino.spi.eventlistener.QueryCompletedEvent")
    public OpensearchEventListenerConfig setLogCompleted(boolean logCompleted) {
        if (logCompleted) {
            loggedEvents.add(OpensearchEventListenerEventType.QUERY_COMPLETED);
        }
        return this;
    }

    public boolean getLogCompleted() {
        return loggedEvents.contains(OpensearchEventListenerEventType.QUERY_COMPLETED);
    }

    @Config("opensearch-event-listener.log-split")
    @ConfigDescription("Will log io.trino.spi.eventlistener.SplitCompletedEvent")
    public OpensearchEventListenerConfig setLogSplit(boolean logSplit) {
        if (logSplit) {
            loggedEvents.add(OpensearchEventListenerEventType.QUERY_SPLIT);
        }
        return this;
    }

    public boolean getLogSplit() {
        return loggedEvents.contains(OpensearchEventListenerEventType.QUERY_SPLIT);
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    @Config("opensearch-event-listener.connect-http-headers")
    @ConfigDescription("List of custom custom HTTP headers provided as: \"Header-Name-1: header value 1, Header-Value-2: header value 2, ...\" ")
    public OpensearchEventListenerConfig setHttpHeaders(List<String> httpHeaders) {
        try {
            this.httpHeaders = httpHeaders
                    .stream()
                    .collect(Collectors.toUnmodifiableMap(kvs -> kvs.split(":", 2)[0], kvs -> kvs.split(":", 2)[1]));
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Cannot parse http headers from property opensearch-event-listener.connect-http-headers; value provided was %s, " +
                    "expected format is \"Header-Name-1: header value 1, Header-Value-2: header value 2, ...\"", String.join(", ", httpHeaders)), e);
        }
        return this;
    }

    @Config("opensearch-event-listener.connect-retry-count")
    @ConfigDescription("Number of retries on server error")
    public OpensearchEventListenerConfig setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    @Min(value = 0, message = "Retry count must be a positive value. Use 0 or leave empty for no retries.")
    public int getRetryCount() {
        return retryCount;
    }

    @Config("opensearch-event-listener.connect-retry-delay")
    @ConfigDescription("Delay in seconds between retries")
    public OpensearchEventListenerConfig setRetryDelay(Duration retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public Duration getRetryDelay()
    {
        return this.retryDelay;
    }

    @Config("opensearch-event-listener.connect-backoff-base")
    @ConfigDescription("Base used for exponential backoff when retying on server error. " +
            "Formula is attemptDelay = retryDelay * backoffBase ^ attemptCount. " +
            "Attempt counting starts from 0. Leave empty or set to 1 to disable.")
    public OpensearchEventListenerConfig setBackoffBase(double backoffBase) {
        this.backoffBase = backoffBase;
        return this;
    }

    @Min(value = 1, message = "Exponential base must be a positive, non-zero integer.")
    public double getBackoffBase() {
        return this.backoffBase;
    }

    @Config("opensearch-event-listener.connect-max-delay")
    @ConfigDescription("Maximum delay between retries. This should be used with exponential backoff.")
    public OpensearchEventListenerConfig setMaxDelay(Duration maxDelay) {
        this.maxDelay = maxDelay;
        return this;
    }

    public String getHost() {
        return this.host;
    }

    @Config("opensearch-event-listener.opensearch-host")
    @ConfigDescription("Opensearch host.")
    public OpensearchEventListenerConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return this.port;
    }

    @Config("opensearch-event-listener.opensearch-port")
    @ConfigDescription("Opensearch port.")
    public OpensearchEventListenerConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public String getOpensearchIndex() {
        return this.index;
    }

    @Config("opensearch-event-listener.opensearch-index")
    @ConfigDescription("Opensearch host.")
    public OpensearchEventListenerConfig setOpensearchIndex(String index) {
        this.index = index;
        return this;
    }

    public int getOpensearchBulkSize() {
        return this.eventBulkSize;
    }

    @Config("opensearch-event-listener.opensearch-bulk-size")
    @ConfigDescription("Opensearch host.")
    public OpensearchEventListenerConfig setOpensearchBulkSize(int eventBulkSize) {
        this.eventBulkSize = eventBulkSize;
        return this;
    }

    public long getOpensearchBulkTimeoutMs() {
        return this.eventBulkTimeoutMs;
    }

    @Config("opensearch-event-listener.opensearch-bulk-timeout")
    @ConfigDescription("Opensearch host.")
    public OpensearchEventListenerConfig setOpensearchBulkTimeout(long eventBulkTimeoutMs) {
        this.eventBulkTimeoutMs = eventBulkTimeoutMs;
        return this;
    }

    public boolean isOpensearchTlsEnabled() {
        return this.tlsEnabled;
    }

    @Config("opensearch-event-listener.opensearch-tls-enabled")
    @ConfigDescription("Opensearch tls is enabled or not.")
    public OpensearchEventListenerConfig setOpensearchTlsEnabled(boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
        return this;
    }

    public Duration getMaxDelay() {
        return this.maxDelay;
    }
}
