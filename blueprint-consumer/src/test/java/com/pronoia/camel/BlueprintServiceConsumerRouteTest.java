package com.pronoia.camel;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.pronoia.osgi.service.MyServiceInterface;
import com.pronoia.osgi.service.stub.StubServiceImplementation;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;

import org.junit.Test;

public class BlueprintServiceConsumerRouteTest extends CamelBlueprintTestSupport {
	
    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint-context.xml";
    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        services.put( MyServiceInterface.class.getName(), asService(new StubServiceImplementation(), "implementation", "bp-external"));
    }

    @Test
    public void testBlueprintServiceConsumer() throws Exception {
        getMockEndpoint("mock:result").expectedMinimumMessageCount(1);

        assertMockEndpointsSatisfied( 10, TimeUnit.SECONDS);
    }

}
