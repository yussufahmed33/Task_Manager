package com.example.Task.Maneger.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private  static final String LOGIN="/login";
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    BCryptPasswordEncoder passwordEncoder()throws Exception{
return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                .requestMatchers(LOGIN,"/register","/css/**","/js/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage(LOGIN)
                .permitAll()
                .loginProcessingUrl(LOGIN)
                .defaultSuccessUrl("/",true)
                .and()
                .logout()
                .permitAll();



        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()) 
                .and()
                .build();
    }

}
