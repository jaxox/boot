package com.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
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
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

//    @Bean
//    public AuthenticationSecurity authenticationSecurity() {
//        return new AuthenticationSecurity();
//    }


    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private org.springframework.boot.autoconfigure.security.SecurityProperties security;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //TODO: find out what exactly is that and how does it work in Restful way
            http.csrf().disable();



            http.authorizeRequests().antMatchers("/api/secure/**").fullyAuthenticated();


            //Permit All
            http
                .formLogin().loginPage("/login")
                .failureUrl("/login?error")
                .permitAll();

            http.authorizeRequests().antMatchers("/**").permitAll();

        }
    }


//    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
//    protected static class AuthenticationSecurity extends  GlobalAuthenticationConfigurerAdapter {
//
////        @Autowired
////        private DataSource dataSource;
//
//        @Override
//        public void init(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(new RestAuthenticationProvider());
//
////            auth.jdbcAuthentication().dataSource(this.dataSource).withUser("admin")
////                    .password("admin").roles("ADMIN", "USER").and().withUser("user")
////                    .password("user").roles("USER");
//        }
//    }

}