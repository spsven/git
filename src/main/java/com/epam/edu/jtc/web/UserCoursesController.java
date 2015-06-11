package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.GradeService;
import com.epam.edu.jtc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 13.05.2015.
 */
@Controller
public class UserCoursesController {

    final static Logger logger = Logger.getLogger(UserCoursesController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/getusercoursespage"}, method = RequestMethod.GET)
    public String getMyCoursesPageGet(ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        String userRoles = getAuthRole();
        logger.info("User " + userNameAuth + " come with a " + userRoles + " roles.");

        if ((userRoles != null)) {
            if (userRoles.contains("LECTURER")) {
                model.addAttribute("userRole", "LECTURER");
                logger.warn("User " + userNameAuth + " is Lecturer.");
            }

            User user = userService.getUser(userNameAuth);

            List<Courses> listCoursesFilteredByCategory = userService.findAllCoursesToUser(userNameAuth, "All");
            List<Category> categoryList = categoryService.listCategory();

            model.addAttribute("listCoursesFilteredByCategory", listCoursesFilteredByCategory);
            model.addAttribute("user", user);
            model.addAttribute("coursesService", coursesService);
            model.addAttribute("gradeService", gradeService);
            model.addAttribute("categoryService", categoryService);
            model.addAttribute("categoryList", categoryList);

        }
        return "getUserCoursesPage";
    }

    @RequestMapping(value = {"/getusercoursespage"}, method = RequestMethod.POST)
    public String getMyCoursesPagePost(@RequestParam(value = "filterValue", required = false)
                                       String filterValue,
                                       ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        String userRoles = getAuthRole();
        logger.info("User " + userNameAuth + " come with a " + userRoles + " roles.");

        if ((userRoles != null)) {

            if (userRoles.contains("LECTURER")) {
                model.addAttribute("userRole", "LECTURER");
                logger.warn("User " + userNameAuth + " is Lecturer.");
            }

            User user = userService.getUser(userNameAuth);

            List<Courses> listCoursesFilteredByCategory = userService.findAllCoursesToUser(userNameAuth, filterValue);
            List<Category> categoryList = categoryService.listCategory();

            model.addAttribute("listCoursesFilteredByCategory", listCoursesFilteredByCategory);
            model.addAttribute("user", user);
            model.addAttribute("coursesService", coursesService);
            model.addAttribute("gradeService", gradeService);
            model.addAttribute("categoryService", categoryService);
            model.addAttribute("categoryList", categoryList);
        }
        return "getUserCoursesPage";
    }
}
