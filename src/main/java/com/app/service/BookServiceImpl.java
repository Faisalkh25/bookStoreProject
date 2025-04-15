package com.app.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Book;
import com.app.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	  @Autowired
	  private BookRepository bookRepo;
	  
	  @Value("${upload.directory}")
	  private String uploadDirectory;
	
	@Override
	public Book saveBook(Book book) {
		   Book b = bookRepo.save(book);
		return b;
	}

	@Override
	public List<Book> getAllBooks() {
		     List<Book> books = bookRepo.findAll();
		return books;
	}

	@Override
	public Book getBookById(int id) {
		      Book book = bookRepo.findById(id).get();
		return book;
	}

	@Override
	public void deleteBook(int id) {
		   bookRepo.deleteById(id);
		
	}

	@Override
	public String saveBookPhoto(MultipartFile file) {
   
		   if(file.isEmpty()) {
			   throw new RuntimeException("failed to store a file.");
		   }
		   
		   try {
			String fileName =  System.currentTimeMillis() + "_" + file.getOriginalFilename();
			             Path uploadPath = Paths.get(uploadDirectory);
			             
			             if(!Files.exists(uploadPath)) {
			            	 Files.createDirectories(uploadPath);
			             }
			             
			          try(InputStream ins = file.getInputStream()) {
						  Files.copy(ins, uploadPath.resolve(fileName),  StandardCopyOption.REPLACE_EXISTING);
					} 
			          return fileName;
		   } 
			          catch (Exception e) {
						throw new RuntimeException("failed to store a file. " + e);
			          }  
		} 
		  		
		
	

	@Override
	public Book updateBook(Book book) {
		         Book existingBook = getBookById(book.getBook_id());
		         
		         existingBook.setBook_name(book.getBook_name());
		         existingBook.setAuthor(book.getAuthor());
		         existingBook.setPrice(book.getPrice());
		         
		         if(book.getImagePath() != null && !book.getImagePath().isEmpty()) {
		        	 existingBook.setImagePath(book.getImagePath());
		         }
		   
		return bookRepo.save(existingBook);
	}

}
