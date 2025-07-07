package fr.cci.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur gérant la page d'accès refusé.
 * <p>
 * Permet d'afficher une vue lorsque l'utilisateur tente d'accéder à une ressource
 * pour laquelle il n'a pas les droits suffisants.
 * </p>
 */
@Controller
public class ErrorController {

    /**
     * Affiche la page d'accès refusé (HTTP 403).
     *
     * @return le nom de la vue Thymeleaf "access-denied"
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // nom de la vue (Thymeleaf)
    }
}
