package com.moozi.mooziweb.common.initialize;

import com.moozi.mooziweb.thread.channel.RequestChannel;
import com.moozi.mooziweb.thread.worker.WorkerThread;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class PointThreadInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        RequestChannel requestChannel = new RequestChannel(10);
        //todo#14-1 servletContext에 requestChannel을 등록합니다.
        ctx.setAttribute("requestChannel", requestChannel);

        //todo#14-2 WorkerThread 시작합니다.
        new WorkerThread(requestChannel).start();

    }
}
