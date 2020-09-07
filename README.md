# Quarkus Dependency Injection

This post is a WIP - Work In Progress

Everything written here can be very wrong


Martin Kouba:
>It's probably because bean A is never instantiated, the constructor is called when a client proxy is injected into Service, BUT the real problem is a.counter++;... you can't do this for normal scoped beans (incl. @RequestScoped), **you can't access fields of a normal scoped bean, instead you need to call a business method...**
see also https://quarkus.io/guides/cdi#client_proxies
>
>This is a CDI limitation...

This is really important! So let me repeat:

**You can't access fields of a normal scoped bean, instead you need to call a business method**

Example:

```java
package org.acme;

import javax.enterprise.context.*;
import javax.inject.*;

@RequestScoped
public class Service {

    @Inject
    A a;
       
    public int getAQnt() {
	// If calling incrementQnt, then A constructor is called and
	// it always returns 1 
        return a.incrementQnt(); // This is CORRECT
        
        // return a.qnt++; // !!THIS IS WRONG!! And it'll get you into trouble.
    }

    public Service() {
        System.out.println("Service constructor called.");
    }
}
```

### ApplicationScoped -> RequestScoped

When a class annotated with ApplicationScoped injects other class
annotated with RequestScoped, the RequestScoped class behaves as if
it was ApplicationScoped:

```java
package org.acme;

import javax.enterprise.context.*;
import javax.inject.*;

@ApplicationScoped
public class A {
        public int counter;

        public A() {
            System.out.println("A constructor called.");
        }
}
```

```java
package org.acme;

import javax.enterprise.context.*;
import javax.inject.*;

@RequestScoped
public class B {
        public int counter;

        public B() {
            System.out.println("B constructor called.");
        }
}
```

```java
package org.acme;

import javax.enterprise.context.*;
import javax.inject.*;

@ApplicationScoped
public class Service {

    @Inject
    A a;

    @Inject
    B b;

    public int getACounter() {
        return a.counter++;
    }

    public int getBCounter() {
        return b.counter++;
    }
}
```

```java
package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.*;

@Path("/hello")
public class ExampleResource {

    @Inject
    Service service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/counters")
    public String testCounters() {
        return String.format("A counter is %d and B counter is %d",
                service.getACounter(), service.getBCounter());
    }

    public ExampleResource() {
        System.out.println("ExampleResource constructor called.");
    }
}
```

The constructor's sysouts are printed only once, even for ExampleResource:
```log
ExampleResource constructor called.
A constructor called.
B constructor called.
```


