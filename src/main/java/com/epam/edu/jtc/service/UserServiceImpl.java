package com.epam.edu.jtc.service;

import com.epam.edu.jtc.dao.UserDAO;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Alexandr Kozlov on 08.05.2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public com.epam.edu.jtc.entity.User getUser(String username) {
        return userDAO.findByUserName(username);

    }

    @Override
    @Transactional
    public List<Courses> findAllCoursesToUser(String username, String categoryName) {
        return userDAO.findAllCoursesToUser(username, categoryName);
    }

    @Override
    @Transactional
    public List<Courses> findAllCoursesFilteredByCategory(String categoryName) {
        return userDAO.findAllCoursesFilteredByCategory(categoryName);
    }

    @Override
    @Transactional
    public List<User> userList() {
        return userDAO.userList();
    }

    @Override
    @Transactional
    public List<User> managerList() {
        return userDAO.managerList();
    }
}
