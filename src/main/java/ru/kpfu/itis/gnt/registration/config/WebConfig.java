package ru.kpfu.itis.gnt.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.JstlView;
import ru.kpfu.itis.gnt.registration.config.localeresolver.CustomSessionLocaleResolver;
import ru.kpfu.itis.gnt.registration.config.messagesource.CustomDatabaseMessageSource;
import ru.kpfu.itis.gnt.registration.config.viewresolver.CustomViewResolver;
import ru.kpfu.itis.gnt.registration.data.repository.message.MessageSourceRepository;
import ru.kpfu.itis.gnt.registration.security.SecurityConfig;
import ru.kpfu.itis.gnt.registration.services.ArticleService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"ru.kpfu.itis.gnt.registration.controller", "ru.kpfu.itis.gnt.registration.advice"})
@Import(SecurityConfig.class)
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ArticleService service;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/js/**").addResourceLocations("/assets/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/assets/css/");
    }

    @Bean
    public ViewResolver viewResolver() {
        return CustomViewResolver.builder()
                .prefix("/WEB-INF/jsp/")
                .suffix(".jsp")
                .addStatusCodeSupport(404)
                .setRedirectContextRelative(false)
                .setViewClass(JstlView.class)
                .build();

    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Bean
    public MessageSource messageSource(MessageSourceRepository messageSourceRepository) {
        return new CustomDatabaseMessageSource(messageSourceRepository);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CustomSessionLocaleResolver resolver = new CustomSessionLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SlugInterceptor(service))
                .excludePathPatterns("/article/**")
                .excludePathPatterns("/user/**")
                .addPathPatterns("/**");
    }


}
