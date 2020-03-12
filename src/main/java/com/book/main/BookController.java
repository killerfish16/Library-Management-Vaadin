package com.book.main;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/test/books")
    public ResponseEntity allBooks() {
		
		List<Book> books = bookService.findAllBooks();
		if(books.size() >0) {
			return ResponseEntity.status(HttpStatus.OK).body(books);
		}
		
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
     
    }
	
	@GetMapping("/test/books/{title}")
    public List<Book> getBook(@Valid @PathVariable String title) {

        return bookService.findByTitle(title);
    }
	
	@DeleteMapping("/test/books/{id}")
    public void deleteBook(@Valid @PathVariable Long id) {
		bookService.deleteBook(id);
    }
	
	@DeleteMapping("/test/books")
    public void deleteAllBooks() {
		bookService.deleteAllBooks();
    }
	
	@PostMapping("/test/books")
	@ResponseStatus(code = HttpStatus.CREATED)
    public void addBook(@Valid @RequestBody Book book) {
		bookService.addBook(book);
    }
	
	@PutMapping("/test/books")
    public void updateBook(@Valid @RequestBody Book book) {
		bookService.updateBook(book);
    }
}