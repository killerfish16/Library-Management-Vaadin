package com.book.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */

@SpringUI(path="/BookUI")
public class MyUI extends UI {

	static RestTemplate restTemplate = new RestTemplate();

	@Autowired
	BookRepository bookRep;

	@Autowired
	UserRepository userRep;

	public void setBookRep(BookRepository bookRep) {
		this.bookRep = bookRep;
	}

	public void setUserRep(UserRepository userRep) {
		this.userRep = userRep;
	}

	public MyUI(BookRepository bookRep, UserRepository userRep) {
		this.bookRep = bookRep;
		this.userRep = userRep;
	}
	protected MyUI() {}
	// Book Library Tab grid
	private Grid<Book> grid;
	// Users Tab grid
	private Grid<Users> grid1;
	// Google Books Tab grid
	private Grid<GBook> grid2;

	private VerticalLayout layout;
	// Book Library tab components
	private VerticalLayout bookLayout;
	private BookForm form;
	private TextField filterText = new TextField();
	private Button clearFilterTextBtn;
	private Button addBookBtn;
	private CssLayout filtering;
	private HorizontalLayout toolbar;
	private HorizontalLayout main;

	// Users tab components
	private VerticalLayout userLayout;
	private UserForm uform;
	private TextField filterText1 = new TextField();
	private Button clearFilterTextBtn1;
	private Button addUserBtn1;
	private CssLayout filtering1;
	private HorizontalLayout toolbar1;
	private HorizontalLayout main1;

	// Google Books Tab components
	private VerticalLayout gbookLayout;
	private TextField filterText2 = new TextField();
	private Button filterText2Btn2;
	private Button clearFilterTextBtn2;
	private Button addGBookBtn2;
	private CssLayout filtering2;
	private HorizontalLayout toolbar2;
	private HorizontalLayout main2;

	// thumbnail
	private Resource thumbnailRes;
	private Image image;

	// Tab
	private TabSheet tabsheet;

	public void init(VaadinRequest vaadinRequest) {
		getPage().setTitle("BookUI");
		Label pageHeader = new Label("Book Management System");
		pageHeader.addStyleName(ValoTheme.LABEL_BOLD);
		layout = new VerticalLayout();
		createBookGrid();
		createUserGrid();
		createGBookGrid();
		tabsheet = new TabSheet();
		tabsheet.addTab(bookLayout).setCaption("Book Library");
		tabsheet.addTab(userLayout).setCaption("Users");
		tabsheet.addTab(gbookLayout).setCaption("Google Books");
		
		layout.addComponents(pageHeader,tabsheet);
		setContent(layout);
	}
	
	public  void hideUserGrid() {
		tabsheet.getTab(1).setVisible(false);
	}

