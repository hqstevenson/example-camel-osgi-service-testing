package com.pronoia.camel.builder;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorConsumerBuilder extends RouteBuilder{
    Logger log = LoggerFactory.getLogger(this.getClass());

    boolean trace = false;

    boolean autoStartup = true;
    String timerName = "processor-timer";

    Processor processor;

    public ProcessorConsumerBuilder() {
    }

    @Override
    public void configure() throws Exception {
        fromF( "timer://%s?period=%d&fixedRate=%b", timerName, 5000, true)
            .autoStartup(autoStartup)
            .routeId( "processor-route")
            .setBody().constant( "Dummy Value")
            .log( "Calling processor" )
            .process( processor )
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

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        if (trace) {
            Exception ex = new Exception();
            ex.fillInStackTrace();
            log.info("********** Setting processor of type " + processor.getClass().getName() + ": ", ex);
        } else {
            log.info("********** Setting processor type" + processor.getClass().getName());
        }

        this.processor = processor;
    }

    public static void main( String[] args ) {
        System.out.println( "Hello");

    }
}
