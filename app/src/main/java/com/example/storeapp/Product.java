package com.example.storeapp;

public class Product {
    private String Name;
    private String Price;
    private int Imange_id;
    private int Quantity;
    private String Category;
    private int id;
    public Product(String Name, String Price, int Image_id, int quantity,String Category){
        this.Name = Name;
        this.Imange_id = Image_id;
        this.Price = Price;
        this.Quantity = quantity;
        this.Category = Category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getImange_id() {
        return Imange_id;
    }

    public void setImange_id(int imange_id) {
        Imange_id = imange_id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

}
