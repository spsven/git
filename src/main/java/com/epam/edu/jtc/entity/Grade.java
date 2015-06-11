package com.epam.edu.jtc.entity;

import javax.persistence.*;

/**
 * Created by Alexandr Kozlov on 11.05.2015.
 */
@Entity
@Table(name = "GRADE_TABLE")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GRADE_ID")
    private long coursesID;

    @Column(name = "GRADE_COURSE_ID", nullable = false)
    private long courseID;

    @Column(name = "GRADE_USER_ID", nullable = false)
    private long userID;

    @Column(name = "GRADE_MARK", nullable = false)
    private int mark;

    public Grade() {
    }

    public Grade(long courseID, long userID, int mark) {
        this.courseID = courseID;
        this.userID = userID;
        this.mark = mark;
    }

    public long getCoursesID() {
        return coursesID;
    }

    public void setCoursesID(long coursesID) {
        this.coursesID = coursesID;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
