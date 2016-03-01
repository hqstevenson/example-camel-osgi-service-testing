package com.pronoia.camel;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.scr.AbstractCamelRunner;
import com.pronoia.camel.internal.ScrRouteBuilder;
import com.pronoia.osgi.service.MyServiceInterface;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.spi.ComponentResolver;
import org.apache.felix.scr.annotations.*;

@Component(label = ScrRunner.COMPONENT_LABEL, description = ScrRunner.COMPONENT_DESCRIPTION, immediate = true, metatype = true,
        policy = ConfigurationPolicy.REQUIRE
    )
@Properties({
    @Property(name = "camelContextId", value = "scr-test"),
    @Property(name = "camelRouteId", value = "scr-route"),
    @Property(name = "active", value = "true"),
    @Property(name = "from", value = "timer://scr-test?period=5000"),
    @Property(name = "to", value = "log://scr-test")
})
@References({
    @Reference(name = "camelComponent",referenceInterface = ComponentResolver.class,
        cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
        policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent"),
    @Reference( name = "serviceInterface", referenceInterface = MyServiceInterface.class, target = "(implementation=stub)",
        bind = "setServiceInterface", unbind = "unsetServiceInterface")
})
public class ScrRunner extends AbstractCamelRunner {

    public static final String COMPONENT_LABEL = "com.pronoia.camel.ScrRunner";
    public static final String COMPONENT_DESCRIPTION = "This is the description for scr.";

    MyServiceInterface serviceInterface;

    @Override
    protected List<RoutesBuilder>getRouteBuilders() {
        List<RoutesBuilder>routesBuilders = new ArrayList<>();
        ScrRouteBuilder builder = new ScrRouteBuilder();
        builder.setServiceInterface( serviceInterface );
        routesBuilders.add(builder);
        return routesBuilders;
    }

    void setServiceInterface( MyServiceInterface svc ) {
        log.info( "Setting service interface...");
        serviceInterface = svc;
    }

    void unsetServiceInterface( MyServiceInterface svc ) {

    }
}
