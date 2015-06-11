package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 28.05.2015.
 */
@Controller
public class CourseFinishController {
    final static Logger logger = Logger.getLogger(CourseUpdateController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CourseSendMailController mailMail;

    @RequestMapping(value = {"/courses/{coursesID}/finish"}, method = RequestMethod.GET)
    public String getCourseFinishGet(@PathVariable String coursesID,
                                     ModelMap model) {
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
                model.addAttribute("finishForbidden", ERROR_ACTION_FORBIDDEN);
            }
        }

        if (isLong(coursesID)) {
            long courseID = Long.parseLong(coursesID);

            if (coursesService.findCourseByCourseID(courseID) == null) {
                logger.warn("Course not found! Wrong ID.");
                getErrorMessage(model);
                model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
            } else {
                Courses course = coursesService.findCourseByCourseID(courseID);

                if ((course.getCoursesState().equals("In progress")))
                    if (userNameAuth != null && userNameAuth.equals(course.getCoursesOwner())) {
                        logger.info("User " + userNameAuth + " is Lecturer and Owner of course "
                                + course.getCoursesName());
                        model.addAttribute("course", course);

                        List<Category> categoryList = categoryService.listCategory();
                        model.addAttribute("categoryList", categoryList);
                        model.addAttribute("categoryService", categoryService);

                    } else {
                        logger.warn("Action forbidden to no owner of course " + course.getCoursesName());
                        getErrorMessage(model);
                        model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                    }
                else {
                    logger.warn("Action forbidden to no owner of course " + course.getCoursesName());
                    getErrorMessage(model);
                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                }

            }

        } else {
            logger.warn("Course not found! Course ID must be integer.");
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
        }
        return "courseFinish";
    }

    @RequestMapping(value = {"/courses/{coursesID}/finish"}, method = RequestMethod.POST)
    public String getCourseFinishPost(@PathVariable String coursesID,
                                      ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);
        long courseID = Long.parseLong(coursesID);
        Courses course = coursesService.findCourseByCourseID(courseID);

        String courseState = "Finished";
        coursesService.updateCourse(courseID, course.getCoursesName(), course.getCoursesDescription(),
                course.getCoursesLinks(), courseState, course.getCategory());
        //Send email
        List<User> listAttendee = coursesService.listAttendeeOfCourse(courseID);
        Category category = course.getCategory();

        for (User aListAttendee : listAttendee) {
            mailMail.sendMail(aListAttendee.getUsername(), course.getCoursesOwner(),
                    "Course Finish",
                    " Course has been finished.\n" +
                            "----------------------------------------------------\n" +
                            "Name: " + course.getCoursesName() + "\n" +
                            "Category: " + category.getCategoryName() + "\n" +
                            "Description: " + course.getCoursesDescription() + "\n" +
                            "Links: " + course.getCoursesLinks() + "\n" + "----------------------------------------------------\n"
            );
        }
        return "redirect:/courses";
    }
}
