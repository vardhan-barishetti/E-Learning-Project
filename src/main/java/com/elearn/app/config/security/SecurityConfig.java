package com.elearn.app.config.security;

import com.elearn.app.dtos.CustomMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private AuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;



    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService (){
//
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("ram")
//                        .password("ram")
//                        .roles("ADMIN")
//                        .build()
//        );
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("shyam")
//                        .password("shyam")
//                        .roles("USER")
//                        .build()
//        );
//
//
//        return userDetailsManager;
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(cor->{
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("http://localhost:5173/");
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            config.setAllowCredentials(true);
            config.setMaxAge(300L);
            UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
            configurationSource.registerCorsConfiguration("/**", config);
            cor.configurationSource(configurationSource);
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(e->{
//            e.requestMatchers(HttpMethod.GET,"api/v1/categories").permitAll()
//                    .requestMatchers("/client-login", "/client-login-process").permitAll()
//                    .requestMatchers(HttpMethod.GET,"api/v1/courses").permitAll()
//                    .anyRequest()
//                    .authenticated();



            e
                    .requestMatchers("/doc.html","v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                    .requestMatchers("api/v1/auth/login").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/v1/**").hasRole("GUEST")
                    .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated();


            try {
                httpSecurity.sessionManagement(eAuth -> eAuth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            try {
                httpSecurity.exceptionHandling(auth->auth.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            CustomMessage customMessage = new CustomMessage();

                            customMessage.setMessage("You dont have permission to perform operation");
                            customMessage.setSuccess(false);

                            String stringMessage = new ObjectMapper().writeValueAsString(customMessage);

                            response.getWriter().println(stringMessage);
                        }));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

//        httpSecurity.formLogin(form->{
//            form.loginPage("/client-login");
//            form.usernameParameter("username");
//            form.passwordParameter("userpassword");
//            form.loginProcessingUrl("/client-login-process");
//            form.successForwardUrl("/success");
//        });
//
//        httpSecurity.logout(logout->{
//            logout.logoutUrl("/logout");
//        });

        //httpSecurity.httpBasic(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));

//        httpSecurity.exceptionHandling(ex -> {
//            ex.authenticationEntryPoint(authenticationEntryPoint);
//        });

        return httpSecurity.build();
    }
}
