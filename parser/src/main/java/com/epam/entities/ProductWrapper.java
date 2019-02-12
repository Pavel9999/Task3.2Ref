package com.epam.entities;

import java.util.LinkedList;
import java.util.List;

public class ProductWrapper
{
    List<Product> cold_snacks;
    List<Product> hot_snacks;
    List<Product> breakfasts;


    public ProductWrapper(){}

    public void setProducts(List<Product> products)
    {
        cold_snacks = new LinkedList<>();
        hot_snacks = new LinkedList<>();
        breakfasts = new LinkedList<>();

        for (Product product : products)
        {
            switch (product.getType()){

                case COLD_SNACK:{
                    cold_snacks.add(product);
                }break;
                case HOT_SNACK:{
                    hot_snacks.add(product);
                }break;
                case BREAKFAST:{
                    breakfasts.add(product);
                }break;

                default: break;
            }
        }
    }

    public List<Product> getProducts(String type, String page_number, int productsOnPage)
    {
        List<Product> products = new LinkedList<>();
        switch (type)
        {
            case "cold_snack":{
                products = cold_snacks;
            }break;
            case "hot_snack":{
                products = hot_snacks;
            }break;
            case "breakfast":{
                products = breakfasts;
            }break;
            default:{
                products = cold_snacks;
            }break;
        }

        int pagesCount = products.size() / productsOnPage;
        if (products.size() % productsOnPage != 0) pagesCount++;
        int pn = Integer.valueOf(page_number);
        if (pn <= pagesCount) // сравнивает с числом округлённым в бОльшую сторону
        {
            products = products.subList((pn-1)*productsOnPage, Math.min(pn*productsOnPage, products.size()));
        }
        else
        {
            pn = 1;
            products = products.subList((pn-1)*productsOnPage, Math.min(pn*productsOnPage, products.size()));
        }

        return products;
    }

    public int getPagesCount(String type, int productsOnPage) {
        List<Product> products = new LinkedList<>();
        switch (type)
        {
            case "cold_snack":{
                products = cold_snacks;
            }break;
            case "hot_snack":{
                products = hot_snacks;
            }break;
            case "breakfast":{
                products = breakfasts;
            }break;
            default: break;
        }

        int pagesCount = products.size() / productsOnPage;
        if (products.size() % productsOnPage != 0) pagesCount++;

        return pagesCount;
    }

    public List<Product> getCold_snacks() {
        return cold_snacks;
    }

    public void setCold_snacks(List<Product> cold_snacks) {
        this.cold_snacks = cold_snacks;
    }

    public List<Product> getHot_snacks() {
        return hot_snacks;
    }

    public void setHot_snacks(List<Product> hot_snacks) {
        this.hot_snacks = hot_snacks;
    }

    public List<Product> getBreakfasts() {
        return breakfasts;
    }

    public void setBreakfasts(List<Product> breakfasts) {
        this.breakfasts = breakfasts;
    }
}
