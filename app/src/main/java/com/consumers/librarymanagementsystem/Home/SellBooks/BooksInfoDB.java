package com.consumers.librarymanagementsystem.Home.SellBooks;

public class BooksInfoDB {
    public String genre,imageURI,sellerName,sellerID,sellerEmail,price;

    public BooksInfoDB(String genre, String imageURI, String sellerName, String sellerID, String sellerEmail,String price) {
        this.genre = genre;
        this.price = price;
        this.imageURI = imageURI;
        this.sellerName = sellerName;
        this.sellerID = sellerID;
        this.sellerEmail = sellerEmail;
    }
}
