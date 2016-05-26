package com.pronoia.osgi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractServiceImplementation implements MyServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    boolean trace = false;

    String implementation = "default";
    long startTicks = System.currentTimeMillis();

    @Override
    public String execute(String body) {
        log.info("Executing [ {} ] - {}.execute( {} ) {}. Superclass {}",
                startTicks, this.getClass().getSimpleName(), body, System.identityHashCode(this), this.getClass().getSuperclass().getName());

        if ( trace ) {
            Exception tracer = new Exception();
            tracer.fillInStackTrace();
            log.info("Call Trace:", tracer);
        }
        return body;
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
