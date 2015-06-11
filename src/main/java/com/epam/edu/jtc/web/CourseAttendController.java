package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.service.CoursesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 10.05.2015.
 */
@Controller
public class CourseAttendController {

    final static Logger logger = Logger.getLogger(CourseAttendController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CourseSendMailController mailMail;

    @RequestMapping(value = {"/courses/{coursesID}/attend"}, method = RequestMethod.GET)
    public String getAttendPageGet(@PathVariable String coursesID,
                                   ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        if (isLong(coursesID)) {

            long courseID = Long.parseLong(coursesID);

            if (coursesService.findCourseByCourseID(courseID) == null) {
                getErrorMessage(model);
                logger.warn("Course not found! Wrong ID.");
                model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
            } else {
                if (!coursesService.isUserSubscriber(courseID, userNameAuth)) {
                    getErrorMessage(model);
                    logger.warn("Action forbidden to no subscriber.");
                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                } else {

                    Courses course = coursesService.findCourseByCourseID(courseID);

                    if ((course.getCoursesState().equals("Open"))
                            || (course.getCoursesState().equals("Ready"))) {
                        model.addAttribute("course", course);
                        if (userNameAuth != null) {
                            if (!userNameAuth.equals(course.getCoursesOwner())) {
                                model.addAttribute("course", course);
                                logger.debug("User " + userNameAuth + " is not owner os course " + course.getCoursesName());
                            } else {
                                logger.warn("Action forbidden! User " + userNameAuth +
                                        " is the owner of course " + course.getCoursesName());
                                getErrorMessage(model);
                                model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                            }
                        }
                    } else {
                        getErrorMessage(model);
                        model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                    }
                }
            }
        } else {
            logger.warn("Course not found! Course ID must be integer.");
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
        }
        return "courseAttend";
    }

    @RequestMapping(value = {"/courses/{coursesID}/attend"}, method = RequestMethod.POST)
    public String getAttendPagePost(@PathVariable String coursesID,
                                    ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);
        long courseID = Long.parseLong(coursesID);
        logger.debug("User + " + userNameAuth + " add to attendee of course with ID " + coursesID);
        coursesService.addToAttend(courseID, userNameAuth);
        Courses course = coursesService.findCourseByCourseID(courseID);

        if (coursesService.countOfAttend(courseID) >= course.getCoursesMinAttend()) {
            String courseState = "Ready";
            course.setCoursesState(courseState);
            Category category = course.getCategory();
            coursesService.updateCourse(courseID, course.getCoursesName(), course.getCoursesDescription(),
                    course.getCoursesLinks(), courseState, category);
            //Send email
            mailMail.sendMail(course.getCoursesOwner(), "km@tc.edu",
                    "Course is ready to start",
                    "Course is ready to start.\n" +
                            "----------------------------------------------------\n" +
                            "Name: " + course.getCoursesName() + "\n" +
                            "Category: " + category.getCategoryName() + "\n" +
                            "Description: " + course.getCoursesDescription() + "\n" +
                            "Links: " + course.getCoursesLinks() + "\n" +
                            "----------------------------------------------------\n"
            );
        }

        return "redirect:/courses";
    }
}
