package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 05.05.2015.
 */
@Controller
public class CourseSubscriptionController {

    final static Logger logger = Logger.getLogger(CourseSubscriptionController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseSendMailController mailMail;

    @RequestMapping(value = {"/courses/{coursesID}/subscribe"}, method = RequestMethod.GET)
    public String getSubscribePageGet(@PathVariable String coursesID,
                                      ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        if (isLong(coursesID)) {
            long courseID = Long.parseLong(coursesID);

            if (coursesService.findCourseByCourseID(courseID) == null) {
                getErrorMessage(model);
                model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
                logger.warn("Course not found! Wrong ID.");
            } else {
                User user = userService.getUser(userNameAuth);
                if (coursesService.isUserSubscriber(courseID, user.getUsername())) {
                    getErrorMessage(model);
                    logger.warn("User " + userNameAuth + " has been a subscriber of this course yet. " +
                            "Action is forbidden");
                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                } else {

                    if (coursesService.isUserAttend(courseID, userNameAuth)) {
                        getErrorMessage(model);
                        model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                        logger.warn("User " + userNameAuth + " has been attendee of this course yet. " +
                                "Action is forbidden");
                    } else {

                        Courses course = coursesService.findCourseByCourseID(courseID);

                        if ((course.getCoursesState().equals("Draft"))
                                || (course.getCoursesState().equals("Proposal"))
                                || (course.getCoursesState().equals("Rejected"))
                                || (course.getCoursesState().equals("In progress"))
                                || (course.getCoursesState().equals("Finished"))) {
                            logger.warn("Action forbidden to user. Course " + course.getCoursesName()
                                    + " has Draft state.");
                            getErrorMessage(model);
                            model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                        } else {
                            if (userNameAuth != null) {
                                if (!userNameAuth.equals(course.getCoursesOwner())) {
                                    model.addAttribute("course", course);
                                } else {
                                    getErrorMessage(model);
                                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                                    logger.warn("User " + userNameAuth + " is owner of course " + course.getCoursesName()
                                            + ". Action forbidden!");
                                }
                            }
                        }
                    }
                }
            }
        } else {
            getErrorMessage(model);
            logger.warn("Course not found! Course ID must be integer.");
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
        }

        return "courseSubscribe";
    }

    @RequestMapping(value = {"/courses/{coursesID}/subscribe"}, method = RequestMethod.POST)
    public String getSubscribePagePost(@PathVariable String coursesID,
                                       ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);
        long courseID = Long.parseLong(coursesID);
        User user = userService.getUser(userNameAuth);
        Courses course = coursesService.findCourseByCourseID(courseID);
        Category category = course.getCategory();
        coursesService.addToSubscriber(courseID, user);
        if (coursesService.countOfSubscriber(courseID) >= course.getCoursesMinSubscriber()) {

            String courseState = "Open";
            coursesService.updateCourse(courseID, course.getCoursesName(), course.getCoursesDescription(),
                    course.getCoursesLinks(), courseState, category);
            //Send email
            List<User> listToSendNotification = new ArrayList<User>();
            listToSendNotification.add(userService.getUser(course.getCoursesOwner()));
            listToSendNotification.addAll(coursesService.listSubscribersOfCourse(courseID));
            for (int i = 0; i < listToSendNotification.size(); i++) {
                mailMail.sendMail(listToSendNotification.get(i).getUsername(), "km@tc.edu",
                        "Course open",
                        "Course has been opened.\n" +
                                "----------------------------------------------------\n" +
                                "Name: " + course.getCoursesName() + "\n" +
                                "Category: " + category.getCategoryName() + "\n" +
                                "Description: " + course.getCoursesDescription() + "\n" +
                                "Links: " + course.getCoursesLinks() + "\n" + "----------------------------------------------------\n"
                );
            }
        }
        return "redirect:/courses";
    }
}
