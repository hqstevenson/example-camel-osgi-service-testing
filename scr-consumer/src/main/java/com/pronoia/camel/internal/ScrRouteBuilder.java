package com.pronoia.camel.internal;

import com.pronoia.osgi.service.MyServiceInterface;

import org.apache.camel.builder.RouteBuilder;
import org.apache.felix.scr.annotations.Reference;

public class ScrRouteBuilder extends RouteBuilder {

    Object myPropValue;

    // Configured fields
    private String camelRouteId;

    Object serviceInterface;

    @Override
	public void configure() throws Exception {
        if ( null == serviceInterface ) {
            System.err.println( "Service Inteface not injected...");
            serviceInterface = new MyServiceInterface() {
                @Override
                public String execute(String body) {
                    System.err.println( "Anonymous version called...");
                    return body;
                }
            };
        }
        from("{{from}}")
            .routeId(camelRouteId)
            .bean(serviceInterface, "execute")
            .to("{{to}}");
	}

    public void setServiceInterface(Object serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
