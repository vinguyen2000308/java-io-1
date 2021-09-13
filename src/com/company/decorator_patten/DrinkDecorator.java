package com.company.decorator_patten;

public abstract class DrinkDecorator extends Drink{
    protected Drink drink;

    // this.name call from Drink
    // super.getName() call from Drink. Because we in the DrinkDecorator and Parent class is Drink

    @Override
    public String getName() {
//        return this.name + "," + this.drink.getName();
        // Using supper
        return super.getName() + "," + this.drink.getName();
    }

    @Override
    public Double getPrice() {
//        return this.price + this.drink.getPrice();
        // Using supper
        return super.getPrice() + this.drink.getPrice();
    }

    public Drink getDrink() {
        return drink;
    }

}
