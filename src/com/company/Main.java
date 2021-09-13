package com.company;

import com.company.decorator_patten.Rum;
import com.company.decorator_patten.Spices;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Closures and Callbacks
    // a higher order function is an anonymous function that can be treated as a data object
    // it can be stored in a variable and passed around from one context to another. It might be invoked in a context
    //that did not necessarily define it
//a higher order function is an anonymous function, so the invoking context
//does not have to know its name.

//    A closure is a higher order function packaged with its defining environment


    // Example of CallBack
    private final List<Callback> callbacks = new ArrayList<>();

    public static void main(String[] args) {
        // write your code here

        // Testing Decorator
        Rum rum = new Rum();
        Spices spices = new Spices(rum);
        System.out.println(spices.getName());
        System.out.println(spices.getPrice());
    }

}