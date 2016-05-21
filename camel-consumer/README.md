# blueprint-consumer

A simple Camel route defined using the Blueprint DSL that uses the OSGi services.

// Injected
2016-03-02 11:40:05,176 | TRACE | ueprint-consumer | org.apache.camel.core.osgi.utils.BundleDelegatingClassLoader | 76 - org.apache.camel.camel-blueprint - 2.17.0.SNAPSHOT | FindClass: java.lang.reflect.Method
java.lang.Exception
	at com.pronoia.osgi.service.impl.ServiceTwoImplementation.execute(ServiceTwoImplementation.java:16)
	at Proxy2bb90e11_2bba_46db_8f53_46473dfbf42e.execute(Unknown Source)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)[:1.8.0_73]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)[:1.8.0_73]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)[:1.8.0_73]
	at java.lang.reflect.Method.invoke(Method.java:497)[:1.8.0_73]
	at org.apache.camel.component.bean.MethodInfo.invoke(MethodInfo.java:408)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.bean.MethodInfo$1.doProceed(MethodInfo.java:279)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.bean.MethodInfo$1.proceed(MethodInfo.java:252)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.bean.BeanProcessor.process(BeanProcessor.java:177)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.management.InstrumentationProcessor.process(InstrumentationProcessor.java:77)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.RedeliveryErrorHandler.process(RedeliveryErrorHandler.java:468)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.CamelInternalProcessor.process(CamelInternalProcessor.java:190)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.Pipeline.process(Pipeline.java:121)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.Pipeline.process(Pipeline.java:83)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.CamelInternalProcessor.process(CamelInternalProcessor.java:190)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.timer.TimerConsumer.sendTimerExchange(TimerConsumer.java:192)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.timer.TimerConsumer$1.run(TimerConsumer.java:76)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at java.util.TimerThread.mainLoop(Timer.java:555)[:1.8.0_73]
	at java.util.TimerThread.run(Timer.java:505)[:1.8.0_73]

// Not Injected
2016-03-02 11:38:05,910 | TRACE | ueprint-consumer | org.apache.camel.core.osgi.utils.BundleDelegatingClassLoader | 76 - org.apache.camel.camel-blueprint - 2.17.0.SNAPSHOT | FindClass: java.lang.reflect.Method
java.lang.Exception
	at com.pronoia.osgi.service.impl.ServiceOneImplementation.execute(ServiceOneImplementation.java:16)
	at Proxy09d5f297_6c32_4dfe_87ee_aa6a960e03f4.execute(Unknown Source)
	at sun.reflect.GeneratedMethodAccessor19.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)[:1.8.0_73]
	at java.lang.reflect.Method.invoke(Method.java:497)[:1.8.0_73]
	at org.apache.camel.component.bean.MethodInfo.invoke(MethodInfo.java:408)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.bean.MethodInfo$1.doProceed(MethodInfo.java:279)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.bean.MethodInfo$1.proceed(MethodInfo.java:252)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.bean.BeanProcessor.process(BeanProcessor.java:177)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.management.InstrumentationProcessor.process(InstrumentationProcessor.java:77)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.RedeliveryErrorHandler.process(RedeliveryErrorHandler.java:468)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.CamelInternalProcessor.process(CamelInternalProcessor.java:190)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.Pipeline.process(Pipeline.java:121)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.Pipeline.process(Pipeline.java:83)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.processor.CamelInternalProcessor.process(CamelInternalProcessor.java:190)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.timer.TimerConsumer.sendTimerExchange(TimerConsumer.java:192)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at org.apache.camel.component.timer.TimerConsumer$1.run(TimerConsumer.java:76)[58:org.apache.camel.camel-core:2.17.0.SNAPSHOT]
	at java.util.TimerThread.mainLoop(Timer.java:555)[:1.8.0_73]
	at java.util.TimerThread.run(Timer.java:505)[:1.8.0_73]
