package fr.cci.front.configuration.interceptors;

import fr.cci.front.service.PlayerService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private PlayerService playerService;

    public AdminInterceptor(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //UserModel user = userService.getUserInformation();

//        if(user == null || !user.getRoles().contains("ADMIN")) {
//            response.sendRedirect("/");
//            return false;
//        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}