package com.tekodata.eventlistener.trino.plugin;

import io.trino.spi.eventlistener.QueryCompletedEvent;
import io.trino.spi.eventlistener.QueryCreatedEvent;
import io.trino.spi.eventlistener.SplitCompletedEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class OpensearchEventSender {

	private long oldestEventTs = 0;
	private int maxBulkSize;
	private int bulkTimeout;

	private ConcurrentLinkedQueue<Object> eventQueue;

	public OpensearchEventSender(int maxBulkSize, int bulkTimeout) {
		this.maxBulkSize = maxBulkSize;
		this.bulkTimeout = bulkTimeout;
	}

	public int bufferSize() {
		return eventQueue.size();
	}

	public void cockEvent(Object event) {
		if(event instanceof QueryCreatedEvent ||
			event instanceof QueryCompletedEvent ||
			event instanceof SplitCompletedEvent
		) {
			eventQueue.add(event);
		}
	}

	private boolean shouldCommit() {
		long now = System.currentTimeMillis();
		boolean timeout = now - oldestEventTs > this.bulkTimeout;
		boolean bulkSizeExceeded = eventQueue.size() >= maxBulkSize;

		return timeout || bulkSizeExceeded;
	}

}
