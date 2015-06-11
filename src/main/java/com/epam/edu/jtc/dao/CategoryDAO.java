package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Category;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 13.05.2015.
 */
public interface CategoryDAO {

    String findCategoryNameByCourseID(long courseID);

    Category findCategoryByCategoryName(String categoryName);

    List<Category> listCategory();

}
