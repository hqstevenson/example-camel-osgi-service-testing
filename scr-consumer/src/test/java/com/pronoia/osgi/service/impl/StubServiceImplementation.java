package com.pronoia.osgi.service.impl;

import com.pronoia.osgi.service.MyServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StubServiceImplementation implements MyServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public StubServiceImplementation() {
        log.info( "Creating ....");
    }

    @Override
    public String execute(String body) {
        log.info("Executing Stub {}", this.getClass().getSimpleName());
        return body;
    }

}
