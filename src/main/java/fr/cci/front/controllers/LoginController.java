package fr.cci.front.controllers;

import fr.cci.front.model.PlayerModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
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
    public String loginDisplay(Model model, HttpSession session) {
        if (session.getAttribute("jwt") != null) {
            return "redirect:/user/profile";
        }
        model.addAttribute("user", new UserModel());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute UserModel user, Model model, HttpSession httpSession) {
        try {
            String token = userService.login(user);
            tokenContext.setToken(token);

            PlayerModel connectedUser = userService.getUserInformation();

            httpSession.setAttribute("user", connectedUser);
            httpSession.setAttribute("jwt", token);

            return "redirect:/user/profile";

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 400) {
                model.addAttribute("error", "Email ou mot de passe incorrect.");
            } else {
                model.addAttribute("error", "Erreur technique : " + e.getStatusCode());
            }

            model.addAttribute("user", user); // pour pré-remplir le formulaire
            return "login"; // reste sur login.html
        }


    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        tokenContext.setToken(null);
        return "redirect:/login";
    }



}