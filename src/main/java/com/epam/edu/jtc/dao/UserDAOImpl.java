package com.epam.edu.jtc.dao;

import com.epam.edu.jtc.entity.Category;
import com.epam.edu.jtc.entity.Courses;
import com.epam.edu.jtc.entity.User;
import com.epam.edu.jtc.entity.UserRole;
import com.epam.edu.jtc.service.CoursesService;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.epam.edu.jtc.web.HelpClassWithOthersMethods.getAuthRole;

/**
 * Created by Alexandr Kozlov on 03.05.2015.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private CoursesService coursesService;

    @Override
    @SuppressWarnings("unchecked")
    public User findByUserName(String username) {

        Criteria criteria = sessionFactory.getCurrentSession().
                createCriteria(User.class).add(Restrictions.eq("username", username));
        List<User> users = criteria.list();

        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Courses> findAllCoursesToUser(String username, String categoryName) {

        User user = findByUserName(username);


        List<Courses> listCoursesToUser = new ArrayList<Courses>();

        listCoursesToUser.addAll(user.getUserCourseSubscribe());
        listCoursesToUser.addAll(user.getUserCourseAttend());

        List<Courses> listAllCourses = coursesService.listCourses();
        List<Courses> listCoursesOwners = new ArrayList<Courses>();

        if (!listAllCourses.isEmpty()) {
            for (Courses listAllCourse : listAllCourses) {
                if (listAllCourse.getCoursesOwner().equals(username)) {
                    listCoursesOwners.add(listAllCourse);
                }
            }
        }

        listCoursesToUser.addAll(listCoursesOwners);
        // Sort list user's courses
        Collections.sort(listCoursesToUser, new Comparator<Courses>() {
            public int compare(Courses course1, Courses course2) {
                return course1.getCoursesName().compareTo(course2.getCoursesName());
            }
        });

        // Get list user's courses where categoryName are All, or Project Management, or Development

        if (categoryName.equals("All")) {
            return listCoursesToUser;
        } else {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Category.class)
                    .add(Restrictions.eq("categoryName", categoryName));

            List<Category> categoryList = criteria.list();

            Set<Courses> coursesSet = new HashSet<Courses>();
            for (Category aCategoryList : categoryList) {
                coursesSet.addAll(aCategoryList.getCoursesSet());
            }

            List<Courses> listCoursesFilteredByCategory = new ArrayList<Courses>();

            for (Courses aListCoursesToUser : listCoursesToUser) {
                if (coursesSet.contains(aListCoursesToUser)) {
                    listCoursesFilteredByCategory.add(aListCoursesToUser);
                }
            }
            return listCoursesFilteredByCategory;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Courses> findAllCoursesFilteredByCategory(String categoryName) {

        List<Courses> listAllCourses = new ArrayList<Courses>();
        String userRoles = getAuthRole();
        if ((userRoles != null)) {
            if (userRoles.contains("Manager")) {
                listAllCourses = coursesService.listCoursesToManager();
            } else {
                listAllCourses = coursesService.listCourses();
            }
        }
        if (categoryName.equals("All")) {
            return listAllCourses;
        } else {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Category.class)
                    .add(Restrictions.eq("categoryName", categoryName));

            List<Category> categoryList = criteria.list();

            Set<Courses> coursesSet = new HashSet<Courses>();
            for (Category aCategoryList : categoryList) {
                coursesSet.addAll(aCategoryList.getCoursesSet());

            }

            List<Courses> listCoursesFilteredByCategory = new ArrayList<Courses>();

            for (Courses aListCoursesToUser : listAllCourses) {
                if (coursesSet.contains(aListCoursesToUser)) {
                    listCoursesFilteredByCategory.add(aListCoursesToUser);
                }
            }
            return listCoursesFilteredByCategory;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> userList() {
        Criteria criteria = sessionFactory.getCurrentSession().
                createCriteria(User.class);

        List<User> allUsersList = criteria.list();
        List<User> userList = new ArrayList<User>();
        for (User anAllUsersList : allUsersList) {
            List<UserRole> role = new ArrayList<UserRole>();
            role.addAll(anAllUsersList.getUserRole());
            for (UserRole aRole : role) {
                if (!aRole.getRole().contains("Manager") || !aRole.getRole().contains("LECTURER")) {
                    userList.add(anAllUsersList);
                }
            }
        }


        return userList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> managerList() {
        Criteria criteria = sessionFactory.getCurrentSession().
                createCriteria(User.class);

        List<User> allUsersList = criteria.list();
        List<User> managerList = new ArrayList<User>();

        for (User anAllUsersList : allUsersList) {
            List<UserRole> role = new ArrayList<UserRole>();
            role.addAll(anAllUsersList.getUserRole());
            for (UserRole aRole : role) {
                if (aRole.getRole().contains("Manager")) {
                    managerList.add(anAllUsersList);
                }

            }

        }
        return managerList;
    }
}
