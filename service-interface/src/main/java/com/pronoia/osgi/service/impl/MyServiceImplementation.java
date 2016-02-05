package com.pronoia.osgi.service.impl;

import com.pronoia.osgi.service.MyServiceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServiceImplementation implements MyServiceInterface{
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public String execute(String body) {
        log.info( "Executing {}", this.getClass().getSimpleName());
        return body;
    }
}
