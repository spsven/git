package com.epam.edu.jtc.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandr Kozlov on 13.05.2015.
 */
@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CATEGORY_ID")
    private long categoryID;


    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Courses> coursesSet = new HashSet<Courses>();

    public Category() {

    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Courses> getCoursesSet() {
        return coursesSet;
    }

    public void setCoursesSet(Set<Courses> coursesSet) {
        this.coursesSet = coursesSet;
    }

    public Category(String categoryName, Set<Courses> coursesSet) {
        this.categoryName = categoryName;
        this.coursesSet = coursesSet;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
