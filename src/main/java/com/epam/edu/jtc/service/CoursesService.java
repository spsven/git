package com.epam.edu.jtc.service;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 28.04.2015.
 */
public interface CoursesService {
    void addCourses(Courses courses);

    void deleteCourse(long courseID);

    List<Courses> listCourses();

    List<Courses> listCoursesToManager();

    Courses findCourseByCourseID(long courseID);

    void updateCourse(long courseID, String courseNameForUpdate, String courseDescrForUpdate,
                      String courseLinkForUpdate, String courseState, Category category);

    void setCourseMinSubscribers(long courseID, int minCourseSubscribers);

    void setCourseMinAttendee(long courseID, int minCourseAttendee);

    void addToSubscriber(long courseID, User user);

    void addToAttend(long courseID, String userName);

    boolean isUserSubscriber(long courseID, String userName);

    boolean isUserAttend(long courseID, String userName);

    int countOfSubscriber(long courseID);

    int countOfAttend(long courseID);

    List<User> listSubscribersOfCourse(long courseID);

    List<User> listAttendeeOfCourse(long courseID);


}
