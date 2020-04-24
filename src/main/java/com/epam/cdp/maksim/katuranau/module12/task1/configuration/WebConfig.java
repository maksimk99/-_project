package com.epam.cdp.maksim.katuranau.module12.task1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private LocaleChangeInterceptor localeChangeInterceptor;
    private ThemeChangeInterceptor themeChangeInterceptor;

    @Autowired
    public WebConfig(final LocaleChangeInterceptor localeChangeInterceptor,
                     final ThemeChangeInterceptor themeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
        this.themeChangeInterceptor = themeChangeInterceptor;
    }

    @Bean
    public Validator getValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(themeChangeInterceptor);
    }
}

