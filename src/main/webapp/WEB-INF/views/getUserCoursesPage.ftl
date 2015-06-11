<#-- @ftlvariable name="categoryList" type="java.util.Collection<com.epam.edu.jtc.entity.Category>" -->
<#-- @ftlvariable name="listCoursesFilteredByCategory" type="java.util.List<com.epam.edu.jtc.entity.Courses>" -->
<#-- @ftlvariable name="userNameAuth" type="String" -->
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
        <a class="btn" href="./">Courses</a>
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
            <#if listCoursesFilteredByCategory??>
                <#list listCoursesFilteredByCategory as course> <!-- Courses list-->
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
                                <div class="btn-group">
                                    <a class="btn btn-mini"
                                       href="./courses/${course.coursesID}/attend"">Attendee</a>
                                </div>
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
                                        <a class="btn btn-mini"
                                           href="./courses/${course.coursesID}/evaluation"">Evaluate</a>
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
                                                        || course.getCoursesState() == "Rejected">
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
                                                </#if>
                                            <#else>
                                                <!-- User is Lector, but not courseOwner -->
                                                <a class="btn btn-mini"
                                                   href="./courses/${course.coursesID}/subscribe"">Subscribe</a>
                                            </#if>
                                            <!-- User is not Lector -->
                                        <#else >
                                            <a class="btn btn-mini"
                                               href="./courses/${course.coursesID}/subscribe"">Subscribe</a>
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