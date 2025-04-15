package com.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.model.Book;

public interface BookService {

	   public Book saveBook(Book book);
	   public List<Book> getAllBooks();
	   public Book getBookById(int id);
	   public void deleteBook(int id);
	   public String saveBookPhoto(MultipartFile file);
	   public Book updateBook(Book book);
}
