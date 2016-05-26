package com.pronoia.osgi.blueprint;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import com.pronoia.camel.builder.BeanConsumerBuilder;
import com.pronoia.osgi.service.MyServiceInterface;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlueprintConsumer {
    Logger log = LoggerFactory.getLogger(this.getClass());

    boolean trace = false;

    BundleContext context;
    BlueprintContainer container;

    MyServiceInterface injectedService;
    MyServiceInterface alternateService;
    MyServiceInterface discoveredService;
    long delay = 1000;
    long period = 5000;

    Timer timer;
    BlueprintConsumerTimerTask task;

    public BlueprintContainer getContainer() {
        return container;
    }

    public void setContainer(BlueprintContainer container) {
        this.container = container;
    }

    public BundleContext getContext() {
        return context;
    }

    public void setContext(BundleContext context) {
        this.context = context;
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

    public MyServiceInterface getService() {
        return injectedService;
    }

    public void setService(MyServiceInterface service) {
        if (trace ) {
            Exception ex = new Exception();
            ex.fillInStackTrace();
            log.info("********** Setting service reference of type " + service.getClass().getName() + ": ", ex);
        } else {
            log.info("********** Setting service reference of type " + service.getClass().getName());
        }

        this.injectedService = service;
    }

    public MyServiceInterface getAlternate() {
        return alternateService;
    }

    public void setAlternate(MyServiceInterface service) {
        if (trace ) {
            Exception ex = new Exception();
            ex.fillInStackTrace();
            log.info("********** Setting service reference of type " + service.getClass().getName() + ": ", ex);
        } else {
            log.info("********** Setting service reference of type " + service.getClass().getName());
        }

        this.alternateService = service;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void start() {
        // discoverServiceFromBundleContext();
        // discoverServiceFromBlueprintContainer();
        timer = new Timer( "Blueprint Consumer Timer");
        task = new BlueprintConsumerTimerTask();
        timer.schedule( task, delay, period);

    }

    public void stop() {
        task.cancel();
        timer.purge();
        timer.cancel();
    }

    private void discoverServiceFromBundleContext() {
        try {
            Collection<ServiceReference<MyServiceInterface>> serviceReferences = context.getServiceReferences(MyServiceInterface.class, "(implementation=bp-external)");
            log.info("********** Found {} Services **********", serviceReferences.size());
            ServiceReference<MyServiceInterface> ref = serviceReferences.iterator().next();
            if (null != ref) {
                discoveredService = context.getService(ref);
            }
            /*
            for ( ServiceReference<MyServiceInterface> ref: serviceReferences ) {
                MyServiceInterface instance = context.getService(ref);
                log.info( "Calling instance of {} from loop in {}", instance.getClass().getName(), this.getClass().getName());
                instance.execute( "Calling from loop");
                context.ungetService(ref);
            }
            if (serviceReferences.size() > 0) {
                Object[] objects = serviceReferences.toArray();
            }
            */
        } catch (InvalidSyntaxException e) {
            log.error( "Failed to lookup service", e);
        }

    }

    private void discoverServiceFromBlueprintContainer() {
        discoveredService = (MyServiceInterface) container.getComponentInstance("osgi-service");
    }

    class BlueprintConsumerTimerTask extends TimerTask {

        @Override
        public void run() {
            log.info("********** Begin calling service **********");
            if (null != injectedService) {
                log.debug("Calling injected instance of {} from {}", injectedService.getClass().getName(), this.getClass().getSimpleName());
                injectedService.execute("Calling injected service {" + injectedService.getClass().getName() + " " + System.identityHashCode(injectedService) + "} from blueprint");
            }
            if (null != alternateService) {
                log.debug("Calling injected instance of {} from {}", alternateService.getClass().getName(), this.getClass().getSimpleName());
                alternateService.execute("Calling injected alternate service {" + alternateService.getClass().getName() + " " + System.identityHashCode(alternateService) + "} from blueprint");
            }
            if (null != discoveredService) {
                log.debug("Calling discovered instance of {} from {}", discoveredService.getClass().getName(), this.getClass().getSimpleName());
                discoveredService.execute("Calling discovered service {" + discoveredService.getClass().getName() + " " + System.identityHashCode(discoveredService) + "} from blueprint");
            }
            /*
            callServiceFromBlueprintContainer();
            callServicesFromBundleContext();
            */
            log.info("********** Done calling service **********");
        }

        private void callServiceFromBlueprintContainer() {
            MyServiceInterface tmpDiscoveredService = (MyServiceInterface) container.getComponentInstance("osgi-service");
            tmpDiscoveredService.execute("Calling service from blueprint container {" + tmpDiscoveredService.getClass().getName() + " " + System.identityHashCode(tmpDiscoveredService) + "} from blueprint");
        }

        private void callServicesFromBundleContext() {
            try {
                Collection<ServiceReference<MyServiceInterface>> serviceReferences = context.getServiceReferences(MyServiceInterface.class, "(implementation=bp-external)");
                if (null != serviceReferences) {
                    log.info("Found {} Services in bundle context", serviceReferences.size());
                    /*
                    ServiceReference<MyServiceInterface> ref = serviceReferences.iterator().next();
                    if (null != ref) {
                        discoveredService = context.getService(ref);
                    } */
                    for (ServiceReference<MyServiceInterface> ref : serviceReferences) {
                        MyServiceInterface instance = context.getService(ref);
                        // log.info("Calling instance of {} from loop in {}", instance.getClass().getName(), this.getClass().getName());
                        instance.execute("Calling service from bundle context {" + instance.getClass().getName() + " " + System.identityHashCode(instance) + "} from blueprint");
                        context.ungetService(ref);
                    }
                } else {
                    log.warn("Didn't find any services in bundle context");
                }
            } catch (InvalidSyntaxException e) {
                log.error( "Failed to lookup service", e);
            }

        }
    }

}


