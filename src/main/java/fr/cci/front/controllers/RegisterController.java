package fr.cci.front.controllers;

import fr.cci.front.model.PlayerModel;
import fr.cci.front.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;



@Controller
public class RegisterController {

	private PlayerService playerService;

	public RegisterController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping("/register")
	public String registerDisplay(Model model, HttpSession session) {
		if (session.getAttribute("jwt") != null) {
			return "redirect:/user/profile";
		}
		model.addAttribute("user", new PlayerModel());
		return "register";
	}

	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute PlayerModel user, Model model) {
		try {
			playerService.add(user);
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
