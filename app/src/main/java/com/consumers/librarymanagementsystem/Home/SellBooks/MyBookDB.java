package com.consumers.librarymanagementsystem.Home.SellBooks;

public class MyBookDB {
    public String genre,price,purchasedCount,imageUri;

    public MyBookDB(String genre, String price, String purchasedCount,String imageUri) {
        this.genre = genre;
        this.imageUri = imageUri;
        this.price = price;
        this.purchasedCount = purchasedCount;
    }
}
