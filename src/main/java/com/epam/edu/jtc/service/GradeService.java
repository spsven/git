package com.epam.edu.jtc.service;

import com.epam.edu.jtc.entity.Grade;

/**
 * Created by Alexandr Kozlov on 11.05.2015.
 */
public interface GradeService {

    void addGrade(Grade grade);

    boolean isFindGradeByIDCourseUser(long courseID, long userID);

    double getAverageMark(long courseID);
}
