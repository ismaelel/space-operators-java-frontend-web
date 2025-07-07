package fr.cci.front.configuration.interceptors;

import fr.cci.front.model.PlayerModel;
import fr.cci.front.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final PlayerService playerService;

    public AdminInterceptor(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("jwt") == null) {
            response.sendRedirect("/login");
            return false;
        }

        PlayerModel player = playerService.getUserInformation();
        System.out.println("PLAYER + " + player);
        System.out.println("ROLE + " + player.getRole());
        System.out.println("ADMIN? + " + "ROLE_ADMIN".equalsIgnoreCase(player.getRole()));
        if (player == null || !"ROLE_ADMIN".equalsIgnoreCase(player.getRole())) {
            response.sendRedirect("/access-denied");
            return false;
        }

        return true;
    }

}