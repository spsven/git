package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
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
 * Created by Alexandr Kozlov on 12.05.2015.
 */
@Controller
public class CourseParticipantsController {

    final static Logger logger = Logger.getLogger(CourseParticipantsController.class);

    @Autowired
    private CoursesService coursesService;

    @RequestMapping(value = {"/courses/{coursesID}/participants"}, method = RequestMethod.GET)
    public String getParticipantsGet(@PathVariable String coursesID,
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
                Courses course = coursesService.findCourseByCourseID(courseID);
                List<User> subscriberList = coursesService.listSubscribersOfCourse(courseID);
                List<User> attendList = coursesService.listAttendeeOfCourse(courseID);
                model.addAttribute("course", course);

                if (!subscriberList.isEmpty()) {
                    model.addAttribute("subscriberList", subscriberList);
                }
                if (!attendList.isEmpty()) {
                    model.addAttribute("attendList", attendList);
                }
            }

        } else {
            getErrorMessage(model);
            logger.warn("Course not found! Course ID must be integer.");
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
        }
        return "getParticipants";
    }


}
