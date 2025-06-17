package fr.cci.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.cci.front.service.UserService;

@Controller
public class HomeController {

	private UserService userService;

	public HomeController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("users", userService.get());
		return mav;
	}

	@GetMapping("/admin")
	public ModelAndView admin() {
		return new ModelAndView("admin");
	}

}