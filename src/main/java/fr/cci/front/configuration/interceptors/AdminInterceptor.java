package fr.cci.front.configuration.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.cci.front.model.UserModel;
import fr.cci.front.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private UserService userService;

    public AdminInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        UserModel user = userService.getUserInformation();

        if(user == null || !user.getRoles().contains("ADMIN")) {
            response.sendRedirect("/");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}