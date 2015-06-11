package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.GradeService;
import com.epam.edu.jtc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.getAuthRole;
import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.getAuthUser;

@Controller
public class CoursesController {

    final static Logger logger = Logger.getLogger(CoursesController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/", "/courses/"}, method = RequestMethod.GET)
    public String getCoursesGet(ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        String userRoles = getAuthRole();
        if ((userRoles != null)) {
            if (userRoles.contains("Manager")) {
                model.addAttribute("manager", "MANAGER");
                logger.info("User " + userNameAuth + " is a manager.");
            }

            if (userRoles.contains("LECTURER")) {
                model.addAttribute("userRole", "LECTURER");
                logger.info("User " + userNameAuth + " is a lecturer.");
            }

            logger.info("User " + userNameAuth + " is a simple user.");

            logger.debug("Start get list of all courses.");
            List<Courses> listCoursesFilteredByCategory = userService.findAllCoursesFilteredByCategory("All");
            logger.debug("No errors with getting list of courses.");

            logger.debug("Start get list of category");
            List<Category> categoryList = categoryService.listCategory();
            logger.debug("No errors with getting list of category");

            logger.debug("Start get user, where user name is " + userNameAuth + ".");
            User user = userService.getUser(userNameAuth);
            logger.debug("No errors, user found.");

            model.addAttribute("user", user);
            model.addAttribute("coursesList", listCoursesFilteredByCategory);
            model.addAttribute("coursesService", coursesService);
            model.addAttribute("gradeService", gradeService);
            model.addAttribute("categoryService", categoryService);
            model.addAttribute("categoryList", categoryList);
        }
        return "courses";
    }

    @RequestMapping(value = {"/", "/courses/"}, method = RequestMethod.POST)
    public String getCoursesPost(@RequestParam(value = "filterValue", required = false)
                                 String filterValue,
                                 ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        String userRoles = getAuthRole();
        if ((userRoles != null)) {

            if (userRoles.contains("LECTURER")) {
                logger.info("User " + userNameAuth + " is a lecturer.");
                model.addAttribute("userRole", "LECTURER");
            }

            logger.info("User " + userNameAuth + " is a simple user.");

            logger.debug("Start get list of courses, where category name is " + filterValue + ".");
            List<Courses> listCoursesFilteredByCategory = userService.findAllCoursesFilteredByCategory(filterValue);
            logger.debug("No errors with getting list of courses, filtered by category");

            logger.debug("Start get user, where user name is " + userNameAuth + ".");
            User user = userService.getUser(userNameAuth);
            logger.debug("No errors, user found.");

            logger.debug("Start get list of category");
            List<Category> categoryList = categoryService.listCategory();
            logger.debug("No errors with getting list of category");

            model.addAttribute("user", user);
            model.addAttribute("coursesList", listCoursesFilteredByCategory);
            model.addAttribute("coursesService", coursesService);
            model.addAttribute("gradeService", gradeService);
            model.addAttribute("categoryService", categoryService);
            model.addAttribute("categoryList", categoryList);
        }
        return "courses";
    }

}