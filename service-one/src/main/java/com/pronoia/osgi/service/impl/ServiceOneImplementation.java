package com.pronoia.osgi.service.impl;

import com.pronoia.osgi.service.MyServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceOneImplementation implements MyServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    String implementation = "default";

    @Override
    public String execute(String body) {
        log.info("Executing {} - {}", this.getClass().getSimpleName(), implementation);
        return body;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }
}
