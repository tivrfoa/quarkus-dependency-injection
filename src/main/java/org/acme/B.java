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
