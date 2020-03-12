package com.book.main;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class UserForm extends FormLayout {

	private TextField email = new TextField("Email");

	private TextField username = new TextField("UserName");
	private PasswordField password = new PasswordField("Password");
	private ComboBox<String> role = new ComboBox<String>("Role");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private Users users;
	private MyUI myUI;
	private Binder<Users> binder = new Binder<>();

	public UserForm(MyUI myUI, UserRepository userRep) {
		this.myUI = myUI;
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		addComponents(username, password, role, email, buttons);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		binder.forField(this.username).bind(Users::getUsername, Users::setUsername);
		binder.forField(this.password).bind(Users::getPassword, Users::setPassword);
		binder.forField(this.role).bind(Users::getRole, Users::setRole);
		binder.forField(this.email).withValidator(new EmailValidator("Enter a valid email")).bind(Users::getEmail,
				Users::setEmail);
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
	}

	private void save() {
		myUI.userRep.save(users);
		myUI.updateAllUserList();
		setVisible(false);
	}
	
	private void delete() {
		myUI.userRep.delete(users);
		myUI.updateAllUserList();
		setVisible(false);
	}

	public void setUsers(Users users) {
		this.users = users;
		binder.setBean(users);
		delete.setVisible(users.isPersisted());
		setVisible(true);
		username.selectAll();
	}

	public TextField getEmail() {
		return email;
	}

	public void setEmail(TextField email) {
		this.email = email;
	}

	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public PasswordField getPassword() {
		return password;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}

	public MyUI getMyUI() {
		return myUI;
	}

	public void setMyUI(MyUI myUI) {
		this.myUI = myUI;
	}

	public Binder<Users> getBinder() {
		return binder;
	}

	public void setBinder(Binder<Users> binder) {
		this.binder = binder;
	}

	public Users getUsers() {
		return users;
	}

	public ComboBox<String> getRole() {
		return role;
	}

	public void setRole(ComboBox<String> role) {
		this.role = role;
	}

}
