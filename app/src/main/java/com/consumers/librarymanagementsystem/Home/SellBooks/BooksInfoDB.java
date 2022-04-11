package com.consumers.librarymanagementsystem.Home.SellBooks;

public class BooksInfoDB {
    public String genre,imageURI,sellerName,sellerID,sellerEmail,price,authorName,publisherName,contactNumber;

    public BooksInfoDB(String genre, String imageURI, String sellerName, String sellerID, String sellerEmail,String price,String authorName,String publisherName,String contactNumber) {
        this.genre = genre;
        this.authorName = authorName;
        this.contactNumber = contactNumber;
        this.publisherName = publisherName;
        this.price = price;
        this.imageURI = imageURI;
        this.sellerName = sellerName;
        this.sellerID = sellerID;
        this.sellerEmail = sellerEmail;
    }
}
