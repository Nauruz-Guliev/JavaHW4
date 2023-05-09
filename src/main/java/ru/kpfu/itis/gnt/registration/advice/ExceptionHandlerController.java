package ru.kpfu.itis.gnt.registration.advice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.services.ArticleService;

import java.io.IOException;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {
    private final ArticleService service;

    @ExceptionHandler( {HttpClientErrorException.NotFound.class, NotFoundException.class, NotFoundException.class, NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound(Exception exception, HttpServletResponse response, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("404");
        mav.addObject("message", exception.getMessage() + " " + exception.getClass());
        return mav;
    }

    @ExceptionHandler(DBException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView notFound(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage() + " " + exception.getClass());
        mav.setViewName("error/general");
        return mav;
    }

}
