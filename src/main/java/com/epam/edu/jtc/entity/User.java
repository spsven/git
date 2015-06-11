package com.epam.edu.jtc.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandr Kozlov on 03.05.2015.
 */

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private long userID;

    @Column(name = "USER_NAME", unique = true,
            nullable = false, length = 45)
    private String username;

    @Column(name = "USER_PASSWORD",
            nullable = false, length = 60)
    private String password;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Column(nullable = true)
    private Set<UserRole> userRole = new HashSet<UserRole>(0);

    @ManyToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Courses> userCourseSubscribe = new HashSet<Courses>();

    public User(String username, String password, boolean enabled, Set<UserRole> userRole,
                Set<Courses> userCourseSubscribe, Set<Courses> userCourseAttend) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
        this.userCourseSubscribe = userCourseSubscribe;
        this.userCourseAttend = userCourseAttend;
    }

    public Set<Courses> getUserCourseAttend() {

        return userCourseAttend;
    }

    public void setUserCourseAttend(Set<Courses> userCourseAttend) {
        this.userCourseAttend = userCourseAttend;
    }

    @ManyToMany(mappedBy = "attend", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Courses> userCourseAttend = new HashSet<Courses>();

    public User() {
    }

    public User(String username, String password, boolean enabled,
                Set<UserRole> userRole, Set<Courses> userCourseSubscribe) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
        this.userCourseSubscribe = userCourseSubscribe;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public Set<Courses> getUserCourseSubscribe() {
        return userCourseSubscribe;
    }

    public void setUserCourseSubscribe(Set<Courses> userCourseSubscribe) {
        this.userCourseSubscribe = userCourseSubscribe;
    }
}
