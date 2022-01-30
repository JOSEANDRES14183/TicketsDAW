package com.daw.ticketsdaw;

import com.daw.ticketsdaw.Converters.StringToOrganitzadorConverter;
import com.daw.ticketsdaw.Interceptors.SalaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addFormatters(FormatterRegistry registry){
        //registry.addConverter(new StringToOrganitzadorConverter());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/*")
                .addResourceLocations("file:" + env.getProperty("tickets.uploads.path"));
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SalaInterceptor()).addPathPatterns("/salas");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }

}
