package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.GradeService;
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
 * Created by Alexandr Kozlov on 29.05.2015.
 */
@Controller
public class CourseNotifyController {

    final static Logger logger = Logger.getLogger(CourseUpdateController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseSendMailController mailMail;

    @RequestMapping(value = {"/courses/{coursesID}/notify"}, method = RequestMethod.GET)
    public String getCourseNotifyGet(@PathVariable String coursesID,
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
                model.addAttribute("notifyForbidden", ERROR_ACTION_FORBIDDEN);
            }
        }

        if (isLong(coursesID)) {
            long courseID = Long.parseLong(coursesID);
            Courses course = coursesService.findCourseByCourseID(courseID);
            if (course == null) {
                logger.warn("Course not found! Wrong ID.");
                getErrorMessage(model);
                model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
            } else {

                if ((course.getCoursesState().equals("Finished")))
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
        return "courseNotify";
    }

    @RequestMapping(value = {"/courses/{coursesID}/notify"}, method = RequestMethod.POST)
    public String getCourseNotifyPost(@PathVariable String coursesID,
                                      ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);
        long courseID = Long.parseLong(coursesID);
        Courses course = coursesService.findCourseByCourseID(courseID);
        List<User> listAttendee = coursesService.listAttendeeOfCourse(courseID);
        Category category = course.getCategory();

        for (User aListAttendee : listAttendee) {
            if (gradeService.isFindGradeByIDCourseUser(courseID, aListAttendee.getUserID())) {
                mailMail.sendMail(aListAttendee.getUsername(), course.getCoursesOwner(),
                        "Course Evaluate",
                        " Course still needs your vote\n" +
                                "----------------------------------------------------\n" +
                                "Name: " + course.getCoursesName() + "\n" +
                                "Category: " + category.getCategoryName() + "\n" +
                                "Description: " + course.getCoursesDescription() + "\n" +
                                "Links: " + course.getCoursesLinks() + "\n" +
                                "----------------------------------------------------\n" +
                                "Please, evaluate this course at " +
                                "http://localhost:8080/training-center-ak/courses/" + courseID + "/evaluation"
                );

            }
        }
        return "redirect:/courses";
    }
}
