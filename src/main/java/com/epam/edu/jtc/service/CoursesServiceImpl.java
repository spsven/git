package com.epam.edu.jtc.service;

import com.epam.edu.jtc.dao.CoursesDAO;
import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexandr Kozlov on 28.04.2015.
 */
@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    private CoursesDAO coursesDAO;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void addCourses(Courses courses) {
        coursesDAO.addCourses(courses);
    }

    @Override
    @Transactional
    public void deleteCourse(long courseID) {
        coursesDAO.deleteCourse(courseID);
    }

    @Override
    @Transactional
    public List<Courses> listCourses() {
        return coursesDAO.listCourses();
    }

    @Override
    public List<Courses> listCoursesToManager() {
        return coursesDAO.listCoursesToManager();
    }

    @Override
    @Transactional
    public Courses findCourseByCourseID(long courseID) {
        return coursesDAO.findCourseByCourseID(courseID);
    }

    @Override
    @Transactional
    public void updateCourse(long courseID, String courseNameForUpdate,
                             String courseDescrForUpdate, String courseLinkForUpdate, String courseState,
                             Category category) {
        coursesDAO.updateCourse(courseID, courseNameForUpdate, courseDescrForUpdate,
                courseLinkForUpdate, courseState, category);
    }

    @Override
    @Transactional
    public void setCourseMinSubscribers(long courseID, int minCourseSubscribers) {
        coursesDAO.setCourseMinSubscribers(courseID, minCourseSubscribers);
    }

    @Override
    @Transactional
    public void setCourseMinAttendee(long courseID, int minCourseAttendee) {
        coursesDAO.setCourseMinAttendee(courseID, minCourseAttendee);
    }

    @Override
    @Transactional
    public void addToSubscriber(long courseID, User user) {
        Courses course = findCourseByCourseID(courseID);
        course.getSubscriber().add(user);
    }

    @Override
    @Transactional
    public void addToAttend(long courseID, String userName) {
        Courses course = findCourseByCourseID(courseID);
        User user = userService.getUser(userName);
        course.getAttend().add(user);
        List<User> userSubscribersList = new ArrayList<User>();

        userSubscribersList.addAll(course.getSubscriber());
        course.getSubscriber().remove(user);
    }

    @Override
    @Transactional
    public boolean isUserSubscriber(long courseID, String userName) {
        Courses course = findCourseByCourseID(courseID);

        List<User> userList = new ArrayList<User>();
        userList.addAll(course.getSubscriber());

        int userSubscribe = 0;
        if (!userList.isEmpty()) {
            for (User anUserList : userList) {
                if (anUserList.getUsername().equals(userName)) {
                    userSubscribe++;
                }
            }
        }

        return userSubscribe != 0;
    }

    @Override
    @Transactional
    public boolean isUserAttend(long courseID, String userName) {
        Courses course = findCourseByCourseID(courseID);

        List<User> userList = new ArrayList<User>();
        userList.addAll(course.getAttend());

        int userAttend = 0;
        if (!userList.isEmpty()) {
            for (User anUserList : userList) {
                if (anUserList.getUsername().equals(userName)) {
                    userAttend++;
                }
            }
        }

        return userAttend != 0;
    }

    @Override
    @Transactional
    public int countOfSubscriber(long courseID) {
        Courses course = findCourseByCourseID(courseID);
        Set<User> userSet = course.getSubscriber();
        if (userSet.isEmpty()) {
            return 0;
        } else {
            return userSet.size();
        }
    }

    @Override
    @Transactional
    public int countOfAttend(long courseID) {
        Courses course = findCourseByCourseID(courseID);
        Set<User> userSet = course.getAttend();
        if (userSet.isEmpty()) {
            return 0;
        } else {
            return userSet.size();
        }
    }

    @Override
    @Transactional
    public List<User> listSubscribersOfCourse(long courseID) {
        Courses course = findCourseByCourseID(courseID);

        List<User> subscribersList = new ArrayList<User>();
        subscribersList.addAll(course.getSubscriber());

        return subscribersList;
    }

    @Override
    @Transactional
    public List<User> listAttendeeOfCourse(long courseID) {
        Courses course = findCourseByCourseID(courseID);

        List<User> attendList = new ArrayList<User>();
        attendList.addAll(course.getAttend());

        return attendList;
    }
}
