package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.service.CoursesService;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 13.05.2015.
 */
@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CoursesService coursesService;

    @Override
    @SuppressWarnings("unchecked")
    public String findCategoryNameByCourseID(long courseID) {
        Courses course = coursesService.findCourseByCourseID(courseID);

        Category category = course.getCategory();

        return category.getCategoryName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Category findCategoryByCategoryName(String categoryName) {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Category.class).add(Restrictions.eq("categoryName", categoryName));

        return (Category) criteria.list().get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> listCategory() {
        return (List<Category>) sessionFactory.getCurrentSession()
                .createCriteria(Category.class)
                .setMaxResults(2)
                .list();
    }
}
