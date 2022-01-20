package com.daw.ticketsdaw;

import com.daw.ticketsdaw.Converters.StringToOrganitzadorConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
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
        registry.addResourceHandler("/resources/*.png")
                .addResourceLocations("/resources/", "file:" + env.getProperty("tickets.uploads.path"));
    }
}
