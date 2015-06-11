package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.*;

/**
 * Created by Alexandr Kozlov on 04.05.2015.
 */
@Controller
public class CourseUpdateController {

    final static Logger logger = Logger.getLogger(CourseUpdateController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseSendMailController mailMail;

    @RequestMapping(value = {"/courses/{coursesID}/update"}, method = RequestMethod.GET)
    public String getCourseUpdateGet(@PathVariable String coursesID,
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
                model.addAttribute("updateForbidden", ERROR_ACTION_FORBIDDEN);
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

                if (course.getCoursesState().equals("Proposal")) {
                    logger.warn("Action forbidden to owner of course " + course.getCoursesName());
                    getErrorMessage(model);
                    model.addAttribute("errors", ERROR_ACTION_FORBIDDEN);
                }

                if ((course.getCoursesState().equals("Draft"))
                        || (course.getCoursesState().equals("Rejected")))
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
        return "courseUpdate";
    }

    @RequestMapping(value = {"/courses/{coursesID}/update"}, method = RequestMethod.POST)
    public String getCourseUpdatePost(@PathVariable String coursesID,
                                      @RequestParam(value = "courseNameForUpdate", required = false)
                                      String courseNameForUpdate,
                                      @RequestParam(value = "courseDescrForUpdate", required = false)
                                      String courseDescrForUpdate,
                                      @RequestParam(value = "courseLinkForUpdate", required = false)
                                      String courseLinkForUpdate,
                                      @RequestParam(value = "courseCategory", required = false)
                                      String courseCategory,
                                      @RequestParam(value = "updateButton", required = false)
                                      String updateButton,
                                      @RequestParam(value = "reviewButton", required = false)
                                      String reviewButton,
                                      @RequestParam(value = "courseMinSubscriber", required = false)
                                      String courseMinSubscriber,
                                      @RequestParam(value = "courseMinAttend", required = false)
                                      String courseMinAttend,
                                      ModelMap model) {
        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        int errorCount = 0;

        if (courseMinSubscriber.length() == 0) {
            logger.warn("Minimal Subscriber must be no empty.");
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_MINIMAL_SUBSCRIBERS_EMPTY);
            errorCount++;
        } else {
            if (!isInt(courseMinSubscriber)) {
                errorCount++;
                getErrorMessage(model);
                logger.warn("Minimal Subscriber must be integer.");
                model.addAttribute("errors", ERROR_MINIMAL_SUBSCRIBERS_TYPE);
            } else {
                int courseMinSubs = Integer.parseInt(courseMinSubscriber);
                if ((courseMinSubs < 1) || (courseMinSubs > 10)) {
                    logger.warn("Minimal Subscriber must be between 1 and 10.");
                    getErrorMessage(model);
                    model.addAttribute("errors", ERROR_MINIMAL_SUBSCRIBERS_RANGE);
                    errorCount++;
                }
            }
        }
        if (courseMinAttend.length() == 0) {
            logger.warn("Minimal Attendee must be no empty.");
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_MINIMAL_ATTENDEE_EMPTY);
            errorCount++;
        } else {
            if (!isInt(courseMinAttend)) {
                errorCount++;
                getErrorMessage(model);
                logger.warn("Minimal Attendee must be integer.");
                model.addAttribute("errors", ERROR_MINIMAL_ATTENDEE_TYPE);
            } else {
                int courseMinAtt = Integer.parseInt(courseMinAttend);
                if ((courseMinAtt < 1) || (courseMinAtt > 10)) {
                    logger.warn("Minimal Attendee must be between 1 and 10.");
                    getErrorMessage(model);
                    model.addAttribute("errors", ERROR_MINIMAL_ATTENDEE_RANGE);
                    errorCount++;
                }
            }
        }

        if (errorCount == 0) {
            if (updateButton != null && updateButton.length() != 0) {
                List<Category> categoryList = categoryService.listCategory();
                model.addAttribute("categoryList", categoryList);

                String courseState = "Draft";

                long courseID = Long.parseLong(coursesID);
                Category category = categoryService.findCategoryByCategoryName(courseCategory);
                coursesService.updateCourse(courseID, courseNameForUpdate,
                        courseDescrForUpdate, courseLinkForUpdate, courseState, category);
                int courseMinSubs = Integer.parseInt(courseMinSubscriber);
                coursesService.setCourseMinSubscribers(courseID, courseMinSubs);
                int courseMinAtt = Integer.parseInt(courseMinAttend);
                coursesService.setCourseMinAttendee(courseID, courseMinAtt);

                return "redirect:/courses";
            }
            if (reviewButton != null && reviewButton.length() != 0) {
                List<Category> categoryList = categoryService.listCategory();
                model.addAttribute("categoryList", categoryList);

                long courseID = Long.parseLong(coursesID);
                Category category = categoryService.findCategoryByCategoryName(courseCategory);
                String courseState = "Proposal";
                coursesService.updateCourse(courseID, courseNameForUpdate,
                        courseDescrForUpdate, courseLinkForUpdate, courseState, category);
            /* Send mail to manager*/
                int courseMinSubs = Integer.parseInt(courseMinSubscriber);
                coursesService.setCourseMinSubscribers(courseID, courseMinSubs);
                int courseMinAtt = Integer.parseInt(courseMinAttend);
                coursesService.setCourseMinAttendee(courseID, courseMinAtt);

                Courses course = coursesService.findCourseByCourseID(courseID);
                List<User> managerList = userService.managerList();

                for (User aManagerList : managerList) {

                    mailMail.sendMail(aManagerList.getUsername(), course.getCoursesOwner(),
                            "Course Announcement",
                            course.getCoursesOwner() + " had created a new course\n" +
                                    "----------------------------------------------------\n" +
                                    "Name: " + course.getCoursesName() + "\n" +
                                    "Category: " + category.getCategoryName() + "\n" +
                                    "Description: " + course.getCoursesDescription() + "\n" +
                                    "Links: " + course.getCoursesLinks() + "\n" +
                                    "----------------------------------------------------\n" +
                                    "Please, review this course at " +
                                    "http://localhost:8080/training-center-ak/courses/" + courseID + "/update"
                    );
                }
                return "redirect:/courses";
            }
        }
        return "courseUpdate";
    }
}
