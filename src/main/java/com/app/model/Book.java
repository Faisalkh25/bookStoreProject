package com.app.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="books")
public class Book {
      
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Integer book_id;
	   private String book_name;
	   private String author;
	   private double price;
	   
	   private String imagePath;
	   @Transient
	   @JsonIgnore
	   private MultipartFile bookPhoto;
	   
	   
	public Book(Integer book_id, String book_name, String author, double price, String imagePath,
			MultipartFile bookPhoto) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.author = author;
		this.price = price;
		this.imagePath = imagePath;
		this.bookPhoto = bookPhoto;
	}


	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getBook_id() {
		return book_id;
	}


	public void setBook_id(Integer book_id) {
		this.book_id = book_id;
	}


	public String getBook_name() {
		return book_name;
	}


	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public MultipartFile getBookPhoto() {
		return bookPhoto;
	}


	public void setBookPhoto(MultipartFile bookPhoto) {
		this.bookPhoto = bookPhoto;
	}
	   
	   
	   
}
