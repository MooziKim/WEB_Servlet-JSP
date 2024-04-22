package com.moozi.mooziweb.thread.channel;


import com.moozi.mooziweb.thread.request.ChannelRequest;

import java.util.LinkedList;
import java.util.Queue;

public class RequestChannel {
    private final Queue<ChannelRequest> queue;
    private final int queueMaxSize;

    public RequestChannel(int queueMaxSize) {
        this.queueMaxSize = queueMaxSize;
        this.queue = new LinkedList<>();
    }

    public synchronized ChannelRequest getRequest() throws InterruptedException {

        //todo#14-3 queue가 비어있다면 대기합니다.
        while(queue.isEmpty()) {
            wait();
        }

        //todo#14-4 queue에서 request 반환합니다.
        ChannelRequest request = queue.poll();
        return request;
    }

    public synchronized void addRequest(ChannelRequest request) throws InterruptedException {

        //todo#14-5 queue가 가득차있다면 요청이 소비될 때까지 대기합니다.
        while(queue.size() == queueMaxSize) {
            wait();
        }

        //todo#14-6 queue에 요청을 추가하고 대기하고 있는 스레드를 깨웁니다
        queue.add(request);
        notifyAll();
    }

}
