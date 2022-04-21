package ru.common.micro.social.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.common.micro.social.dao.dto.User;

import java.util.List;

public interface MsnUserService extends UserDetailsService {
    User getUserData(String login);
    boolean saveUser(User user);

    List<User> getFriends(String login);

    List<User> getNotFriends(String login);

    int updateFriends(List<String> friendLogins, String userLogin);

    int removeFriend(String friendLogin, String login);
}
