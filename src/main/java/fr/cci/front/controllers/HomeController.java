package fr.cci.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.cci.front.service.PlayerService;

@Controller
public class HomeController {

	private PlayerService playerService;

	public HomeController(final PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("users", playerService.get());
		return mav;
	}

	@GetMapping("/admin")
	public ModelAndView admin() {
		return new ModelAndView("admin");
	}

}