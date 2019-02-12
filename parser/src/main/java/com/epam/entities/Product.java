package com.epam.entities;

import java.util.LinkedList;
import java.util.List;



public class Product
{
    ProductType type;
    int id;
    String imagePath;
    String title;
    List<ProductComposition> composition;
    String description;
    String portion;

    public Product(){
        composition = new LinkedList<>();
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductComposition> getComposition() {
        return composition;
    }

    public void setComposition(List<ProductComposition> composition) {
        this.composition = composition;
    }

    public void addComposition(ProductComposition composition) {
        if (this.composition == null)  this.composition = new LinkedList<>();
        this.composition.add(composition);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }
}