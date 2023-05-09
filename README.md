[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/X_qLQTIg)

# Database Message Source and HttpSession Locale Resolver


### Настройка 
*  Реализуем интерфейс
``` Java

public interface MessageSourceRepository {
    Optional<MessageEntity> findByKeyAndLocale(String key, Locale locale);
    Optional<MessageEntity> findByKey(String key);
}

```
* Настраиваем бины
```Java
@Bean
public LocaleResolver localeResolver() {
    CustomSessionLocaleResolver resolver = new CustomSessionLocaleResolver();
    resolver.setDefaultLocale(Locale.US);
    return resolver;
}

@Bean
public MessageSource messageSource(MessageSourceRepository messageSourceRepository) {
    return new CustomDatabaseMessageSource(messageSourceRepository);
}
```
* Кладём локаль в сессию. 
```Java
(HttpServletRequest) request.getSession().setAttribute("locale","ru_RU");
```

### Пример работы
<p align="middle">
   <img src="/images/Снимок%20экрана%202023-05-06%20124702.png" width="400"/>
   <img src="/images/Снимок%20экрана%202023-05-06%20124459.png" width="400"/>
</p>

### Хранение в базе данных 
![Снимок](/images/Снимок%20экрана%202023-05-06%20124825.png)
![Снимок](/images/Снимок%20экрана%202023-05-06%20124847.png)

