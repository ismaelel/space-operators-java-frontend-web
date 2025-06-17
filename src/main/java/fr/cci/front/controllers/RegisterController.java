package fr.cci.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import fr.cci.front.model.UserModel;
import fr.cci.front.service.UserService;

@Controller
public class RegisterController {

	private UserService userService;

	public RegisterController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String registerDisplay(Model model) {
		model.addAttribute("user", new UserModel());
		return "register";
	}

	@PostMapping("/register")
	public RedirectView registerSubmit(@ModelAttribute UserModel user) {
		userService.add(user);
		return new RedirectView("/login");
	}

}
