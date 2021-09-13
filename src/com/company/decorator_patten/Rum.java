package com.company.decorator_patten;

public class Rum extends Drink{
    @Override
    public String getName() {
        return " Rum ";
    }

    @Override
    public Double getPrice() {
        return 1.0;

    }
}
