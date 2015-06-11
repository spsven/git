package com.epam.edu.jtc.web;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 03.05.2015.
 */
@Controller
public class CourseLoginController {

    final static Logger logger = Logger.getLogger(CourseLoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               HttpServletRequest request,
                               ModelMap model) {
        String userRoles = getAuthRole();
        logger.info("User come as anonymous");
        if ((userRoles != null)) {
            if (userRoles.contains("USER")) {
                return "redirect:/courses";
            }
        }
        getErrorMessage(model);
        if (error != null) {
            model.addAttribute("error", getErrorMessages(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }
        return "login";
    }

    // customize the error message
    private String getErrorMessages(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error;
        if (exception instanceof BadCredentialsException) {
            logger.warn(ERROR_UNKNOWN_USER);
            error = ERROR_UNKNOWN_USER;
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = ERROR_UNKNOWN_USER;
            logger.warn(ERROR_UNKNOWN_USER);
        }
        return error;
    }
}
