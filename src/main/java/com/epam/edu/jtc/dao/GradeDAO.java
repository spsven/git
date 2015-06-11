package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Grade;

/**
 * Created by Alexandr Kozlov on 11.05.2015.
 */
public interface GradeDAO {
    void addGrade(Grade grade);

    boolean isFindGradeByIDCourseUser(long courseID, long userID);

    double getAverageMark(long courseID);

}
