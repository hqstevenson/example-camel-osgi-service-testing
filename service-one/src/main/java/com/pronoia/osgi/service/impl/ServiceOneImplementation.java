package com.pronoia.osgi.service.impl;

import com.pronoia.osgi.service.MyServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceOneImplementation implements MyServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    boolean trace = false;

    String implementation = "default";

    @Override
    public String execute(String body) {
        if ( trace ) {
            Exception tracer = new Exception();
            tracer.fillInStackTrace();
            log.info("Executing { " + implementation  + " } - " + this.getClass().getSimpleName() + ".execute(" + body + ") " + System.identityHashCode(this), tracer);
        } else {
            log.info("Executing { " + implementation  + " } - " +  this.getClass().getSimpleName() + ".execute( " + body + " ) " + System.identityHashCode(this));
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
