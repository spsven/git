package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.getAuthUser;

/**
 * Created by Alexandr Kozlov on 28.04.2015.
 */
@Repository
public class CoursesDAOImpl implements CoursesDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addCourses(Courses courses) {
        sessionFactory.getCurrentSession().save(courses);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteCourse(long courseID) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Courses.class)
                .add(Restrictions.eq("coursesID", courseID));
        Courses course = (Courses) criteria.list().get(0);

        sessionFactory.getCurrentSession().delete(course);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateCourse(long courseID, String courseNameForUpdate,
                             String courseDescrForUpdate, String courseLinkForUpdate,
                             String courseState, Category category) {

        Courses course = findCourseByCourseID(courseID);
        course.setCoursesName(courseNameForUpdate);
        course.setCoursesDescription(courseDescrForUpdate);
        course.setCoursesLinks(courseLinkForUpdate);
        course.setCoursesState(courseState);

        course.setCategory(category);

        sessionFactory.getCurrentSession().update(course);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCourseMinSubscribers(long courseID, int minCourseSubscribers) {
        Courses course = findCourseByCourseID(courseID);
        course.setCoursesMinSubscriber(minCourseSubscribers);
        sessionFactory.getCurrentSession().update(course);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setCourseMinAttendee(long courseID, int minCourseAttendee) {
        Courses course = findCourseByCourseID(courseID);
        course.setCoursesMinAttend(minCourseAttendee);
        sessionFactory.getCurrentSession().update(course);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Courses findCourseByCourseID(long courseID) {
        Criteria criteria = sessionFactory.getCurrentSession().
                createCriteria(Courses.class).add(Restrictions.eq("coursesID", courseID));
        List<Courses> courses = criteria.list();

        if (!courses.isEmpty()) {
            return courses.get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Courses> listCourses() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Courses.class);
        List<Courses> listAllCourses = criteria.list();

        String userName = getAuthUser();

        List<Courses> coursesListByUserRole = new ArrayList<Courses>();
        for (Courses listAllCourse : listAllCourses) {
            if (listAllCourse.getCoursesOwner().equals(userName)) {
                coursesListByUserRole.add(listAllCourse);
            } else {
                if (!listAllCourse.getCoursesState().equals("Draft")
                        && (!listAllCourse.getCoursesState().equals("Proposal"))
                        && (!listAllCourse.getCoursesState().equals("Rejected"))) {
                    coursesListByUserRole.add(listAllCourse);
                }
            }
        }
        return coursesListByUserRole;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Courses> listCoursesToManager() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Courses.class);
        List<Courses> listAllCourses = criteria.list();

        List<Courses> coursesListToManager = new ArrayList<Courses>();
        for (Courses listAllCourse : listAllCourses) {
            if (!listAllCourse.getCoursesState().equals("Draft")) {
                coursesListToManager.add(listAllCourse);
            }
        }
        return coursesListToManager;
    }
}
