package com.epam.edu.jtc.web;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.entity.Vote;
import com.epam.edu.jtc.service.CategoryService;
import com.epam.edu.jtc.service.CoursesService;
import com.epam.edu.jtc.service.UserService;
import com.epam.edu.jtc.service.VoteService;
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
 * Created by Alexandr Kozlov on 19.05.2015.
 */
@Controller
public class CourseApproveController {

    final static Logger logger = Logger.getLogger(CourseApproveController.class);

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private CourseSendMailController mailMail;

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/courses/{coursesID}/approve"}, method = RequestMethod.GET)
    public String getCourseApproveGet(@PathVariable String coursesID,
                                      ModelMap model) {

        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        model.addAttribute("userNameAuth", userNameAuth);

        String userRoles = getAuthRole();
        logger.info("User " + userNameAuth + " come with a " + userRoles + " roles.");

        if ((userRoles != null)) {
            if (!userRoles.contains("Manager")) {
                logger.warn("User " + userNameAuth + " is not Manager. Action forbidden!");
                getErrorMessage(model);
                model.addAttribute("approveForbidden", ERROR_ACTION_FORBIDDEN);
            }
        }
        if (isLong(coursesID)) {
            long courseID = Long.parseLong(coursesID);

            if (coursesService.findCourseByCourseID(courseID) == null) {
                logger.warn("Course not found! Wrong ID.");
                getErrorMessage(model);
                model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
            } else {
                List<Vote> voteList = voteService.findVoteByCourseID(courseID);

                switch (voteList.size()) {
                    case 0:
                        if (userRoles != null) {
                            if (userRoles.contains("Department Manager")) {
                                model.addAttribute("kmDisabled", "disabled");
                            } else {
                                model.addAttribute("dmDisabled", "disabled");
                            }
                        }
                        break;
                    case 1:
                        if (userRoles != null) {
                            if (userRoles.contains(voteList.get(0).getVoteManagerRole())) {
                                model.addAttribute("disabledButton", "disabled");
                            }
                        }
                        Vote vote = voteList.get(0);
                        if (vote.getVoteManagerRole().equals("Department Manager")) {
                            if (userRoles != null) {
                                if (userRoles.contains("Department Manager")) {
                                    model.addAttribute("kmDisabled", "disabled");
                                    model.addAttribute("dmDisabled", "disabled");
                                    model.addAttribute("voteDM", vote);
                                } else {
                                    model.addAttribute("voteDM", vote);
                                }
                            }
                        } else {
                            if (userRoles != null) {
                                if (userRoles.contains("Knowledge Manager")) {
                                    model.addAttribute("kmDisabled", "disabled");
                                    model.addAttribute("dmDisabled", "disabled");
                                    model.addAttribute("voteKM", vote);
                                } else {
                                    model.addAttribute("voteKM", vote);
                                }
                            }
                        }
                        break;
                }
                Courses course = coursesService.findCourseByCourseID(courseID);
                model.addAttribute("course", course);
                List<Category> categoryList = categoryService.listCategory();
                model.addAttribute("categoryList", categoryList);
                model.addAttribute("categoryService", categoryService);
            }
        } else {
            logger.warn("Course not found! Course ID must be integer.");
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_UNKNOWN_COURSE);
        }

