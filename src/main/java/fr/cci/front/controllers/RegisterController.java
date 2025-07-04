package fr.cci.front.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
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
	public String registerDisplay(Model model, HttpSession session) {
		if (session.getAttribute("jwt") != null) {
			return "redirect:/user/profile";
		}
		model.addAttribute("user", new UserModel());
		return "register";
	}

	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute UserModel user, Model model) {
		try {
			userService.add(user);
			return "redirect:/login";
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 409) {
				model.addAttribute("error", "Un compte avec cette adresse email existe déjà.");
			} else if (e.getStatusCode().value() == 400) {
				model.addAttribute("error", "Données invalides. Merci de vérifier le formulaire.");
			} else {
				model.addAttribute("error", "Erreur technique : " + e.getStatusCode());
			}

			model.addAttribute("user", user);
			return "register";
		}
	}


}
