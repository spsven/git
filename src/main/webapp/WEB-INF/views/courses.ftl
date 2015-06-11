<#-- @ftlvariable name="user" type="com.epam.edu.jtc.entity.User" -->
<#-- @ftlvariable name="userRole" type="String" -->
<#-- @ftlvariable name="coursesList" type="java.util.Collection<com.epam.edu.jtc.entity.Courses>" -->
<#-- @ftlvariable name="userNameAuth" type="String" -->
<#-- @ftlvariable name="categoryList" type="java.util.Collection<com.epam.edu.jtc.entity.Category>" -->
<script src="./css/jquery-1.8.1.min.js"></script>
<script src="./css/bootstrap-2.2.2.min.js"></script>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>
            Courses
            <div class="logout">
            <span id="currentUserLogin">
            <#if userNameAuth??>
                    ${userNameAuth}
                </#if>
            </span>
                <a href="./logout">
                    <td><i class="icon-off"></i></td>
                </a>
            </div>
        </h1>
    </header>
    <!-- Top buttons -->
    <div class="courses-top-control">
        <a class="btn" href="./getusercoursespage">My Courses</a>
    <#if userRole??>
        <a id="createCourseButton" class="btn btn-primary" href="./courses/create">Create</a>
    </#if>
        <!-- Filter courses by category -->
        <div class="search-box">
            <form name="filter" method="post" class="form-search">
                <select name="filterValue" id="categoryField" class="span3">
                    <option>All</option>
                <#if categoryList??>
                    <#list categoryList as category>
                        <option>${category.getCategoryName()}</option>
                    </#list>
                </#if>
                </select>
                <button class="btn" type="submit">Apply</button>
            </form>
        </div>
    </div>
    <table class="table table-striped table-bordered">
        <!-- Head Table -->
        <thead>
        <tr>
            <th class="span1">Id</th>
            <th>Course</th>
            <th class="span3">Category</th>
            <th class="span1">S/A</th>
            <th class="span1">Grade</th>
            <th class="span2">Action</th>
        </tr>
        </thead>
        <!-- Table Body -->
        <tbody>
        <#if user??>
            <#if coursesList??>
                <#list coursesList as course> <!-- Список курсов-->
                    <#if coursesService??>
                    <!-- User is Subscriber?-->
                        <#assign userSubscriber = coursesService.isUserSubscriber(course.getCoursesID(), userNameAuth)>
                    <!-- User is Attendee? -->
                        <#assign  userAttend = coursesService.isUserAttend(course.getCoursesID(), userNameAuth)>
                    <!-- Count of Subscribers of course -->
                        <#assign countSubscribers = coursesService.countOfSubscriber(course.getCoursesID())>
                    <!-- Count of Attendee of course -->
                        <#assign countAttend = coursesService.countOfAttend(course.getCoursesID())>
                    <!-- Category course-->
                        <#assign categoryName = categoryService.findCategoryNameByCourseID(course.getCoursesID())>
                    <!-- User is Subscriber? -->
                        <#if userSubscriber>
                        <tr>
                            <td>${course_index + 1}</td>
                            <!-- Course Name -->
                            <td><a href="./courses/${course.coursesID}"> ${course.getCoursesName()}</a></td>
                            <!-- Category -->
                            <td>${categoryName}</td>
                            <!-- Count of Subscribers  and Attendee-->
                            <td>
                                <a href="./courses/${course.coursesID}/participants">
                                    <#if countSubscribers == 0>
                                        <#if countAttend != 0>
                                        ${countSubscribers} / ${countAttend}
                                        </#if>
                                    <#else >
                                        <#if countAttend != 0>
                                        ${countSubscribers} / ${countAttend}
                                        <#else >
                                        ${countSubscribers}
                                        </#if>
                                    </#if>
                                </a>
                            </td>
                            <!-- Average mark of course -->
                            <td>
                                <#if gradeService??>
                                        <#assign averageMark = gradeService.getAverageMark(course.getCoursesID())>
                                        <#if averageMark != 0>
                                ${averageMark}
                                </#if>
                                    </#if>
                            </td>
                            <!-- Buttons-->
                            <td>
                                <#if course.getCoursesState() == "Open"
                                || course.getCoursesState() == "Ready">
                                    <div class="btn-group">
                                        <a class="btn btn-mini"
                                           href="./courses/${course.coursesID}/attend"">Attendee</a>
                                    </div>
                                </#if>
                            </td>
                        <#else>
                            <!-- User is Attendee? -->
                            <#if userAttend>
                            <tr>
                                <td>${course_index + 1}</td>
                                <!-- Course Name -->
                                <td><a href="./courses/${course.coursesID}"> ${course.getCoursesName()}</a></td>
                                <!-- Category -->
                                <td>${categoryName}</td>
                                <!-- Count of Subscribers  and Attendee-->
                                <td>
                                    <a href="./courses/${course.coursesID}/participants">
                                        <#if countSubscribers == 0>
                                            <#if countAttend != 0>
                                            ${countSubscribers} / ${countAttend}
                                            </#if>
                                        <#else >
                                            <#if countAttend != 0>
                                            ${countSubscribers} / ${countAttend}
                                            <#else >
                                            ${countSubscribers}
                                            </#if>
                                        </#if>
                                    </a>
                                </td>
                                <!-- Average mark of course -->
                                <td>
                                    <#if gradeService??>
                                    <#assign averageMark = gradeService.getAverageMark(course.getCoursesID())>
                                    <#if averageMark != 0>
                                    ${averageMark}
                                    </#if>
                                </#if>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <#if course.getCoursesState() == "Finished">
                                            <a class="btn btn-mini"
                                               href="./courses/${course.coursesID}/evaluation"">Evaluate</a>
                                        </#if>
                                    </div>
                                </td>
                            <#else >
                            <tr>
                                <td>${course_index + 1}</td>
                                <td>
                                    <!-- Course Name -->
                                    <a href="./courses/${course.coursesID}"> ${course.getCoursesName()}</a>
                                    <!-- Course state-->
                                    <#if course.getCoursesState() == "Draft">
                                        <span class="label">Draft</span>
                                    </#if>
                                    <#if course.getCoursesState() == "Proposal">
                                        <span class="label label-warning">Proposal</span>
                                    </#if>
                                    <#if course.getCoursesState() == "Rejected">
                                        <span class="label label-important">Rejected</span>
                                    </#if>
                                </td>
                                <!-- Category -->
                                <td>${categoryName}</td>
                                <!-- Count of Subscribers  and Attendee-->
                                <td>
                                    <a href="./courses/${course.coursesID}/participants">
                                        <#if countSubscribers == 0>
                                            <#if countAttend != 0>
                                            ${countSubscribers} / ${countAttend}
                                            </#if>
                                        <#else >
                                            <#if countAttend != 0>
                                            ${countSubscribers} / ${countAttend}
                                            <#else >
                                            ${countSubscribers}
                                            </#if>
                                        </#if>
                                    </a>
                                </td>
                                <!-- Average mark of course -->
                                <td>
                                    <#if gradeService??>
                                        <#assign averageMark = gradeService.getAverageMark(course.getCoursesID())>
                                        <#if averageMark != 0>
                                    ${averageMark}
                                    </#if>
                                    </#if>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <!-- Buttons-->
                                        <!-- User is Lector ?-->
                                        <#if userRole??>
                                            <!-- User is Lector and courseOwner?-->
                                            <#if userNameAuth == course.coursesOwner>
                                                <#if course.getCoursesState() != "Proposal">
                                                    <#if course.getCoursesState() == "Draft"
                                                    || course.getCoursesState() == "Rejected" >
                                                        <a class="btn btn-mini"
                                                           href="./courses/${course.coursesID}/update">Update</a>
                                                        <a class="btn dropdown-toggle btn-mini" data-toggle="dropdown"
                                                           href="#"><span class="caret"></span></a>
                                                        <ul class="dropdown-menu">
                                                            <li>
                                                                <a href="./courses/${course.coursesID}/update">Send to
                                                                    Review</a>
                                                                <a href="./courses/${course.coursesID}/delete">
                                                                    Delete</a>
                                                            </li>
                                                        </ul>
                                                    </#if>
                                                    <#if course.getCoursesState() == "Ready">
                                                        <a class="btn btn-mini"
                                                           href="./courses/${course.coursesID}/start">Start</a>
                                                    </#if>
                                                    <#if course.getCoursesState() == "In progress">
                                                        <a class="btn btn-mini"
                                                           href="./courses/${course.coursesID}/finish">Finish</a>
                                                    </#if>
                                                    <#if course.getCoursesState() == "Finished">
                                                        <a class="btn btn-mini"
                                                           href="./courses/${course.coursesID}/notify">Notify</a>
                                                    </#if>
                                                </#if>
                                            <#else>
                                                <!-- User is Lector, but not courseOwner -->
                                                <#if course.getCoursesState() == "New"
                                                || course.getCoursesState() == "Open"
                                                || course.getCoursesState() == "Ready">
                                                    <a class="btn btn-mini"
                                                       href="./courses/${course.coursesID}/subscribe"">Subscribe</a>
                                                </#if>
                                            </#if>
                                            <!-- User is not Lector -->
                                        <#else >
                                            <#if manager??>
                                                <#if course.getCoursesState() == "Proposal">
                                                    <a class="btn btn-mini"
                                                       href="./courses/${course.coursesID}/approve">Approve</a>
                                                </#if>
                                                <#if course.getCoursesState() == "New"
                                                || course.getCoursesState() == "Open"
                                                || course.getCoursesState() == "Ready">
                                                    <a class="btn btn-mini"
                                                       href="./courses/${course.coursesID}/subscribe"">Subscribe</a>
                                                </#if>
                                            <#else >
                                                <#if course.getCoursesState() == "New"
                                                || course.getCoursesState() == "Open"
                                                || course.getCoursesState() == "Ready">
                                                    <a class="btn btn-mini"
                                                       href="./courses/${course.coursesID}/subscribe"">Subscribe</a>
                                                </#if>
                                            </#if>
                                        </#if>
                                    </div>
                                </td>
                            </tr>
                            </#if>
                        </#if>
                    </#if>
                </#list>
            </#if>
        </#if>
        </tbody>
    </table>
</div>
</body>
</html>