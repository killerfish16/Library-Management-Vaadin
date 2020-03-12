package com.book.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public List<Users> findAllUsers() {

		Iterable<Users> usrIter = userRepository.findAll();
		List<Users> users = new ArrayList<Users>();
		usrIter.forEach(e -> users.add(e));
		return users;
	}

	public Users findByUsername(String username) {

		Users user = userRepository.findByUsername(username);

		return user;

	}

	public void addUser(Users user) {

		userRepository.save(user);
	}

	public void deleteUser(Users user) {
		userRepository.delete(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username);
		if(null == user.getRole()) {
			user.setRole("Admin");
		}
		
		UserDetails usr = User.withUsername(user.getUsername()).password("{noop}"+user.getPassword()).roles(user.getRole())
				.build();
		return usr;
	}
}