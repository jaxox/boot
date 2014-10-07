package com.boot;

import com.boot.adapter.SimpleSignInAdapter;
import com.boot.security.RestAuthenticationProvider;
import com.boot.security.SpringSecurityAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableAutoConfiguration
@ComponentScan
@PropertySource("classpath:/application.properties")
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Bean
    public SignInAdapter signInAdapter() {
        return new SimpleSignInAdapter(new HttpSessionRequestCache());
    }

    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Bean
    public AuthenticationSecurity authenticationSecurity() {
        return new AuthenticationSecurity();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SpringSecurityAuditorAware springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }



    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private org.springframework.boot.autoconfigure.security.SecurityProperties security;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //TODO: find out what exactly is that and how does it work in Restful way




            //http.authorizeRequests().antMatchers("/api/secure/**").fullyAuthenticated();
            http.csrf().disable()
//               .formLogin()
//                    .loginPage("/signin")
//                    .loginProcessingUrl("/signin/authenticate")
//                    .failureUrl("/signin?param.error=bad_credentials")
//               .and()
                    .logout()
                        .logoutUrl("/signout")
                        .deleteCookies("JSESSIONID")
               .and()
                    .authorizeRequests()
                        .antMatchers("/admin/**", "/favicon.ico", "/resources/**", "/auth/**", "/api/login", "/signup/**", "/disconnect/facebook").permitAll()
                        .antMatchers("/**").permitAll();//authenticated();

            //Permit All
//            http
//                .formLogin().loginPage("/signin")
//                .failureUrl("/signin?param.error=bad_credentials")
//                .permitAll();
//
//            http.authorizeRequests().antMatchers("/**").permitAll();

        }
    }


    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {


        @Autowired
        private RestAuthenticationProvider restAuthenticationProvider;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(restAuthenticationProvider);
        }
    }

}