package ru.common.micro.social.ru.common.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.common.micro.social.data.dto.User;
import ru.common.micro.social.services.MsnUserService;
import ru.common.micro.social.services.MsnUserServiceImpl;

import javax.validation.Valid;

@Controller
public class MvcController {

    final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	MsnUserService msnUserService;

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}

	@PostMapping("/register")
	public String register(
            @ModelAttribute("register-form") @Valid User userForm,
            BindingResult bindingResult,
            Model model
	) {
		boolean result = msnUserService.saveUser(userForm);
		if(result) {
			return "success-registration";
		}
		model.addAttribute("login", userForm.getLogin());
		return "wrong-login";
	}
}