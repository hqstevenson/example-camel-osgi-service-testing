package com.pronoia.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProcessorImplementation implements Processor {
    Logger log = LoggerFactory.getLogger(this.getClass());

    boolean trace = false;

    String implementation = "default";
    long startTicks = System.currentTimeMillis();

    @Override
    public void process(Exchange exchange) {
        log.info("Executing [ {} ] - {}.process() {}. Superclass {}",
                startTicks, this.getClass().getSimpleName(), System.identityHashCode(this), this.getClass().getSuperclass().getName());

        if ( trace ) {
            Exception tracer = new Exception();
            tracer.fillInStackTrace();
            log.info("Call Trace:", tracer);
        }
        return;
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

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

}
