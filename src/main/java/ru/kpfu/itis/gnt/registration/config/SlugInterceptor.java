package ru.kpfu.itis.gnt.registration.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import ru.kpfu.itis.gnt.registration.constants.PathConstants;
import ru.kpfu.itis.gnt.registration.services.ArticleService;

@RequiredArgsConstructor
public class SlugInterceptor implements HandlerInterceptor {

    private final ArticleService service;
    private boolean isSlugPreferred = true;


    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String slug = request.getRequestURI().substring(request.getContextPath().length() + 1);
            if (service.slugExists(slug)) {
                if (isSlugPreferred) {
                    response.sendRedirect(request.getContextPath() + PathConstants.Article.SINGLE + "/" + slug);
                } else {
                    response.sendRedirect(request.getContextPath() + "/" + slug);
                }
            }
            return true;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    public void isSlugPreferred(boolean isSlugPreferred) {
        this.isSlugPreferred = isSlugPreferred;
    }
}
