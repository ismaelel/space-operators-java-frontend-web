package fr.cci.front.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.cci.front.configuration.interceptors.LoginInterceptor;
import fr.cci.front.configuration.interceptors.AdminInterceptor;

/**
 * Configuration personnalisée de Spring MVC.
 * Permet d’enregistrer les intercepteurs (middleware) qui s'exécutent avant les contrôleurs.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Intercepteur chargé de vérifier si un utilisateur est connecté.
     * (non utilisé ici, mais prêt à être intégré).
     */
    private final LoginInterceptor loginInterceptor;

    /**
     * Intercepteur qui vérifie si l'utilisateur a le rôle ADMIN.
     * Utilisé pour restreindre l'accès à certaines routes.
     */
    private final AdminInterceptor roleInterceptor;

    /**
     * Constructeur d’injection des intercepteurs.
     *
     * @param loginInterceptor intercepteur de connexion
     * @param roleInterceptor  intercepteur de rôle (admin)
     */
    public WebConfiguration(
            final LoginInterceptor loginInterceptor,
            final AdminInterceptor roleInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.roleInterceptor = roleInterceptor;
    }

    /**
     * Méthode appelée automatiquement par Spring pour enregistrer les intercepteurs.
     *
     * @param registry registre des intercepteurs
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/user/dashboard"); // Intercepte uniquement cette route
    }
}
