package com.pronoia.camel.builder;

import java.util.concurrent.TimeUnit;

import com.pronoia.osgi.service.stub.StubServiceImplementation;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class BeanConsumerBuilderTest extends CamelTestSupport{

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();

        registry.bind("osgi-service", new StubServiceImplementation());

        return registry;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        BeanConsumerBuilder builder = new BeanConsumerBuilder();

        builder.setBeanInstance( new StubServiceImplementation() );
        return builder;
    }

    @Test
    public void testRouteBuilder() throws Exception {
        NotifyBuilder notify = new NotifyBuilder(context()).whenDone(3).create();

        assertTrue( "Should have completed", notify.matches(15, TimeUnit.SECONDS));
    }

}