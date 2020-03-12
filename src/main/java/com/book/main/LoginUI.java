package com.book.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SpringUI(path = "/login")
public class LoginUI extends UI {
	private TextField userName;

	private PasswordField passwordField;

	private Button login;

	private VerticalLayout layout;
	
	private VaadinSession session = VaadinSession.getCurrent();
	
	public static String role;

	@Autowired
	UserRepository userRep;

	@Autowired
	UserService uService;

	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

	public LoginUI(UserRepository urep) {
		this.userRep = urep;
		this.uService = new UserService(urep);
	}

	@Override
	protected void init(VaadinRequest request) {

		layout = new VerticalLayout();
		Label title = new Label("Book Management System");
		title.addStyleName(ValoTheme.LABEL_H1);
		userName = new TextField("userName");
		passwordField = new PasswordField("Password");
		login = new Button("Sign In");
		login.addClickListener(eveent -> {
			auth();
		});
		layout.addComponents(title, userName, passwordField, login);
		setContent(layout);
	}

	public void auth() {

		daoAuthenticationProvider.setUserDetailsService(uService);
		Authentication auth = new UsernamePasswordAuthenticationToken(userName.getValue(), passwordField.getValue());
		System.out.println("username" + userName.getValue());
		System.out.println("Password"+ passwordField.getValue());
		try {
		Authentication authenticated = daoAuthenticationProvider.authenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		System.out.println("auth" + authenticated.isAuthenticated());
		
		if(authenticated.isAuthenticated()) {
			String role = userRep.findByUsername(userName.getValue()).getRole();
			if(!role.equalsIgnoreCase("admin")) {
			    
				//session.setAttribute("Role", "admin");
				//new MyUI().hideUserGrid();
				role = "admin";
					
			}
			com.vaadin.server.Page.getCurrent().setLocation("/BookUI");
			
		}
		}catch(AuthenticationException e) {
			System.out.println("Bad auth");
			Notification.show("Who are you?", Type.ERROR_MESSAGE);
		}
	}

	public TextField getUserName() {
		return userName;
	}

	public void setUserName(TextField userName) {
		this.userName = userName;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public Button getLogin() {
		return login;
	}
}
