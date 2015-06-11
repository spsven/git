package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Grade;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 11.05.2015.
 */
@Repository
public class GradeDAOImpl implements GradeDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    @SuppressWarnings("unchecked")
    public void addGrade(Grade grade) {
        sessionFactory.getCurrentSession().save(grade);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isFindGradeByIDCourseUser(long courseID, long userID) {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Grade.class)
                .add(Restrictions.eq("courseID", courseID))
                .add(Restrictions.eq("userID", userID));

        List<Grade> gradeList = (List<Grade>) criteria.list();

        return !gradeList.isEmpty();

    }

    @Override
    @SuppressWarnings("unchecked")
    public double getAverageMark(long courseID) {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Grade.class)
                .add(Restrictions.eq("courseID", courseID));

        List<Grade> gradeList = (List<Grade>) criteria.list();

        double averageMark = 0;
        if (gradeList.isEmpty()) {
            averageMark = 0;
        } else {
            for (Grade aGradeList : gradeList) {
                averageMark += aGradeList.getMark();
            }
            averageMark = averageMark / gradeList.size();
        }

        return averageMark;
    }
}
