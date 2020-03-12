package com.book.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class GBook implements Serializable, Cloneable  {
	
	private String title;
	private String description;
	private String publishedDate;
	private double price;
	private String authorName;
	private String thumbnail;
	

	public GBook(String title, String description, String publishedDate, double price, String authorName, String thumbnail) {
		this.title = title;
		this.description = description;
		this.publishedDate = publishedDate;
		this.price = price;
		this.authorName = authorName;
		this.thumbnail = thumbnail;
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

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
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

	

	public String getThumbnail() {
		return thumbnail;
	}



	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	
	public static List<GBook> updateGbooks(String result){
		
		List<GBook> gbooks = new ArrayList<GBook>();
		JSONObject obj = new JSONObject(result);
		JSONArray items = obj.getJSONArray("items");
		items.forEach(e ->{
		
			JSONObject volumeInfo = ((JSONObject) e).getJSONObject("volumeInfo");
			JSONObject salesInfo = ((JSONObject) e).getJSONObject("saleInfo");
			String saleability = salesInfo.getString("saleability");
			Double price = 0.0;
			if(saleability.equalsIgnoreCase("FOR_SALE")) {
			JSONObject listPrice = salesInfo.getJSONObject("listPrice");
			price = listPrice.getDouble("amount");
			}
			JSONArray authors = null;
			String author = null;
			try {
				authors = volumeInfo.getJSONArray("authors");
				author = authors.getString(0);
			}
			catch(JSONException exp) {
				author ="NoAuthor";	
			}
			JSONObject imglink = volumeInfo.getJSONObject("imageLinks");
			String title = volumeInfo.getString("title");
			String publishedDate = volumeInfo.getString("publishedDate");
			String description = volumeInfo.getString("description");
			String thumbnail = imglink.getString("thumbnail"); 
			
			GBook gbook = new GBook(title, description, publishedDate, price, author, thumbnail);
			gbooks.add(gbook);
		});
		
		return gbooks;
		
	}
	
}
