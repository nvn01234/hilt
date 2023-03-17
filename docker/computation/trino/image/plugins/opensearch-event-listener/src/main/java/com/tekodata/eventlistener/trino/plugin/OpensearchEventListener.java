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
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import io.airlift.http.client.BodyGenerator;
import io.airlift.http.client.Request;
import io.airlift.log.Logger;
import io.airlift.units.Duration;
import io.trino.spi.eventlistener.EventListener;
import io.trino.spi.eventlistener.QueryCompletedEvent;
import io.trino.spi.eventlistener.QueryCreatedEvent;
import io.trino.spi.eventlistener.SplitCompletedEvent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Verify.verify;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static io.airlift.http.client.Request.Builder.preparePost;
import static io.airlift.http.client.StatusResponseHandler.StatusResponse;
import static java.util.Objects.requireNonNull;

/**
 * Implement an EventListener that send events, serialized as JSON, to a ingest server.
 *
 * For configuration see {@link io.airlift.http.client.HttpClientConfig}, prefixed with "http-event-listener"
 */
public class OpensearchEventListener implements EventListener {
    private final Logger log = Logger.get(OpensearchEventListener.class);

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final OpensearchEventSender eventSender;

    private final boolean logCreated;
    private final boolean logCompleted;
    private final boolean logSplit;
    private final int retryCount;
    private final Duration retryDelay;
    private final Duration maxDelay;
    private final double backoffBase;
    private final Map<String, String> httpHeaders;

    private final URI ingestUri;

    @Inject
    public OpensearchEventListener(OpensearchEventListenerConfig config)
    {
        requireNonNull(config, "Opensearch event listener config is null");

        this.logCreated = config.getLogCreated();
        this.logCompleted = config.getLogCompleted();
        this.logSplit = config.getLogSplit();
        this.retryCount = config.getRetryCount();
        this.retryDelay = config.getRetryDelay();
        this.maxDelay = config.getMaxDelay();
        this.backoffBase = config.getBackoffBase();
        this.httpHeaders = ImmutableMap.copyOf(config.getHttpHeaders());

        String protocol = config.isOpensearchTlsEnabled() ? "https" : "http";
        String host = config.getHost();
        int port = config.getPort();
        String index = config.getOpensearchIndex();
        String opensearchUrl = String.format("%s://%s:%d/%s/_bulk",
                protocol, host, port, index);
        try {
            ingestUri = new URI(opensearchUrl);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(String.format("Opensearch URI %s for event listener is not valid", opensearchUrl), e);
        }
    }

    @Override
    public void queryCreated(QueryCreatedEvent queryCreatedEvent) {
        if (logCreated) {

        }
    }

    @Override
    public void queryCompleted(QueryCompletedEvent queryCompletedEvent) {
        if (logCompleted) {

        }
    }

    @Override
    public void splitCompleted(SplitCompletedEvent splitCompletedEvent) {
        if (logSplit) {

        }
    }

    private void sendLog(BodyGenerator eventBodyGenerator, String queryId) {
        Request request = preparePost()
                .addHeaders(Multimaps.forMap(httpHeaders))
                .addHeader(CONTENT_TYPE, JSON_UTF_8.toString())
                .setUri(ingestUri)
                .setBodyGenerator(eventBodyGenerator)
                .build();

        attemptToSend(request, 0, Duration.valueOf("0s"), queryId);
    }

    private <E> void attemptToSend(E event, int attempt, Duration delay, String queryId) {
        this.eventSender.cockEvent(event);
    }

    private boolean shouldRetry(StatusResponse response) {
        int statusCode = response.getStatusCode();

        // 1XX Information, requests can't be split
        if (statusCode < 200) {
            return false;
        }
        // 2XX - OK
        if (200 <= statusCode && statusCode < 300) {
            return false;
        }
        // 3XX Redirects, not following redirects
        if (300 <= statusCode && statusCode <= 400) {
            return false;
        }
        // 4XX - client error, no retry except 408 Request Timeout and 429 Too Many Requests
        if (400 <= statusCode && statusCode < 500 && statusCode != 408 && statusCode != 429) {
            return false;
        }

        return true;
    }

    private Duration nextDelay(Duration delay) {
        if (delay.compareTo(Duration.valueOf("0s")) == 0) {
            return retryDelay;
        }

        Duration newDuration = Duration.succinctDuration(delay.getValue(TimeUnit.SECONDS) * backoffBase, TimeUnit.SECONDS);
        if (newDuration.compareTo(maxDelay) > 0) {
            return maxDelay;
        }
        return newDuration;
    }
}
