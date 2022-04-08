package ru.common.micro.social.ru.common.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.common.micro.social.data.dto.User;
import ru.common.micro.social.services.MsnUserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	MsnUserServiceImpl userService;

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/username")
	public ResponseEntity<String> getUserName(){
		String currentUserName = "Anonymous";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		return ResponseEntity.ok()
				.body(currentUserName);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/userDetails")
	public ResponseEntity<User> getUserDetails(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String login = authentication.getName();
			currentUser = userService.getUserData(login);
		} else {
			currentUser = userService.getUserData("oliver");//TODO:: remove!
		}

		return ResponseEntity.ok()
				.body(currentUser);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getFriends")
	public ResponseEntity<List<User>> getFriends(){
        String login = getCurrentUserLogin();

		List<User> users = userService.getFriends(login);

		return ResponseEntity.ok()
				.body(users);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getNotFriends")
	public ResponseEntity<List<User>> getNotFriends(){
        String login = getCurrentUserLogin();

		List<User> users = userService.getNotFriends(login);

		return ResponseEntity.ok()
				.body(users);
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/updateFriends")
    public ResponseEntity<Integer> updateFriends(@RequestBody ArrayList<String> friendLogins){
        String login = getCurrentUserLogin();

        int count = userService.updateFriends(friendLogins, login);

        return ResponseEntity.ok()
                .body(count);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/removeFriend")
    public ResponseEntity<Integer> removeFriend(@RequestBody String friendLogin){
        String login = getCurrentUserLogin();

        int count = userService.removeFriend(friendLogin, login);

        return ResponseEntity.ok()
                .body(count);
    }

    private String getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            login = authentication.getName();
        } else {
            //login = "oliver"; //TODO:: remove!
            throw new IllegalArgumentException();
        }
        return login;
    }
}