package com.epam.entities;

public class ProductComposition
{
    int price;
    int number;
    String description;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPrice(String price) {
        if (price.isEmpty())
        {
            this.number = 0;
            return;
        }
        if (price.matches("-?\\d+(\\.\\d+)?"))
        {
            this.price = Integer.valueOf(price);
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNumber(String number) {
        if (number.isEmpty())
        {
            this.number = 0;
            return;
        }
        if (number.matches("-?\\d+(\\.\\d+)?"))
        {
            this.number = Integer.valueOf(number);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}