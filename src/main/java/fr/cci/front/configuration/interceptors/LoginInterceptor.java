package fr.cci.front.configuration.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.cci.front.configuration.TokenContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Intercepteur de sécurité qui vérifie si un utilisateur est connecté
 * en contrôlant la présence d'un token dans le {@link TokenContext}.
 * <p>
 * Si aucun token n'est présent, la requête est redirigée vers la page de login.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final TokenContext tokenContext;

    /**
     * Constructeur avec injection du {@link TokenContext}.
     *
     * @param tokenContext le contexte de session contenant le token JWT
     */
    public LoginInterceptor(final TokenContext tokenContext) {
        this.tokenContext = tokenContext;
    }

    /**
     * Méthode appelée avant l'exécution du contrôleur.
     * Vérifie si l'utilisateur est authentifié (présence du token).
     *
     * @param request  la requête HTTP entrante
     * @param response la réponse HTTP sortante
     * @param handler  le handler cible (méthode de contrôleur)
     * @return {@code true} si la requête peut continuer, {@code false} sinon
     * @throws Exception en cas d'erreur d'exécution
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (tokenContext.getToken() == null) {
            response.sendRedirect("/login");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