	public void createBookGrid() {
		form = new BookForm(this, this.bookRep);
		grid = new Grid<>(Book.class);
		addBookBtn = new Button("Add New Book");
		clearFilterTextBtn = new Button("Clear");
		clearFilterTextBtn.setDescription("Clear the current filter");
		filtering = new CssLayout();
		toolbar = new HorizontalLayout();
		main = new HorizontalLayout();
		clearFilterTextBtn.addClickListener(event -> {
			filterText.clear();
			updateAllBookList();
		});
		addBookBtn.addClickListener(event ->{
		
			grid.asSingleSelect().clear();
			form.setBook(new Book());
		});
		filtering.addComponents(filterText,clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		grid.setColumns("title", "description" , "publishedDate", "authorName", "price");
		updateAllBookList();
		 //Filtering
		
        filterText.setPlaceholder("filter by Title");
        filterText.addValueChangeListener(e -> updateBookList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        
        toolbar.addComponents(filtering,addBookBtn );
        main.addComponents(grid,form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 2);
        main.setExpandRatio(form, 1);
        form.setVisible(false);
        grid.asSingleSelect().addValueChangeListener(event ->{
        	if(event.getValue() == null) {
        		form.setVisible(false);
        	}
        	else {
        		form.setBook(event.getValue());
        	}
        });
        bookLayout = new VerticalLayout();
        bookLayout.addComponents(toolbar,main);
		
	}
	
	 public void updateBookList() {
	        List<Book> books = bookRep.findByTitle(filterText.getValue());
	        grid.setItems(books);
	    }

	 public void updateAllBookList() {
	        List<Book> books = StreamSupport.stream(bookRep.findAll().spliterator(), false)
	        	    .collect(Collectors.toList());
	        grid.setItems(books);
	    }
	 
	
	public void createUserGrid() {
		uform = new UserForm(this, this.userRep);
		grid1 = new Grid<>(Users.class);
		
		addUserBtn1 = new Button("Add New User");
		clearFilterTextBtn1 = new Button("Clear");
		clearFilterTextBtn1.setDescription("Clear the current filter");
		filtering1 = new CssLayout();
		toolbar1 = new HorizontalLayout();
		main1 = new HorizontalLayout();
		clearFilterTextBtn1.addClickListener(event -> {
			filterText1.clear();
			updateAllUserList();
		});
		addUserBtn1.addClickListener(event ->{
		
			grid1.asSingleSelect().clear();
			uform.setUsers(new Users());
		});
		filtering1.addComponents(filterText1,clearFilterTextBtn1);
		filtering1.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		grid1.setColumns("username", "password" , "role", "email");
		updateAllUserList();
		 //Filtering
		
        filterText1.setPlaceholder("filter by Username");
        filterText1.setValueChangeMode(ValueChangeMode.LAZY);
        filterText1.addValueChangeListener(e -> updateUserList());
       
        
        toolbar1.addComponents(filtering1,addUserBtn1 );
        main1.addComponents(grid1,uform);
        main1.setSizeFull();
        grid1.setSizeFull();
        main1.setExpandRatio(grid1, 2);
        main1.setExpandRatio(uform, 1);
        uform.setVisible(false);
        grid1.asSingleSelect().addValueChangeListener(event ->{
        	if(event.getValue() == null) {
        		uform.setVisible(false);
        	}
        	else {
        		uform.setUsers(event.getValue());
        	}
        });
        userLayout = new VerticalLayout();
        userLayout.addComponents(toolbar1,main1);
		
	}
	
	 public void updateUserList() {
		 Users users = userRep.findByUsername(filterText1.getValue());
		 grid1.setItems(users);
		 
	 }
	 
	 public void updateAllUserList() {
		 List<Users> users =StreamSupport.stream(userRep.findAll().spliterator(), false)
	        	    .collect(Collectors.toList());
		 grid1.setItems(users);
		 
	 }

	public void createGBookGrid() {
		grid2 = new Grid<>(GBook.class);
		addGBookBtn2 = new Button("Add Book to library");
		clearFilterTextBtn2 = new Button("Clear");
		clearFilterTextBtn2.setDescription("Clear the current filter");
		filtering2 = new CssLayout();
		toolbar2 = new HorizontalLayout();
		main2 = new HorizontalLayout();
		filterText2Btn2 = new Button("search");
	
		clearFilterTextBtn2.addClickListener(event -> {
			filterText2.clear();
			grid2.setItems(new ArrayList<GBook>());
		});
		/*
		 * addGBookBtn2.addClickListener(event ->{
		 * 
		 * form.setBook(new Book()); });
		 */
		filtering2.addComponents(filterText2,filterText2Btn2,clearFilterTextBtn2);
		filtering2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		grid2.setColumns("title", "description" , "publishedDate", "authorName", "price");
		 //Filtering
		filterText2.setPlaceholder("filter by Title");
		//filterText2.addValueChangeListener(e -> listGoogleApi());
		filterText2.setValueChangeMode(ValueChangeMode.LAZY);
		filterText2Btn2.addClickListener(e -> listGoogleApi());
		toolbar2.addComponents(filtering2,addGBookBtn2 );
        main2.addComponent(grid2);
        main2.setSizeFull();
        grid2.setSizeFull();
        //main2.setExpandRatio(grid2, 2);
        //main2.setExpandRatio(form, 1);
        grid2.asSingleSelect().addValueChangeListener(event ->{
        	if(event.getValue() == null) {
        		//Do nothing
        		main2.removeComponent(image);
        	}
        	else {
        		if(image != null) {
        			main2.removeComponent(image);
        		}
        		thumbnailRes = new ExternalResource(event.getValue().getThumbnail());
        		image = new Image(event.getValue().getTitle(), thumbnailRes);
        		main2.addComponent(image);
        		main2.setExpandRatio(grid2, 2);
                main2.setExpandRatio(image, 1);
        	}
        });
        gbookLayout = new VerticalLayout();
        gbookLayout.addComponents(toolbar2,main2);
	}

	private void listGoogleApi() {
		String uri = "https://www.googleapis.com/books/v1/volumes?q=" + filterText2.getValue(); 
		String result = restTemplate.getForObject(uri, String.class);
		List<GBook> gBooks = GBook.updateGbooks(result);
		grid2.setItems(gBooks);
	}

	public BookRepository getBookRep() {
		return bookRep;
	}

	public Grid<Book> getGrid() {
		return grid;
	}

	public TextField getFilterText() {
		return filterText;
	}

	public BookForm getForm() {
		return form;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public Button getClearFilterTextBtn() {
		return clearFilterTextBtn;
	}

	public Button getAddBookBtn() {
		return addBookBtn;
	}

	public CssLayout getFiltering() {
		return filtering;
	}

	public HorizontalLayout getToolbar() {
		return toolbar;
	}

	public HorizontalLayout getMain() {
		return main;
	}
	
	 
}