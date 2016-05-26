package com.pronoia.osgi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServiceWrapper implements MyServiceInterface {
    Logger log = LoggerFactory.getLogger(this.getClass());

    MyServiceInterface delegate;

    @Override
    public String execute(String body) {
        if (null == delegate) {
            log.error( "Service delegate not set");
        } else {
            delegate.execute(body);
        }
        return body;
    }

    public MyServiceInterface getDelegate() {
        return delegate;
    }

    public void setDelegate(MyServiceInterface delegate) {
        this.delegate = delegate;
    }
}
