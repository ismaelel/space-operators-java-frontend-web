package fr.cci.front.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.cci.front.configuration.interceptors.LoginInterceptor;
import fr.cci.front.configuration.interceptors.AdminInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private LoginInterceptor loginInterceptor;
    private AdminInterceptor roleInterceptor;

    public WebConfiguration(
            final LoginInterceptor loginInterceptor,
            final AdminInterceptor roleInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.roleInterceptor = roleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/user/dashboard");
    }
}