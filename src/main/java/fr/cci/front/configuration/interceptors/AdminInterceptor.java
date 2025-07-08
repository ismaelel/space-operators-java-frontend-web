package fr.cci.front.configuration.interceptors;

import fr.cci.front.model.PlayerModel;
import fr.cci.front.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Intercepteur qui vérifie que l'utilisateur connecté est un administrateur.
 * <p>
 * Si aucun utilisateur n'est connecté ou si l'utilisateur n'a pas le rôle "ROLE_ADMIN",
 * il est redirigé vers la page de login ou la page d'accès refusé.
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final PlayerService playerService;

    /**
     * Constructeur avec injection du service joueur.
     *
     * @param playerService service pour récupérer les informations du joueur connecté
     */
    public AdminInterceptor(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Méthode appelée avant l'exécution du contrôleur.
     * Vérifie que l'utilisateur connecté a le rôle administrateur.
     *
     * @param request  requête HTTP entrante
     * @param response réponse HTTP sortante
     * @param handler  handler cible (méthode contrôleur)
     * @return true si l'utilisateur est admin, false sinon (avec redirection)
     * @throws Exception en cas d'erreur
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("jwt") == null) {
            response.sendRedirect("/login");
            return false;
        }

        PlayerModel player = playerService.getUserInformation();

        if (player == null || !"ROLE_ADMIN".equalsIgnoreCase(player.getRole())) {
            response.sendRedirect("/access-denied");
            return false;
        }

        return true;
    }
}
