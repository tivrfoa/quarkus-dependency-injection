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




