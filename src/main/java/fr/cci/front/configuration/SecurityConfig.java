//package fr.cci.front.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().hasRole("ADMIN") // 🔐 accès réservé aux admins
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")         // page personnalisée
//                        .defaultSuccessUrl("/", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                );
//
//        return http.build();
//    }
//}
