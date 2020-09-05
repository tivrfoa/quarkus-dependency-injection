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
