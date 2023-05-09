[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/edemkxOB)


# Articles CMS

В этом проекте совмещены все предыдущие домашки (ViewResolver, LocaleResolver, LocaleConverter). Могут присутствовать недоработки и лишние файлы.

### Cледующие дз выполены:
- 7.1 (m2m + advanced pagination)
- 7.2 CMS

### Что реализовано в CMS
- [x] WYSWIG (tinyMCE)
- [x] SLUG (slugify)
- [x] [Inteceptor](src/main/java/ru/kpfu/itis/gnt/registration/config/SlugInterceptor.java) (не всегда отрабатывающий)
- [x] Filter script (на основе jsoup)

## m2m (article-category)
![Image](images/m2m.png)

## Пагинация
<p align="middle">
   <img src="/images/p1.png" width="200"/>
   <img src="/images/p2.png" width="200"/>
   <img src="/images/p3.png" width="200"/>
</p>
<p align="middle">
   <img src="/images/p4.png" width="200"/>
   <img src="/images/p5.png" width="200"/>
   <img src="/images/p6.png" width="200"/>
</p>

## WYSWIG
<p align="middle">
   <img src="/images/editing.png" width="600"/>
</p>

## Slug
<p align="middle">
   <img src="/images/slug.png" width="600"/>
</p>

## Security

```Java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/article/delete/**").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/article/create/**").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/article/update/**").hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers("/article/list").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin()
                .loginPage(User.LOGIN)
                .failureHandler((request, response, exception) -> response.sendRedirect(User.LOGIN + "?error=" + exception.getMessage()))
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl(User.LOGOUT).logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage(PathConstants.Error.ACCESS_DENIED)
                .and()
                .csrf().disable();
        return http.build();
    }
```


## Что можно улучшить
- Не совсем понятно, как обрабатывать исключения с помощью CriteriaBuilder.
- Возможно, пагинация не полностью соответвует условию визуально, но работает правильно и стабильно. 
- Перечислять категории через запятую, да и в целом их как-то визуально выделять.
- Не удалось нормально настроить interceptor. Он не срабатывает на все ссылки, хоть и установлен путь "/**". [(Webconfig)](/src/main/java/ru/kpfu/itis/gnt/registration/config/WebConfig.java) Можно было сделать через HandlerMapping.


