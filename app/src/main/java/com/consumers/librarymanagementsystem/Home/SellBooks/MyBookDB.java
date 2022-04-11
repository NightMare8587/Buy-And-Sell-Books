package com.consumers.librarymanagementsystem.Home.SellBooks;

public class MyBookDB {
    public String genre,price,purchasedCount,imageUri,authorName,publisherName,contactNumber;

    public MyBookDB(String genre, String price, String purchasedCount,String imageUri,String authorName,String publisherName,String contactNumber) {
        this.genre = genre;
        this.imageUri = imageUri;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.contactNumber  = contactNumber;
        this.price = price;
        this.purchasedCount = purchasedCount;
    }
}
