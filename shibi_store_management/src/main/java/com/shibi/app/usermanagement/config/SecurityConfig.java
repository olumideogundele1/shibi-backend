package com.shibi.app.usermanagement.config;

import com.shibi.app.usermanagement.config.UserSecurityService;
import com.shibi.app.utils.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by User on 07/06/2018.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
   private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private Environment env;

    private BCryptPasswordEncoder passwordEncoder(){
        return SecurityUtility.passwordEncoder();
    }


    private static final String [] PUBLIC_MATCHERS = {
            "/**/css/**",
            "/**/js/**",
            "/**/image/**",
            "/**/user/**",
            "/**/login",
            "/**/password/**",
            "/**/role/**",
            "/**/checkSession",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
//              "/**/swagger**",
//            "/newUser",
//            "/forgetPassword",
//            "/


    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/user/**").permitAll()
                .anyRequest().permitAll() //All request must be authenticated


                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint); //to changing the entry point instead of /login

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/");

//        http
//                .cors().and()
//                .csrf().disable()
//                .authorizeRequests().antMatchers("/").permitAll().
//                anyRequest().permitAll()
//                .and()
//                .requestCache()
//                .requestCache(new NullRequestCache());
////                .and()
////                .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
////                .and().logout().logoutUrl("/user/logout").logoutSuccessUrl("/user/all-users").and().exceptionHandling();
//
//
////        http
////                .logout()
////                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"));
//
//
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(1)
//                .expiredUrl("/");
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
        }

}
