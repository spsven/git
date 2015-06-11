package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 04.05.2015.
 */
@Controller
public class CourseCreateController {

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CategoryService categoryService;

    final static Logger logger = Logger.getLogger(CourseCreateController.class);

    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.GET)
    public String createCourseGet(ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        String userRoles = getAuthRole();
        logger.info("User " + userNameAuth + " come with a " + userRoles + " roles.");

        if ((userRoles != null)) {
            if (!userRoles.contains("LECTURER")) {
                logger.warn("User " + userNameAuth + " is not Lecturer. Action forbidden!");
                getErrorMessage(model);
                model.addAttribute("createForbidden", ERROR_ACTION_FORBIDDEN);
            } else {
                List<Category> categoryList = categoryService.listCategory();
                model.addAttribute("categoryList", categoryList);
            }
        }
        return "courseCreate";
    }

    @RequestMapping(value = {"/courses/create"}, method = RequestMethod.POST)
    public String createCoursePost(@RequestParam(value = "courseName", required = false) String courseName,
                                   @RequestParam(value = "courseDescr", required = false) String courseDescr,
                                   @RequestParam(value = "courseLink", required = false) String courseLink,
                                   @RequestParam(value = "coursesOwner", required = false) String coursesOwner,
                                   @RequestParam(value = "courseCategory", required = false) String courseCategory,
                                   @RequestParam(value = "newCoursesState", required = false) String newCourseState,
                                   ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);

        List<String> errors = new ArrayList<String>();

        if (courseName.length() == 0) {
            logger.warn("Course name is empty!");
            errors.add(ERROR_COURSE_NAME);
        }
        if (courseDescr.length() == 0) {
            logger.warn("Course description is empty!");
            errors.add(ERROR_COURSE_DESCRIPTION);
        }
        if (courseCategory.length() == 0) {
            logger.warn("Course category is not selected!");
            errors.add(ERROR_COURSE_CATEGORY);
        }
        if (errors.size() != 0) {
            getErrorMessage(model);
            logger.warn("Course can not be create. See errors!");
            model.addAttribute("errors", errors);
            return "courseCreate";
        } else {


            Category category = categoryService.findCategoryByCategoryName(courseCategory);


            Courses course = new Courses(courseName, courseDescr,
                    courseLink, coursesOwner, newCourseState, category);
            coursesService.addCourses(course);
            return "redirect:/courses";
        }
    }
}
