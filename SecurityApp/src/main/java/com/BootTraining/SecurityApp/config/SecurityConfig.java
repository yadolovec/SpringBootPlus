package com.BootTraining.SecurityApp.config;

import com.BootTraining.SecurityApp.services.PersonDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailService personDetailService;

    public SecurityConfig(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(personDetailService).passwordEncoder(getPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception{
        http    .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration","/error").permitAll()
                .anyRequest().hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login")
        ;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
