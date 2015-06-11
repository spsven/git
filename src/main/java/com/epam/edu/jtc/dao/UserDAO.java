package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 03.05.2015.
 */
public interface UserDAO {
    User findByUserName(String username);

    List<Courses> findAllCoursesToUser(String username, String categoryName);

    List<Courses> findAllCoursesFilteredByCategory(String categoryName);

    List<User> userList();

    List<User> managerList();
}
