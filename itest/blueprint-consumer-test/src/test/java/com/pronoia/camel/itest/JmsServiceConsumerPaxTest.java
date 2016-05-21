package com.pronoia.camel.itest;

import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import com.pronoia.camel.itest.karaf.CamelKarafTestSupport;

import org.apache.felix.gogo.commands.CommandException;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.karaf.jaas.boot.principal.RolePrincipal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.ops4j.pax.exam.options.TimeoutOption;
import org.ops4j.pax.exam.util.Filter;
import org.ops4j.pax.swissbox.tinybundles.dp.Constants;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.mavenJar;
import static org.ops4j.pax.exam.CoreOptions.propagateSystemProperties;
import static org.ops4j.pax.exam.CoreOptions.streamBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.systemTimeout;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.replaceConfigurationFile;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.modifyBundle;

@RunWith(PaxExam.class)
public class JmsServiceConsumerPaxTest extends CamelKarafTestSupport {
    final String sourceComponentName = "sjms-source";
    final String sourceDestinationName = "queue:test.source";
    final String targetUri = "mock://target";


    final String contextName = "jms-consumer-context";

    @Rule
    public EmbeddedActiveMQBroker embeddedActiveMQBroker = new EmbeddedActiveMQBroker();

    /*
    @Inject
    @Filter("camel.context.name="+contextName)
    */
    protected CamelContext camelContext;


    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*,org.apache.felix.service.*;status=provisional");
        return probe;
    }

    /*
    @BeforeClass
    static public void setUp() throws Exception {
        System.err.println( "Starting Embedded Broker");
        embeddedActiveMQBroker.start();
    }

    @AfterClass
    static public void tearDown() throws Exception {
        System.err.println( "Stopping Embedded Broker");
        embeddedActiveMQBroker.stop();
    }
    */

    @Configuration
    public Option[] config() {
        System.err.println("karaf.http.port = " + System.getProperty("karaf.http.port", "Not Found"));
        System.err.println("karaf.ssh.port = " + System.getProperty("karaf.ssh.port", "Not Found"));
        System.err.println("karaf.rmi.registry.port = " + System.getProperty("karaf.rmi.registry.port", "Not Found"));
        System.err.println("karaf.rmi.server.port = " + System.getProperty("karaf.rmi.server.port", "Not Found"));

        MavenArtifactUrlReference karafUrl = maven()
                .groupId("org.apache.karaf")
                .artifactId("apache-karaf")
                .version(karafVersion())
                .type("tar.gz");

        MavenUrlReference karafStandardRepo = maven()
                .groupId("org.apache.karaf.assemblies.features")
                .artifactId("standard")
                .version(karafVersion())
                .classifier("features")
                .type("xml");
        MavenUrlReference camelStandardRepo = maven()
                .groupId("org.apache.camel.karaf")
                .artifactId("apache-camel")
                .version(camelVersion())
                .classifier("features")
                .type("xml");
        MavenUrlReference activeMqStandardRepo = maven()
                .groupId("org.apache.activemq")
                .artifactId("activemq-karaf")
                .version(activemqVersion())
                .classifier("features")
                .type("xml");
        return new Option[]{
                // KarafDistributionOption.debugConfiguration("5005", true),
                karafDistributionConfiguration()
                        .frameworkUrl(karafUrl)
                        .unpackDirectory(new File("target", "exam"))
                        .useDeployFolder(false)
                        .karafVersion(karafVersion())

                ,
                keepRuntimeFolder(),
                configureConsole().ignoreRemoteShell(),

                // configureConsole().ignoreLocalConsole(),
                junitBundles(),
                // systemTimeout(60000),
                logLevel(LogLevelOption.LogLevel.INFO),
                // editConfigurationFilePut("etc/org.apache.karaf.shell.cfg", "sshPort", System.getProperty("karaf.ssh.port", "8101")),
                editConfigurationFilePut("etc/org.ops4j.pax.web.cfg", "org.osgi.service.http.port", System.getProperty("karaf.http.port", "8181")),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiRegistryPort", System.getProperty("karaf.rmi.registry.port", "1099")),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiServerPort", System.getProperty("karaf.rmi.server.port", "44444")),
                // propagateSystemProperties( "karaf.http.port", "karaf.ssh.port", "karaf.rmi.registry.port", "karaf.rmi.server.port"),
                /*editConfigurationFilePut("etc/org.apache.karaf.features.cfg", "featuresBoot",
                        "karaf-framework,shell,features,service-security,admin,config,kar,deployer,diagnostic"
                ),
                */
                editConfigurationFilePut("etc/custom.properties", "karaf.shutdown.port", "-1"),
                // editConfigurationFilePut("etc/config.properties", "karaf.startRemoteShell", "false"),
                // features(activeMqStandardRepo, "activemq-camel"),
                // replaceConfigurationFile( "etc/activemq.xml", new File("src/test/resources/etc/activemq-embedded.xml")),
                mavenBundle().groupId("com.pronoia.camel.itest").artifactId("camel-itest-karaf").version("0.0.1-SNAPSHOT"),
                // wrappedBundle( maven().groupId("com.pronoia.camel.itest").artifactId("camel-itest-karaf").version("0.0.1-SNAPSHOT") ),
                features( activeMqStandardRepo, "activemq"),
                wrappedBundle(
                        maven()
                                .groupId("org.apache.activemq.tooling")
                                .artifactId("activemq-junit")
                                .version("5.13.1")
                    ),
                features(camelStandardRepo, "camel"),
                features(camelStandardRepo, "camel-blueprint"),
                features(camelStandardRepo, "camel-sjms"),
                features(camelStandardRepo, "camel-test"),
                mavenBundle()
                        .groupId("com.pronoia.osgi")
                        .artifactId("service-interface")
                        .version("0.0.1-SNAPSHOT"),
                mavenBundle()
                        .groupId("com.pronoia.osgi")
                        .artifactId("service-one")
                        .version("0.0.1-SNAPSHOT"),
                /*
                mavenBundle()
                        .groupId("com.pronoia.camel")
                        .artifactId("blueprint-consumer")
                        .version("2.0.0-SNAPSHOT"),
                */
        };
    }

    static private String karafVersion() {
        return "2.4.4";
    }

    static private String camelVersion() {
        return "2.17-SNAPSHOT";
    }

    static private String activemqVersion() {
        // return "5.13.1";
        // return "5.11.4";
        return "5.12.3";
    }

    /*
    @Ignore
    @Test
    public void testBrokerStarted() throws Exception {
        System.err.println( "Broker Name: " + embeddedActiveMQBroker.getBrokerName());
        assertTrue( "Broker Service should be started", embeddedActiveMQBroker.getBrokerService().isStarted());
    }
    */

    @Test
    public void testConfig() throws Exception {
        /*
        System.err.println( "Installing blueprint-consumer");
        String installResult = executeCommand("bundle:install -s mvn:com.pronoia.camel/blueprint-consumer/2.0.0-SNAPSHOT", new RolePrincipal("admin"));
        System.err.println( "Install Command Result: " + installResult);

        camelContext = getOsgiService(CamelContext.class, "camel.context.name=jms-consumer-context", 10000);
        MockEndpoint target = (MockEndpoint) camelContext.getEndpoint(targetUri);
        target.expectedMessageCount(3);

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody(sourceComponentName + "://" + sourceDestinationName, "Test Message");

        // target.assertIsSatisfied();
        String ver = executeCommand( "version");
        System.out.println( "Karaf Version: " + ver);

        String s = executeCommand("osgi:list -t 0");
        System.out.println( "Command result: " + s);


        target.assertIsSatisfied(15, TimeUnit.SECONDS);

        System.err.println( "Target Received: " + target.getReceivedCounter() );
        */
        String s = executeCommand("osgi:list");
        System.err.println( "Command result: " + s);

        /*
        System.err.println("karaf.http.port = " + System.getProperty("karaf.http.port", "Not Found"));
        System.err.println("karaf.ssh.port = " + System.getProperty("karaf.ssh.port", "Not Found"));
        System.err.println("karaf.rmi.registry.port = " + System.getProperty("karaf.rmi.registry.port", "Not Found"));
        System.err.println("karaf.rmi.server.port = " + System.getProperty("karaf.rmi.server.port", "Not Found"));
        */
        Thread.sleep( 15000 );
        // assertTrue( "Broker Service should be started", embeddedActiveMQBroker.getBrokerService().isStarted());
        // System.err.println( "Removing blueprint-consumer");
        // executeCommand( "bundle:uninstall blueprint-consumer", new RolePrincipal("admin"));
    }
}
