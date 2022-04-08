package ru.common.micro.social.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.common.micro.social.data.UserRepository;
import ru.common.micro.social.data.dto.User;

import java.util.Collections;
import java.util.List;

@Service()
public class MsnUserServiceImpl implements MsnUserService {

	private final UserRepository repository;

	@Autowired
	public MsnUserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = this.repository.findByLogin(login);
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRoles()));
	}

	public User getUserData(String login) throws UsernameNotFoundException {
		User user = this.repository.findByLogin(login);
		return user;
	}

	public boolean saveUser(User user) {
		User userFromDB = null;

		try{
			userFromDB = repository.findByLogin(user.getLogin());
		} catch (Exception e){
			System.out.println("User " + user.getLogin() + " not found");
		}

		if (userFromDB != null) {
			return false;
		}

		user.setId(User.idGenerator());
		user.setRoles("ROLE_USER");
		user.setPassword(User.PASSWORD_ENCODER.encode(user.getPassword()));
		repository.save(user);
		return true;
	}

	@Override
	public List<User> getFriends(String login) {
		List<User> users = this.repository.findFriendsByLogin(login);
		return users;
	}

	@Override
	public List<User> getNotFriends(String login) {
		List<User> users = this.repository.findNotFriendsByLogin(login);
		return users;
	}

	@Override
	public int updateFriends(List<String> friendLogins, String userLogin) {
		return this.repository.updateFriends(friendLogins, userLogin);
	}

	@Override
	public int removeFriend(String friendLogin, String login) {
		return this.repository.removeFriend(friendLogin, login);
	}
}
