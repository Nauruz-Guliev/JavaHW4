package ru.kpfu.itis.gnt.registration.config;

import com.github.slugify.Slugify;
import org.mapstruct.factory.Mappers;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.kpfu.itis.gnt.registration.config.messagesource.CustomDatabaseMessageSource;
import ru.kpfu.itis.gnt.registration.data.repository.articlecategory.ArticleCategoryRepository;
import ru.kpfu.itis.gnt.registration.data.repository.articlecategory.ArticleCategoryRepositoryImpl;
import ru.kpfu.itis.gnt.registration.data.repository.message.MessageSourceRepository;
import ru.kpfu.itis.gnt.registration.data.repository.user.UserRepository;
import ru.kpfu.itis.gnt.registration.data.repository.user.UserRepositoryImpl;
import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.mapper.ArticleMapper;
import ru.kpfu.itis.gnt.registration.mapper.CategoryMapper;
import ru.kpfu.itis.gnt.registration.mapper.UserMapper;
import ru.kpfu.itis.gnt.registration.security.SecurityConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;


@Configuration
@Import(SecurityConfig.class)
@ComponentScan(basePackages = {"ru.kpfu.itis.gnt.registration.data.repository", "ru.kpfu.itis.gnt.registration.services"})
public class RootConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("JPA_LOCAL");
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public DataSource dataSource() {
        return new ru.kpfu.itis.gnt.registration.data.DataSource().get();
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public ArticleMapper articleMapper() {
        return Mappers.getMapper(ArticleMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Slugify slugify() {
        return Slugify
                .builder()
                .underscoreSeparator(true)
                .lowerCase(true)
                .build();
    }


}
