package ru.kpfu.itis.gnt.registration.config.localeresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;


import java.util.Locale;

@Component
public class CustomSessionLocaleResolver extends AbstractLocaleResolver {
    private String localeAttributeName = "locale";
    @Override
    @NonNull
    public Locale resolveLocale(@Nullable HttpServletRequest httpServletRequest) {

        String rawLocale;
        try {
            rawLocale = (String) httpServletRequest.getSession().getAttribute(this.localeAttributeName);
            String[] localeValues = rawLocale.split("[-|_]");
            if (localeValues.length == 1) {
                return new Locale.Builder()
                        .setLanguage(localeValues[0])
                        .build();
            } else if (localeValues.length == 2) {
                return new Locale.Builder()
                        .setLanguage(localeValues[0])
                        .setRegion(localeValues[1])
                        .build();
            } else {
                // у Apache Commons есть метод для парсинга локалей. Если моя реализация не отработает
                // и будет введен какой-то необычный формат (не en_US или en-US, к примеру), то пусть парсингом занимается библиотека
                return LocaleUtils.toLocale(rawLocale);
            }
        } catch (NullPointerException ex) {
            return getDefaultLocale(httpServletRequest);
        }

    }

    @Override
    public void setLocale(@Nullable HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
        if (httpServletRequest != null) {
            // локаль не надо изменять, она будет в формате en_US, если перевести в строку
            // также можно хранить объект Locale вместо строки и кастить атрибут из сессии к Locale,
            // но так мы привязываемся к реализации класса???
            httpServletRequest.getSession().setAttribute(this.localeAttributeName, locale.toString());
            // или вариант с статическими методами
            // WebUtils.setSessionAttribute(httpServletRequest, this.localeAttributeName, locale.toString());
        }
    }

    /**
     * Вызывается в случае, если нужна локаль по умолчанию, которая могла содержаться в http-запросе.
     *
     * @param request http-запрос
     * @return Locale по умолчанию из реквеста, если присутствует, если нет, то системная локаль.
     */
    protected Locale getDefaultLocale(HttpServletRequest request) {
        Locale locale = request.getLocale();
        if (locale != null) {
            return locale;
        }
        // обратимся к родительской реализации
        return getDefaultLocale();
    }

    public String getLocaleAttributeName() {
        return this.localeAttributeName;
    }

    public void setLocaleAttributeName(String localeAttributeName) {
        this.localeAttributeName = localeAttributeName;
    }
}
