package fr.cci.front.controllers;

import fr.cci.front.model.PlayerModel;
import fr.cci.front.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import fr.cci.front.configuration.TokenContext;
import jakarta.servlet.http.HttpSession;

/**
 * Contrôleur Spring MVC pour la gestion de la connexion utilisateur.
 * <p>
 * Gère l'affichage du formulaire de connexion, la soumission des identifiants,
 * ainsi que la déconnexion.
 * </p>
 */
@Controller
public class LoginController {

    private TokenContext tokenContext;
    private PlayerService playerService;

    /**
     * Constructeur avec injection des dépendances nécessaires.
     *
     * @param playerService service de gestion des joueurs
     * @param tokenContext  contexte pour gérer le token JWT
     */
    public LoginController(final PlayerService playerService,
                           final TokenContext tokenContext) {
        this.playerService = playerService;
        this.tokenContext = tokenContext;
    }

    /**
     * Affiche la page de connexion.
     * <p>
     * Si un utilisateur est déjà connecté (présence du token JWT en session),
     * redirige vers la page d'accueil.
     * Sinon, affiche le formulaire de connexion avec un modèle vide.
     * </p>
     *
     * @param model   modèle pour la vue
     * @param session session HTTP pour vérifier la présence d'un JWT
     * @return nom de la vue à afficher ou redirection
     */
    @GetMapping("/login")
    public String loginDisplay(Model model, HttpSession session) {
        if (session.getAttribute("jwt") != null) {
            return "redirect:/";
        }
        model.addAttribute("user", new PlayerModel());
        return "auth/login";
    }

    /**
     * Traite la soumission du formulaire de connexion.
     * <p>
     * Tente de connecter l'utilisateur avec les identifiants fournis.
     * En cas de succès, stocke le token JWT et les informations utilisateur en session.
     * En cas d'erreur (identifiants invalides ou autre), affiche un message d'erreur.
     * </p>
     *
     * @param user        données du formulaire (email, mot de passe)
     * @param model       modèle pour la vue
     * @param httpSession session HTTP pour stocker les données utilisateur et token
     * @return redirection vers la page d'accueil en cas de succès, sinon reste sur la page de connexion
     */
    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute PlayerModel user, Model model, HttpSession httpSession) {
        try {
            String token = playerService.login(user);
            tokenContext.setToken(token);

            PlayerModel connectedUser = playerService.getUserInformation();

            httpSession.setAttribute("user", connectedUser);
            httpSession.setAttribute("jwt", token);

            return "redirect:/";

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401 || e.getStatusCode().value() == 400) {
                model.addAttribute("error", "Email ou mot de passe incorrect.");
            } else {
                model.addAttribute("error", "Erreur technique : " + e.getStatusCode());
            }

            model.addAttribute("user", user); // pour pré-remplir le formulaire
            return "auth/login"; // reste sur login.html
        }
    }

    /**
     * Déconnecte l'utilisateur en invalidant la session HTTP et en supprimant le token JWT.
     *
     * @param session session HTTP à invalider
     * @return redirection vers la page de connexion
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        tokenContext.setToken(null);
        return "redirect:/login";
    }
}
