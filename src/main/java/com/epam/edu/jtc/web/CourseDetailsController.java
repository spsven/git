package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Courses;
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

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;


/**
 * Created by Alexandr Kozlov on 03.05.2015.
 */
@Controller
public class CourseDetailsController {

    final static Logger logger = Logger.getLogger(CourseDetailsController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/courses/{coursesID}"}, method = RequestMethod.GET)
    public String getCourseDetails(@PathVariable String coursesID,
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
                logger.info("Course found by ID: " + courseID);
                Courses course = coursesService.findCourseByCourseID(courseID);
                if ((course.getCoursesState().equals("Draft"))
                        || (course.getCoursesState().equals("Proposal"))
                        || (course.getCoursesState().equals("Rejected"))) {
                    if (course.getCoursesOwner().equals(userNameAuth)) {
                        model.addAttribute("course", course);

                        int countAttend = coursesService.countOfAttend(courseID);
                        logger.info("Count of Attendee: " + countAttend);

                        int countSubscribers = coursesService.countOfSubscriber(courseID);
                        logger.info("Count of subscribers: " + countSubscribers);

                        double averageMark = gradeService.getAverageMark(courseID);
                        logger.info("Average mark of course: " + averageMark);

                        model.addAttribute("countAttend", countAttend);
                        model.addAttribute("countSubscribers", countSubscribers);
                        model.addAttribute("averageMark", averageMark);
                        model.addAttribute("categoryService", categoryService);
                    } else {
                        logger.warn("Action forbidden to user. Course " + course.getCoursesName() + " has Draft state.");
                        getErrorMessage(model);
                        model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                    }

                } else {
                    model.addAttribute("course", course);

                    int countAttend = coursesService.countOfAttend(courseID);
                    logger.info("Count of Attendee: " + countAttend);

                    int countSubscribers = coursesService.countOfSubscriber(courseID);
                    logger.info("Count of subscribers: " + countSubscribers);

                    double averageMark = gradeService.getAverageMark(courseID);
                    logger.info("Average mark of course: " + averageMark);

                    model.addAttribute("countAttend", countAttend);
                    model.addAttribute("countSubscribers", countSubscribers);
                    model.addAttribute("averageMark", averageMark);
                    model.addAttribute("categoryService", categoryService);
                }
            }
        } else {
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
            logger.warn("Course not found! Course ID must be integer.");
        }
        return "courseDetails";
    }
}
