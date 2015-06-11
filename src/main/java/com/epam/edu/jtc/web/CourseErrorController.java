package com.epam.edu.jtc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Alexandr Kozlov on 16.05.2015.
 */
@Controller
public class CourseErrorController {

    @RequestMapping("/error404")
    public String error404() {
        return "redirect:/";
    }
}
