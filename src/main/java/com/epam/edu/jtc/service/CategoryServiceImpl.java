package com.epam.edu.jtc.service;

import com.epam.edu.jtc.dao.CategoryDAO;
import com.epam.edu.jtc.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 13.05.2015.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    @Transactional
    public String findCategoryNameByCourseID(long courseID) {
        return categoryDAO.findCategoryNameByCourseID(courseID);
    }

    @Override
    @Transactional
    public List<Category> listCategory() {
        return categoryDAO.listCategory();
    }

    @Override
    @Transactional
    public Category findCategoryByCategoryName(String categoryName) {
        return categoryDAO.findCategoryByCategoryName(categoryName);
    }
}
