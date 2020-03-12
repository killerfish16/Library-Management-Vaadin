package com.book.main;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class BookForm extends FormLayout {

	private TextField title = new TextField("Title");

	private TextField description = new TextField("Description");
	private TextField price = new TextField("Price");
	private DateField publicationDate = new DateField("Publication Date");
	private TextField authorName = new TextField("Author Name");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private Book book;
	private MyUI myUI;
	private Binder<Book> binder = new Binder<>();

	public BookForm(MyUI myUI, BookRepository brep) {
		this.myUI = myUI;
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		addComponents(title, description, price, publicationDate, authorName, buttons);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		binder.forField(this.title).bind(Book::getTitle, Book::setTitle);
		binder.forField(this.description).bind(Book::getDescription, Book::setDescription);
		binder.forField(this.authorName).bind(Book::getAuthorName, Book::setAuthorName);
		binder.forField(this.publicationDate).withConverter(new LocalDateToDateConverter()).bind(Book::getPublishedDate,
				Book::setPublicationDate);
		binder.forField(this.price).withNullRepresentation("")
				.withConverter(new StringToDoubleConverter(Double.valueOf(0.0), "Double only"))
				.bind(Book::getPrice, Book::setPrice);
		// binder.bindInstanceFields(this);
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
	}

	private void delete() {
		myUI.bookRep.delete(book);
		myUI.updateAllBookList();
		setVisible(false);
	}

	private void save() {
		myUI.bookRep.save(book);
		myUI.updateAllBookList();
		setVisible(false);
	}

	public void setBook(Book book) {
		this.book = book;
		binder.setBean(book);
		delete.setVisible(book.isPersisted());
		setVisible(true);
		title.selectAll();
	}

	public TextField getTitle() {
		return title;
	}

	public TextField getDesc() {
		return description;
	}

	public TextField getPrice() {
		return price;
	}

	public DateField getPublicationDate() {
		return publicationDate;
	}

	public TextField getAuthorName() {
		return authorName;
	}

	public Button getSave() {
		return save;
	}

	public Button getDelete() {
		return delete;
	}

	public Book getBook() {
		return book;
	}

	public MyUI getMyUI() {
		return myUI;
	}

	public Binder<Book> getBinder() {
		return binder;
	}

}
