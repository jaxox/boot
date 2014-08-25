package com.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:/application.properties")
public class ApplicationJavaMail {

    @Value("${mail.server.host}")
    String host;
    @Value("${mail.server.port}")
    int port;
    @Value("${mail.server.protocol}")
    String protocoal;
    @Value("${mail.server.username}")
    String username;
    @Value("${mail.server.password}")
    String password;

    @Bean
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setProtocol(protocoal);
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        return javaMailSender;
    }




}