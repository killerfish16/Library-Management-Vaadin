package com.book.main;

import static org.junit.Assert.assertEquals;

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
public class UserRepositoryTest {
	   @Autowired
	    private TestEntityManager entityManager;

	    @Autowired
	    private UserRepository userRepository;

	    
	    @Test
	    public void testFindUser() {
	    	try {
	    		Users cus=new Users();
				cus.setUsername("username");
				cus.setPassword("password");
				cus.setAccountNonLocked(true);
				cus.setAccountNonExpired(true);
				cus.setCredentialsNonExpired(true);
				cus.setEnabled(true);
				cus.setRole("Admin");
				cus.setEmail("test@test.com");
		        entityManager.persist(cus);
		
		        Users  usr = userRepository.findByUsername("username");
		        assertEquals("test@test.com", cus.getEmail());
		        assertEquals("Admin", cus.getRole());
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	  
}
