package com.epam.edu.jtc.service;

import com.epam.edu.jtc.entity.Category;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 13.05.2015.
 */
public interface CategoryService {

    String findCategoryNameByCourseID(long courseID);

    List<Category> listCategory();

    Category findCategoryByCategoryName(String categoryName);
}
