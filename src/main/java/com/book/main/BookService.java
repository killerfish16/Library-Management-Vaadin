package com.book.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> findAllBooks() {

		Iterable<Book> bookIter = bookRepository.findAll();
		List<Book> books = new ArrayList<Book>();
		bookIter.forEach(e -> books.add(e));
		return books;
		
	}
	
	public List<Book> findByTitle(String title) {
		
		List<Book> book = bookRepository.findByTitle(title);
		return book;
		
	}
	
	@Transactional
	public void deleteAllBooks() {
		
		bookRepository.deleteAll();
	}
	@Transactional
	public void deleteBook(long id) {
		bookRepository.deleteById(id);
	}
	
	public void addBook(Book book) {
		bookRepository.save(book);
	}
	@Transactional
	public void updateBook(Book book) {
		Optional<Book> oldbook1 = bookRepository.findById(book.getId());
		Book oldbook = oldbook1.get();
		oldbook.setAuthorName(book.getAuthorName());
		oldbook.setDescription(book.getDescription());
		oldbook.setPrice(book.getPrice());
		oldbook.setPublishedDate(book.getPublishedDate());
		oldbook.setTitle(book.getTitle());
		bookRepository.save(oldbook);
		
	}
}
