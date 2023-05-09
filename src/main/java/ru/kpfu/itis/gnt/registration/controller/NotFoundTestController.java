package ru.kpfu.itis.gnt.registration.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundTestController {

    @GetMapping("/not_found")
    public String notFound() {
        return "status:404";
    }
}
