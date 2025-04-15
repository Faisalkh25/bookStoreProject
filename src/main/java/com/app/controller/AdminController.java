package com.app.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Book;
import com.app.repository.BookRepository;
import com.app.service.BookService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	@Autowired
	private BookService bookService;
	
	@Value("${upload.directory}")
	private String uploadDirectory;
	
	
	    @GetMapping("/admin-panel")
	    public String showAdminDashboard() {
	    	
	    	return "admin-dashboard";
	    }
	    
	    @GetMapping("/list-books")
	    public String showListOfBooks() {
	    	   return "book-list";
	    }
	    
	    @GetMapping("/add-book")
	    public String addBookForm() {
	    	
	    	return "addBook";
	    }
		   
	    @PostMapping("/saveBook")
	    @ResponseBody
	    public ResponseEntity<?> saveBook(@ModelAttribute Book book,
	    		                           @RequestParam("bookPhoto") MultipartFile file ) {
	    	
	    	try {
				if(!file.isEmpty()) {
				String fileName =	bookService.saveBookPhoto(file);
				book.setImagePath(fileName);
				}
				
				Book savedBook = bookService.saveBook(book);
				return ResponseEntity.ok(savedBook);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error saving book:" + e.getMessage());
			}
	    }
	    
	    //update Book
	    
	    @PutMapping("/updateBook")
	    @ResponseBody
	    public ResponseEntity<?> updateBook(@ModelAttribute Book book,
	    		                            @RequestParam(value="bookPhoto", required = false) MultipartFile file) {
	    	   try {
				  if(file != null && !file.isEmpty()) {
					      String fileName =  bookService.saveBookPhoto(file);
					      book.setImagePath(fileName);
				  }
				  
				   Book updatedBook = bookService.updateBook(book);
				   return ResponseEntity.ok(updatedBook);
			} 
	    	   catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error updating book:" + e.getMessage());
						}
	    }
	    
	    //getAllBooks
	    @GetMapping("/list")
	    @ResponseBody
	    public List<Book> getAllBooks() {
	    	return bookService.getAllBooks();
	    }
	    
	    //getById
	    @GetMapping("/book/{id}")
	    @ResponseBody
	    public Book getBookById(@PathVariable("id") int id) {
	    	return bookService.getBookById(id);
	    }
	    
	    //deleteBook
	    @DeleteMapping("/deleteBook/{id}")
	    @ResponseBody
	    public ResponseEntity<?> deleteBook(@PathVariable("id") int id) {
	    	   try {
				   bookService.deleteBook(id);
				   return ResponseEntity.ok("book deleted successfully!");
			} 
	    	   catch (Exception e) {
			       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error deleting book: " + e.getMessage());
			}
	    }
	    
	    // Serve uploaded images
	    @GetMapping("/uploads/{filename:.+}")
	    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
	        try {
	            Path file = Paths.get(uploadDirectory).resolve(filename);
	            Resource resource = new UrlResource(file.toUri());
	            
	            if (resource.exists() || resource.isReadable()) {
	                return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file))
	                    .body(resource);
	            } else {
	                throw new RuntimeException("Could not read the file!");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
}
