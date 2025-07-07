package fr.cci.front.controllers;

import fr.cci.front.configuration.TokenContext;
import fr.cci.front.model.PlayerModel;
import fr.cci.front.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final TokenContext tokenContext;
    //private UserService userService;
    private PlayerService playerService;

    public UserController(TokenContext tokenContext, PlayerService playerService) {
        this.tokenContext = tokenContext;
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        System.out.println(".??????????????GET ATTRIBUTE : " + token);
        tokenContext.setToken(token);
        System.out.println(".??????????????GET ATTRIBUTE : " + tokenContext.getToken());

        PlayerModel playerProfile = playerService.getUserInformation();
        //UserModel userProfile = userService.getUserProfile(token);
        model.addAttribute("user", playerProfile);
        System.out.println("TOLKIEN : " + token);
        System.out.println("USER PROFILE : " + playerProfile);
        return "user/profile";
    }

    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView mav = new ModelAndView("user/users");
        mav.addObject("users", playerService.get());
        return mav;
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Model model) {
        List<PlayerModel> users = playerService.getAllPlayers();
        model.addAttribute("users", users);
        for (PlayerModel player : users) {
            System.out.println(player);
        }
        return "user/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable String id, Model model) {
        PlayerModel user = playerService.getPlayerById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") PlayerModel user) {
        playerService.updatePlayer(user);
        return "redirect:/user/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        playerService.deletePlayer(id);
        return "redirect:/user/dashboard";
    }

}
