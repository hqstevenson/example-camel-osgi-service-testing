package com.pronoia.camel.processor;

import com.pronoia.osgi.service.MyServiceInterface;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorWrapper implements Processor {
    Logger log = LoggerFactory.getLogger(this.getClass());

    Processor delegate;

    @Override
    public void process(Exchange exchange) throws Exception {
        if (null == delegate) {
            log.error( "Processor delegate not set");
        } else {
            delegate.process(exchange);
        }
        return;
    }

    public Processor getDelegate() {
        return delegate;
    }

    public void setDelegate(Processor delegate) {
        this.delegate = delegate;
    }
}
