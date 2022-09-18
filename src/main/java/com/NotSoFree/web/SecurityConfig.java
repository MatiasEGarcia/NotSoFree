package com.NotSoFree.web;

import com.NotSoFree.util.BCPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCPasswordEncoder bcPasswordEncoder;

    @Autowired/*here I enter the user's password*/
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(bcPasswordEncoder.passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/userC/auth/**").authenticated()
                .antMatchers("/userC/admin/**").hasRole("ADMIN")
                .antMatchers("/productC/admin/**").hasRole("ADMIN")
                .antMatchers("/personC/auth/**").authenticated()
                .antMatchers("/favoriteC/auth/**").authenticated()
                .antMatchers("/categoryC/admin/**").hasRole("ADMIN")
                .antMatchers("/cartC/auth/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/userC/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/userC/login?error")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
       

        return http.build();
    }

}
