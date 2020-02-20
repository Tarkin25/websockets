package ch.noseryoung.websockets.config;

import ch.noseryoung.websockets.WebsocketsApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LoggerConfiguration {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(WebsocketsApplication.class);
    }

    //@Bean
    @Primary
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

}
