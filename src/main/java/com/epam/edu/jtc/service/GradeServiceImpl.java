package com.epam.edu.jtc.service;

import com.epam.edu.jtc.dao.GradeDAO;
import com.epam.edu.jtc.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Alexandr Kozlov on 11.05.2015.
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeDAO gradeDAO;

    @Override
    @Transactional
    public void addGrade(Grade grade) {
        gradeDAO.addGrade(grade);
    }

    @Override
    @Transactional
    public boolean isFindGradeByIDCourseUser(long courseID, long userID) {
        return gradeDAO.isFindGradeByIDCourseUser(courseID, userID);
    }

    @Override
    @Transactional
    public double getAverageMark(long courseID) {
        return gradeDAO.getAverageMark(courseID);
    }
}
