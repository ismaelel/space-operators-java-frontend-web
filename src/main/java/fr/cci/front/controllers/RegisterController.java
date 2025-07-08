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

/**
 * Contrôleur gérant l'inscription des utilisateurs.
 * <p>
 * Affiche le formulaire d'inscription, traite les soumissions et gère les erreurs.
 * </p>
 */
@Controller
public class RegisterController {

	private PlayerService playerService;

	/**
	 * Constructeur avec injection du service PlayerService.
	 *
	 * @param playerService service de gestion des joueurs
	 */
	public RegisterController(PlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Affiche la page d'inscription.
	 * <p>
	 * Si l'utilisateur est déjà connecté (token JWT en session),
	 * redirige vers la page d'accueil.
	 * Sinon, affiche un formulaire vide pour la saisie des données.
	 * </p>
	 *
	 * @param model   modèle pour la vue
	 * @param session session HTTP pour vérifier la connexion
	 * @return nom de la vue "register" ou redirection vers "/"
	 */
	@GetMapping("/register")
	public String registerDisplay(Model model, HttpSession session) {
		if (session.getAttribute("jwt") != null) {
			return "redirect:/";
		}
		model.addAttribute("user", new PlayerModel());
		return "auth/register";
	}

	/**
	 * Traite la soumission du formulaire d'inscription.
	 * <p>
	 * Tente d'ajouter un nouvel utilisateur via PlayerService.
	 * En cas d'erreur, affiche un message adapté selon le code HTTP reçu.
	 * </p>
	 *
	 * @param user  données issues du formulaire d'inscription
	 * @param model modèle pour la vue
	 * @return redirection vers la page de login en cas de succès,
	 * sinon retour sur la page d'inscription avec message d'erreur
	 */
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
			return "auth/register";
		}
	}
}
