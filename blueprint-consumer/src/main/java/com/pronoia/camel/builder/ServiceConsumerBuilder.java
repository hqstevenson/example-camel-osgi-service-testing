package com.pronoia.camel.builder;

import com.pronoia.osgi.service.MyServiceInterface;

import org.apache.camel.builder.RouteBuilder;

public class ServiceConsumerBuilder extends RouteBuilder{
    String beanId = "osgi-service";
    String timerName = "default-timer";

    @Override
    public void configure() throws Exception {
        fromF( "timer://%s", timerName)
            .routeId( "java-route")
            .setBody().constant( "Dummy Value")
            .beanRef( beanId, "execute")
            .to( "mock://result")
            ;

    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }
}
