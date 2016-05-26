package com.pronoia.camel.builder;

import com.pronoia.osgi.service.MyServiceInterface;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanConsumerBuilder extends RouteBuilder{
    Logger log = LoggerFactory.getLogger(this.getClass());

    boolean trace = false;

    boolean autoStartup = true;
    boolean cacheBean = true;
    String beanId = "osgi-service";
    String timerName = "service-timer";

    public MyServiceInterface beanInstance;

    public BeanConsumerBuilder() {
    }

    @Override
    public void configure() throws Exception {
        fromF( "timer://%s?period=%d&fixedRate=%b", timerName, 5000, true)
            .autoStartup(autoStartup)
            .routeId( "service-route")
            .setBody().constant( "Dummy Value")
            .log( "Calling " + beanId )
            // .bean( beanInstance, "execute", false)
            .toF( "bean://%s?cache=%b&method=%s", beanId, cacheBean, "execute")
            // .to( "mock://result")
            ;

    }

    public boolean isTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        if (trace) {
            log.warn("********** ACTIVATING TRACE **********");
        } else {
            log.warn("********** DE-ACTIVATING TRACE **********");
        }

        this.trace = trace;
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public boolean isCacheBean() {
        return cacheBean;
    }

    public void setCacheBean(boolean cacheBean) {
        this.cacheBean = cacheBean;
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

    public MyServiceInterface getBeanInstance() {
        return beanInstance;
    }

    public void setBeanInstance(MyServiceInterface beanInstance) {
        if (trace) {
            Exception ex = new Exception();
            ex.fillInStackTrace();
            log.info("********** Setting beanInstance of type " + beanInstance.getClass().getName() + ": ", ex);
        } else {
            log.info("********** Setting beanInstanceof type" + beanInstance.getClass().getName());
        }

        this.beanInstance = beanInstance;
    }

    public static void main( String[] args ) {
        System.out.println( "Hello");

    }
}
