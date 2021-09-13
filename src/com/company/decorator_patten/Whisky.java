package com.company.decorator_patten;

public class Whisky extends Drink{
    @Override
    public String getName() {
        return " Whisky ";
    }

    @Override
    public Double getPrice() {
        return 0.5;
    }
}
