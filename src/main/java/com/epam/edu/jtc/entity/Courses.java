package com.epam.edu.jtc.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandr Kozlov on 28.04.2015.
 */

@Entity
@Table(name = "COURSES_TABLE")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COURSES_ID")
    private long coursesID;

    @Column(name = "COURSES_NAME", nullable = false)
    private String coursesName;

    @Column(name = "COURSES_DESCRIPTION", nullable = false)
    private String coursesDescription;

    @Column(name = "COURSES_LINKS")
    private String coursesLinks;

    @Column(name = "COURSES_OWNER")
    private String coursesOwner;

    @Column(name = "COURSES_STATE")
    private String coursesState;

    @Column(name = "COURSES_MIN_SUBSCRIBER")
    private int coursesMinSubscriber;

    @Column(name = "COURSES_MIN_ATTEND")
    private int coursesMinAttend;

    public int getCoursesMinSubscriber() {
        return coursesMinSubscriber;
    }

    public void setCoursesMinSubscriber(int coursesMinSubscriber) {
        this.coursesMinSubscriber = coursesMinSubscriber;
    }

    public int getCoursesMinAttend() {
        return coursesMinAttend;
    }

    public void setCoursesMinAttend(int coursesMinAttend) {
        this.coursesMinAttend = coursesMinAttend;
    }

    public String getCoursesState() {
        return coursesState;
    }

    public void setCoursesState(String coursesState) {
        this.coursesState = coursesState;
    }

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;


    public Courses(String coursesName, String coursesDescription,
                   String coursesLinks, String coursesOwner,
                   String coursesState, int coursesMinSubscriber,
                   Category category) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
        this.coursesState = coursesState;
        this.coursesMinSubscriber = coursesMinSubscriber;
        this.category = category;
    }

    public Courses(String coursesName, String coursesDescription,
                   String coursesLinks, String coursesOwner, String coursesState,
                   int coursesMinSubscriber, int coursesMinAttend,
                   Category category) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
        this.coursesState = coursesState;
        this.coursesMinSubscriber = coursesMinSubscriber;
        this.coursesMinAttend = coursesMinAttend;
        this.category = category;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            targetEntity = User.class)
    @JoinTable(
            name = "USERS_COURSES_SUBSCRIBE",
            joinColumns = @JoinColumn(name = "COURSES_ID"),

            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @Column(nullable = true)
    private Set<User> subscriber = new HashSet<User>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            targetEntity = User.class)
    @JoinTable(
            name = "USERS_COURSES_ATTEND",
            joinColumns = @JoinColumn(name = "COURSES_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @Column(nullable = true)
    private Set<User> attend = new HashSet<User>();

    public Courses() {
    }

    public Courses(String coursesName, String coursesDescription, String coursesLinks,
                   String coursesOwner, String coursesState, Category category,
                   Set<User> subscriber, Set<User> attend) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
        this.coursesState = coursesState;
        this.category = category;
        this.subscriber = subscriber;
        this.attend = attend;
    }

    public Courses(String coursesName, String coursesDescription, String coursesLinks,
                   String coursesOwner, String coursesState, Category category, Set<User> subscriber) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
        this.coursesState = coursesState;
        this.category = category;
        this.subscriber = subscriber;
    }

    public Courses(String coursesName, String coursesDescription, String coursesLinks,
                   String coursesOwner, String coursesState, Category category) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
        this.coursesState = coursesState;
        this.category = category;
    }

    public Courses(String coursesName, String coursesDescription, String coursesLinks,
                   String coursesOwner, String coursesState) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
        this.coursesState = coursesState;
    }

    public Courses(String coursesName, String coursesDescription, String coursesLinks,
                   String coursesOwner) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
        this.coursesOwner = coursesOwner;
    }

    public Courses(String coursesName, String coursesDescription, String coursesLinks) {
        this.coursesName = coursesName;
        this.coursesDescription = coursesDescription;
        this.coursesLinks = coursesLinks;
    }

    public long getCoursesID() {
        return coursesID;
    }

    public void setCoursesID(long coursesID) {
        this.coursesID = coursesID;
    }

    public String getCoursesName() {
        return coursesName;
    }

    public void setCoursesName(String coursesName) {
        this.coursesName = coursesName;
    }

    public String getCoursesDescription() {
        return coursesDescription;
    }

    public void setCoursesDescription(String coursesDescription) {
        this.coursesDescription = coursesDescription;
    }

    public String getCoursesLinks() {
        return coursesLinks;
    }

    public void setCoursesLinks(String coursesLinks) {
        this.coursesLinks = coursesLinks;
    }

    public String getCoursesOwner() {
        return coursesOwner;
    }

    public void setCoursesOwner(String coursesOwner) {
        this.coursesOwner = coursesOwner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<User> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Set<User> subscriber) {
        this.subscriber = subscriber;
    }

    public Set<User> getAttend() {
        return attend;
    }

    public void setAttend(Set<User> attend) {
        this.attend = attend;
    }
}
