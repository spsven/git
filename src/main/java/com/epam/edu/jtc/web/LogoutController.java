package com.epam.edu.jtc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 03.05.2015.
 */
@Controller
public class LogoutController {

    @RequestMapping(value = "/logout")
    public String getLogoutPage(ModelMap model) {

        model.addAttribute("userNameAuth", getAuthUser());
        return "logout";
    }

}
