package com.book.main;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

  
    @Test
    public void testFindByTitle() {
    	try {
	        entityManager.persist(new Book("Book1","Description",new SimpleDateFormat("yyyy-MM-dd").parse("2001-1-2"),200,"Cris"));
	
	        List<Book>  bkList = bookRepository.findByTitle("Book1");
	        Book bk=bkList.get(0);
	        assertEquals("Description", bk.getDescription());
	        assertEquals("Cris", bk.getAuthorName());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}