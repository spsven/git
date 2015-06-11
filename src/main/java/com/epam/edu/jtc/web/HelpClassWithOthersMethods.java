package com.epam.edu.jtc.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

/**
 * Created by Alexandr Kozlov on 04.05.2015.
 */
public class HelpClassWithOthersMethods {

    public static final String MAIN_ERROR_MESS = "Okay, Houston, we've had a problem here.";
    public static final String ERROR_UNKNOWN_COURSE = "Unknown course.";
    public static final String ERROR_UNKNOWN_USER = "Unknown user or invalid password.";
    public static final String ERROR_ACTION_FORBIDDEN = "Action not allowed. Say again please.";
    public static final String ERROR_GRADE_EMPTY = "Grade field is required.";
    public static final String ERROR_GRADE_RANGE = "Grade must be between 1 and 5";
    public static final String ERROR_GRADE_TYPE = "Grade must be integer.";
    public static final String ERROR_COURSE_NAME = "Name field is required.";
    public static final String ERROR_COURSE_DESCRIPTION = "Description field is required.";
    public static final String ERROR_COURSE_CATEGORY = "Category must be selected.";
    public static final String ERROR_VOTE_DECISION = "Reason is required";
    public static final String ERROR_VOTE_DECISION_COUNT = "The second attempt to leave a vote.";
    public static final String ERROR_MINIMAL_SUBSCRIBERS_EMPTY = "Minimal Subscribers field is required.";
    public static final String ERROR_MINIMAL_ATTENDEE_EMPTY = "Minimal Attendee field is required.";
    public static final String ERROR_MINIMAL_SUBSCRIBERS_RANGE = "Minimal Subscribers must be between 1 and 10";
    public static final String ERROR_MINIMAL_ATTENDEE_RANGE = "Minimal Attendee must be between 1 and 10";
    public static final String ERROR_MINIMAL_SUBSCRIBERS_TYPE = "Grade must be integer.";
    public static final String ERROR_MINIMAL_ATTENDEE_TYPE = "Grade must be integer.";


    public static String getAuthRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();

            return userDetail.getAuthorities().toString();
        }
        return null;
    }

    public static String getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();

            return userDetail.getUsername();
        }
        return null;
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void getErrorMessage(ModelMap model) {
        model.addAttribute("mainErrorMess", MAIN_ERROR_MESS);
    }

}
