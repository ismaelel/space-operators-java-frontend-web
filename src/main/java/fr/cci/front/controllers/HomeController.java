package fr.cci.front.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.cci.front.service.PlayerService;

/**
 * Contrôleur gérant la page d'accueil et la page d'administration.
 * <p>
 * Permet de rediriger vers la page de connexion si l'utilisateur n'est pas connecté,
 * d'afficher la liste des utilisateurs et de gérer l'affichage d'une page admin.
 * </p>
 */
@Controller
public class HomeController {

	private PlayerService playerService;

	/**
	 * Constructeur avec injection du service PlayerService.
	 *
	 * @param playerService service de gestion des joueurs
	 */
	public HomeController(final PlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * Affiche la page d'accueil.
	 * <p>
	 * Si l'utilisateur n'est pas connecté (absence d'attribut "user" en session),
	 * redirige vers la page de login.
	 * Sinon, affiche la vue "index" avec la liste des utilisateurs et un éventuel message d'erreur.
	 * </p>
	 *
	 * @param session session HTTP pour vérifier la connexion
	 * @param error   message d'erreur optionnel passé en paramètre d'URL
	 * @return ModelAndView correspondant à la vue "index" ou redirection vers "/login"
	 */
	@GetMapping("/")
	public ModelAndView home(HttpSession session, @RequestParam(required = false) String error) {
		if (session.getAttribute("user") == null) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("error", error);
		mav.addObject("users", playerService.get());
		return mav;
	}

	/**
	 * Affiche la page d'administration.
	 *
	 * @return ModelAndView correspondant à la vue "admin"
	 */
	@GetMapping("/admin")
	public ModelAndView admin() {
		return new ModelAndView("admin");
	}
}
