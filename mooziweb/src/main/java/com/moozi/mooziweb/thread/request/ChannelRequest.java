package com.moozi.mooziweb.thread.request;

import java.util.concurrent.atomic.AtomicLong;

public abstract class ChannelRequest {
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private final long requestId;

    protected ChannelRequest(){
        requestId = ID_GENERATOR.incrementAndGet();
    }
    public abstract void execute();

    @Override
    public String toString() {
        return "ChannelRequest{" +
                "requestId=" + requestId +
                '}';
    }
}