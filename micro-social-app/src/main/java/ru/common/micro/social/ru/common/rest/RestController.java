package ru.common.micro.social.ru.common.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.common.micro.social.dao.dto.User;
import ru.common.micro.social.dto.SearchUserRequest;
import ru.common.micro.social.services.MsnUserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    MsnUserServiceImpl userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/username")
    public ResponseEntity<String> getUserName() {
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
    public ResponseEntity<User> getUserDetails() {
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
    public ResponseEntity<List<User>> getFriends() {
        String login = getCurrentUserLogin();

        List<User> users = userService.getFriends(login);

        return ResponseEntity.ok()
                .body(users);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getNotFriends")
    public ResponseEntity<List<User>> getNotFriends() {
        String login = getCurrentUserLogin();

        List<User> users = userService.getNotFriends(login);

        return ResponseEntity.ok()
                .body(users);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/updateFriends")
    public ResponseEntity<Integer> updateFriends(@RequestBody ArrayList<String> friendLogins) {
        String login = getCurrentUserLogin();

        int count = userService.updateFriends(friendLogins, login);

        return ResponseEntity.ok()
                .body(count);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/removeFriend")
    public ResponseEntity<Integer> removeFriend(@RequestBody String friendLogin) {
        String login = getCurrentUserLogin();

        int count = userService.removeFriend(friendLogin, login);

        return ResponseEntity.ok()
                .body(count);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/search")
    public ResponseEntity<List<User>> search(@RequestBody SearchUserRequest rq) {
        List<User> users = userService.search(rq.getFirstName(), rq.getLastName(), rq.getMaxCount());

        return ResponseEntity.ok()
                .body(users);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchGet(@RequestParam String firstName,
                                                @RequestParam String lastName,
                                                @RequestParam Integer maxCount) {

        List<User> users = userService.search(firstName, lastName, maxCount);

        return ResponseEntity.ok()
                .body(users);
    }

    private String getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            login = authentication.getName();
        } else {
            throw new IllegalArgumentException();
        }
        return login;
    }
}