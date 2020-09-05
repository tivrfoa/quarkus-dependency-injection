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
