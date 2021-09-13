package com.company.decorator_patten;

public class Spices extends DrinkDecorator{
    public Spices(Drink drink)
    {
        this.drink = drink;
        // this.name is the name of Drink class not DrinkDecorator or Spices
        this.name = "Spices";
        this.price = 0.25;
    }
}
