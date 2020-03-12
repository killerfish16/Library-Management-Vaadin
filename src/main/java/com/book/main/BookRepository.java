package com.book.main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	public List<Book> findByTitle(String Title);
	
}
																																																																																																																																																															