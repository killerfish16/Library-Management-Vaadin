package com.book.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@RunWith(SpringRunner.class)
public class BookControllerTest {
    private MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext context;
    
   

    @Before
    public void setup() {
        //this.mockMvc = MockMvcBuilders.standaloneSetup(new WeatherApiController()).build();
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void delete_ok() throws Exception {
    	mockMvc.perform(delete("/test/books" )).andDo(print())
        .andExpect(status().isOk());
    }
    
    @Test
 	public void retrieveBook_notFound() throws Exception {
    	delete_ok();
    	 mockMvc.perform(get("/test/books" )).andDo(print())
         .andExpect(status().isNoContent());
     }
    
    @Test
	public void addBook_ok() throws Exception {
    	Book bk=new Book();
    	bk.setAuthorName("Author1");
		bk.setDescription("Desc1");
		bk.setPrice(1000);
		bk.setPublicationDate(new SimpleDateFormat("yyyy-MM-dd").parse("1978-12-12"));
		bk.setTitle("Title1");
		byte[] locJson = toJson(bk);
		 mockMvc.perform(post("/test/books" )//.andDo(print())
		 			.content(locJson)
		 			.contentType(MediaType.APPLICATION_JSON)
		 			.accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isCreated());
    }
    
    @Test
  	public void retrieveBook_Ok() throws Exception {
    	addBook_ok();
    	
    	 mockMvc.perform(get("/test/books" )).andDo(print())
         .andExpect(status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Title1"))
         .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value("Desc1"))
         .andExpect(MockMvcResultMatchers.jsonPath("$.[0].authorName").value("Author1"))
//    	 .andExpect(MockMvcResultMatchers.jsonPath("$.[0].publicationDate").value("12/12/1978"))
    	 .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price").value("1000.0"));
      }
    

    
    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
