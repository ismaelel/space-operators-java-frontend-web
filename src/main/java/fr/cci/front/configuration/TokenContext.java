package fr.cci.front.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Composant de session permettant de stocker le token JWT et le rôle de l'utilisateur connecté.
 * <p>
 * Ce bean est instancié une fois par session utilisateur grâce à {@code @SessionScope}.
 */
@Component
@SessionScope
public class TokenContext {

    /**
     * Token JWT de l'utilisateur courant.
     */
    private String token;

    /**
     * Rôle de l'utilisateur courant (ex : ROLE_USER, ROLE_ADMIN).
     */
    private String role;

    /**
     * Récupère le token JWT stocké en session.
     *
     * @return le token actuel
     */
    public String getToken() {
        return token;
    }

    /**
     * Définit le token JWT pour la session en cours.
     *
     * @param token le nouveau token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Récupère le rôle de l'utilisateur courant.
     *
     * @return rôle actuel
     */
    public String getRole() {
        return role;
    }

    /**
     * Définit le rôle de l'utilisateur courant.
     *
     * @param role rôle à assigner
     */
    public void setRole(String role) {
        this.role = role;
    }
}
