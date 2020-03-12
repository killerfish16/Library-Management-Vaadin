package com.book.main;




import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "book",
uniqueConstraints = @UniqueConstraint(name = "uc_title", columnNames = {"title"})
) 
@Entity
public class Book  {
	
	
	@Id
    @GeneratedValue
    private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date publishedDate;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String authorName;
	
	public Book() {
		
	}
	
	public Book(String title, String description, Date publishedDate, double price, String authorName) {

		this.title = title;
		this.description = description;
		this.publishedDate = publishedDate;
		this.price = price;
		this.authorName = authorName;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	public void setPublicationDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public boolean isPersisted() {
		return id != null;
	}


	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", description=" + description + ", publishedDate="
				+ publishedDate + ", price=" + price + ", authorName=" + authorName + "]";
	}


	
	
	
	
}
