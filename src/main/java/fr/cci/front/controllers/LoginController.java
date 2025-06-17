package fr.cci.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.model.UserModel;
import fr.cci.front.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private TokenContext tokenContext;
    private UserService userService;

    public LoginController(final UserService userService,
                           final TokenContext tokenContext) {
        this.userService = userService;
        this.tokenContext = tokenContext;
    }

    @GetMapping("/login")
    public String loginDisplay(Model model) {
        model.addAttribute("user", new UserModel());
        return "login";
    }

    @PostMapping("/login")
    public RedirectView loginSubmit(@ModelAttribute UserModel user, HttpSession httpSession) {
        String token = userService.login(user);
        tokenContext.setToken(token);

        UserModel connectedUser = userService.getUserInformation();
        httpSession.setAttribute("user", connectedUser);

        return new RedirectView("/");
    }


}