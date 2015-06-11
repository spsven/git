package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 28.04.2015.
 */
public interface CoursesDAO {

    void addCourses(Courses courses);

    void deleteCourse(long courseID);

    void updateCourse(long courseID, String courseNameForUpdate, String courseDescrForUpdate,
                      String courseLinkForUpdate, String courseState, Category category);

    void setCourseMinSubscribers(long courseID, int minCourseSubscribers);

    void setCourseMinAttendee(long courseID, int minCourseAttendee);

    Courses findCourseByCourseID(long courseID);

    List<Courses> listCourses();

    List<Courses> listCoursesToManager();


}
