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

/**
 * Contrôleur pour la gestion des utilisateurs (profil, dashboard, édition, suppression).
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final TokenContext tokenContext;
    private PlayerService playerService;

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param tokenContext contexte du token JWT
     * @param playerService service de gestion des joueurs
     */
    public UserController(TokenContext tokenContext, PlayerService playerService) {
        this.tokenContext = tokenContext;
        this.playerService = playerService;
    }

    /**
     * Affiche le profil de l'utilisateur connecté.
     * Récupère le token depuis la session et met à jour le contexte token.
     *
     * @param model modèle pour la vue
     * @param session session HTTP pour récupérer le JWT et infos utilisateur
     * @return nom de la vue du profil utilisateur
     */
    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        tokenContext.setToken(token);

        PlayerModel playerProfile = playerService.getUserInformation();
        model.addAttribute("user", playerProfile);

        return "user/profile";
    }

    /**
     * Affiche la liste de tous les utilisateurs.
     *
     * @return ModelAndView avec la liste des utilisateurs et la vue "user/users"
     */
    @GetMapping("/all")
    public ModelAndView getAll() {
        ModelAndView mav = new ModelAndView("user/users");
        mav.addObject("users", playerService.get());
        return mav;
    }

    /**
     * Affiche le dashboard des utilisateurs, avec la liste des joueurs.
     * Possibilité de passer un paramètre d'erreur affiché dans la vue.
     *
     * @param model modèle pour la vue
     * @param error message d'erreur optionnel (via paramètre URL)
     * @return nom de la vue "user/dashboard"
     */
    @GetMapping("/dashboard")
    public String showUserDashboard(Model model, @RequestParam(required = false) String error) {
        List<PlayerModel> users = playerService.getAllPlayers();
        model.addAttribute("users", users);
        model.addAttribute("error", error);
        return "user/dashboard";
    }

    /**
     * Affiche le formulaire d'édition d'un utilisateur.
     * L'accès est autorisé seulement à un administrateur ou à l'utilisateur lui-même.
     * Sinon redirection vers l'accueil avec message d'erreur.
     *
     * @param id identifiant de l'utilisateur à éditer
     * @param model modèle pour la vue
     * @param session session HTTP pour récupérer l'utilisateur connecté
     * @return nom de la vue "user/edit" ou redirection si non autorisé
     */
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable String id, Model model, HttpSession session) {
        PlayerModel currentUser = (PlayerModel) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/login";
        }

        boolean isAdmin = "ROLE_ADMIN".equals(currentUser.getRole());
        boolean isSelf = currentUser.getPlayerId().equals(id);

        if (!isAdmin && !isSelf) {
            return "redirect:/?error=unauthorized";
        }

        PlayerModel user = playerService.getPlayerById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    /**
     * Traite la mise à jour d'un utilisateur via POST.
     *
     * @param user modèle utilisateur soumis par le formulaire
     * @return redirection vers le dashboard utilisateur
     */
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") PlayerModel user) {
        playerService.updatePlayer(user);
        return "redirect:/user/dashboard";
    }

    /**
     * Supprime un utilisateur par son ID.
     * L'utilisateur ne peut pas se supprimer lui-même (redirige avec message d'erreur).
     *
     * @param id identifiant de l'utilisateur à supprimer
     * @param session session HTTP pour vérifier l'utilisateur connecté
     * @return redirection vers le dashboard utilisateur
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, HttpSession session) {
        PlayerModel currentUser = (PlayerModel) session.getAttribute("user");

        if (currentUser.getPlayerId().equals(id)) {
            // Interdit de se supprimer soi-même
            return "redirect:/user/dashboard?error=self-deletion-not-allowed";
        }

        playerService.deletePlayer(id);
        return "redirect:/user/dashboard";
    }
}