        return "courseApprove";
    }

    @RequestMapping(value = {"/courses/{coursesID}/approve"}, method = RequestMethod.POST)
    public String getCourseApprovePost(@PathVariable String coursesID,
                                       @RequestParam(value = "managerDecision", required = false)
                                       String managerDecision,
                                       @RequestParam(value = "managerReason", required = false)
                                       String managerReason,
                                       @RequestParam(value = "categoryName", required = false)
                                       String categoryName,
                                       ModelMap model) {


        logger.debug("Start check: user is login? And get his name.");
        String userNameAuth = getAuthUser();
        logger.info("User come with " + userNameAuth + " name.");

        String userRoles = getAuthRole();
        String managerRole = null;
        if (userRoles != null) {
            if (userRoles.contains("Department Manager")) {
                managerRole = "Department Manager";
                model.addAttribute("kmDisabled", "disabled");
            } else {
                managerRole = "Knowledge Manager";
                model.addAttribute("dmDisabled", "disabled");
            }
        }

        model.addAttribute("userNameAuth", userNameAuth);

        long courseID = Long.parseLong(coursesID);

        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryService", categoryService);

        Courses course = coursesService.findCourseByCourseID(courseID);
        model.addAttribute("course", course);

        Category category = categoryService.findCategoryByCategoryName(categoryName);
        if (managerDecision != null && managerDecision.equals("Select one")) {
            getErrorMessage(model);
            model.addAttribute("errors", ERROR_VOTE_DECISION);
        } else {
            List<Vote> voteList = voteService.findVoteByCourseID(courseID);
            switch (voteList.size()) {
                case 0:
                    Vote vote = new Vote(courseID, managerDecision, managerRole, managerReason);
                    voteService.addVote(vote);

                    /*Send mail to owner course from manager, who approves the course*/
                    mailMail.sendMail(course.getCoursesOwner(), userNameAuth,
                            "Course Approval Update",
                            userNameAuth + " had made a decision\n" +
                                    "----------------------------------------------------\n" +
                                    "Name: " + course.getCoursesName() + "\n" +
                                    "Lecturer: " + course.getCoursesOwner() + "\n" +
                                    "----------------------------------------------------\n" +
                                    "Decision: " + vote.getVoteDecision() + "\n" +
                                    "Lecturer: " + vote.getVoteReason() + "\n"
                    );

                    return "redirect:/courses";
                case 1:
                    vote = new Vote(courseID, managerDecision, managerRole, managerReason);
                    voteService.addVote(vote);
                    /*Send mail to owner course from manager, who approves the course*/
                    mailMail.sendMail(course.getCoursesOwner(), userNameAuth,
                            "Course Approval Update",
                            userNameAuth + " had made a decision\n" +
                                    "----------------------------------------------------\n" +
                                    "Name: " + course.getCoursesName() + "\n" +
                                    "Lecturer: " + course.getCoursesOwner() + "\n" +
                                    "----------------------------------------------------\n" +
                                    "Decision: " + vote.getVoteDecision() + "\n" +
                                    "Lecturer: " + vote.getVoteReason() + "\n"
                    );

                    List<Vote> newVoteList = voteService.findVoteByCourseID(courseID);

                    if (newVoteList.get(0).getVoteDecision().equals("Approve")
                            && newVoteList.get(1).getVoteDecision().equals("Approve")) {
                        String courseState = "New";

                        coursesService.updateCourse(courseID, course.getCoursesName(),
                                course.getCoursesDescription(), course.getCoursesLinks(),
                                courseState, category);

                        /*Send mail to owner course from manager, who approves the course*/
                        List<User> userList = userService.userList();
                        for (int i = 0; i < userList.size(); i++) {
                            mailMail.sendMail(userList.get(i).getUsername(), userNameAuth,
                                    "New Course Added",
                                    "New course had been added by " + course.getCoursesOwner() + "\n" +
                                            "----------------------------------------------------\n" +
                                            "Name: " + course.getCoursesName() + "\n" +
                                            "Category: " + category.getCategoryName() + "\n" +
                                            "Description: " + course.getCoursesDescription() + "\n" +
                                            "Links: " + course.getCoursesLinks() + "\n" +
                                            "----------------------------------------------------\n" +
                                            "See course details course at " +
                                            "http://localhost:8080/training-center-ak/courses/" + courseID + "\n" +
                                            "Or became a subscriber " +
                                            "http://localhost:8080/training-center-ak/courses/" + courseID + "/subscribe"
                            );
                        }
                        voteService.deleteVote(courseID);
                    } else {
                        String courseState = "Rejected";
                        coursesService.updateCourse(courseID, course.getCoursesName(),
                                course.getCoursesDescription(), course.getCoursesLinks(),
                                courseState, category);

                        List<User> managerList = userService.managerList();
                        for (User aManagerList : managerList) {
                            mailMail.sendMail(course.getCoursesOwner(), aManagerList.getUsername(),
                                    "Course Rejected",
                                    "Course was rejected by\n" +
                                            "----------------------------------------------------\n" +
                                            "Decision: " + newVoteList.get(0).getVoteDecision() + "\n" +
                                            "Reason: " + newVoteList.get(0).getVoteReason() + "\n" +
                                            "----------------------------------------------------\n" +
                                            "Decision: " + newVoteList.get(1).getVoteDecision() + "\n" +
                                            "Reason: " + newVoteList.get(1).getVoteReason() + "\n" +
                                            "----------------------------------------------------\n" +
                                            "Update your course " +
                                            "http://localhost:8080/training-center-ak/courses/" + courseID + "/update"
                            );

                        }

                        voteService.deleteVote(courseID);
                    }
                    return "redirect:/courses";
            }
        }
        return "courseApprove";
    }
}
