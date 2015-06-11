package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.Grade;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.GradeService;
import com.epam.edu.jtc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 10.05.2015.
 */
@Controller
public class CourseEvaluationController {

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    final static Logger logger = Logger.getLogger(CourseEvaluationController.class);

    @RequestMapping(value = {"/courses/{coursesID}/evaluation"}, method = RequestMethod.GET)
    public String getEvaluationPageGet(@PathVariable String coursesID,
                                       ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        if (isLong(coursesID)) {
            long courseID = Long.parseLong(coursesID);
            Courses course = coursesService.findCourseByCourseID(courseID);
            if (course == null) {
                getErrorMessage(model);
                model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
                logger.warn("Course not found! Wrong ID.");
            } else {
                if (!coursesService.isUserAttend(courseID, userNameAuth)) {
                    getErrorMessage(model);
                    logger.warn("User " + userNameAuth + " is not attendee of course. Action forbidden!");
                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                } else {

                    if (course.getCoursesState().equals("Finished")) {
                        User user = userService.getUser(userNameAuth);
                        if (gradeService.isFindGradeByIDCourseUser(courseID, user.getUserID())) {
                            logger.warn("Course has a mark from " + userNameAuth + " yet");
                            getErrorMessage(model);
                            model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                        } else {
                            // не поставил оценку

                            if (userNameAuth != null) {
                                if (!userNameAuth.equals(course.getCoursesOwner())) {
                                    model.addAttribute("course", course);
                                } else {
                                    logger.warn("Action forbidden to owner of course " + course.getCoursesName());
                                    getErrorMessage(model);
                                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                                }
                            }
                        }
                    } else {
                        getErrorMessage(model);
                        model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                    }
                }
            }
        } else {
            getErrorMessage(model);
            logger.warn("Course not found! Course ID must be integer.");
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
        }
        return "setEvaluation";
    }

    @RequestMapping(value = {"/courses/{coursesID}/evaluation"}, method = RequestMethod.POST)
    public String getEvaluationPagePost(@PathVariable String coursesID,
                                        @RequestParam(value = "evaluation", required = false)
                                        String evaluation,
                                        ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        long courseID = Long.parseLong(coursesID);
        User user = userService.getUser(userNameAuth);

        Courses course = coursesService.findCourseByCourseID(courseID);
        model.addAttribute("course", course);

        if (evaluation.length() == 0) {
            logger.warn("Mark must be no empty.");
            getErrorMessage(model);
            model.addAttribute("errorsMark", ERROR_GRADE_EMPTY);

        } else {
            if (isInt(evaluation)) {
                int evaluationMark = Integer.parseInt(evaluation);
                if ((evaluationMark < 1) || (evaluationMark > 5)) {
                    logger.warn("Mark must be between 1 and 5.");
                    getErrorMessage(model);
                    model.addAttribute("errorsMark", ERROR_GRADE_RANGE);
                } else {
                    Grade grade = new Grade(courseID, user.getUserID(), evaluationMark);
                    gradeService.addGrade(grade);

                    return "redirect:/courses";
                }
            } else {
                getErrorMessage(model);
                logger.warn("Mark must be integer.");
                model.addAttribute("errorsMark", ERROR_GRADE_TYPE);
            }
        }
        return "setEvaluation";
    }
}
