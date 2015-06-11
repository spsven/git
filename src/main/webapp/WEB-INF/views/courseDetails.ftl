<#-- @ftlvariable name="averageMark" type="double" -->
<#-- @ftlvariable name="countSubscribers" type="int" -->
<#-- @ftlvariable name="countAttend" type="int" -->
<#-- @ftlvariable name="userRole" type="String" -->
<#-- @ftlvariable name="errors" type="String" -->
<#-- @ftlvariable name="mainErrorMess" type="java.lang.String" -->
<#-- @ftlvariable name="course" type="com.epam.edu.jtc.entity.Courses" -->
<#-- @ftlvariable name="userNameAuth" type="String" -->
<!-- Category course-->

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>
            Course Details
            <div class="logout">
            <span id="currentUserLogin">
            <#if userNameAuth??>
                    ${userNameAuth}
                </#if>
            </span>
                <a href="../logout">
                    <td><i class="icon-off"></i></td>
                </a>
            </div>
        </h1>
    </header>
    <!-- There is errors -->
<#if errors??>

    <div class="alert alert-error">
        <div>
            <strong>${mainErrorMess}</strong>
        </div>
        <ul>
            <li>${errors}</li>
        </ul>
    </div>

    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="../courses">Back</a>
        </div>
    </div>
<#else>
    <!-- There is no errors -->

    <!-- Count of Subscribers of course -->
    <#assign countSubscribers = countSubscribers>
    <!-- Count of Attendee of course -->
    <#assign countAttend = countAttend>
    <!-- AverageMark course -->
    <#assign averageMark = averageMark>
    <div class="form-horizontal">
        <#if course??>
            <!-- Course name -->
            <div class="control-group">
                <div class="control-label">Name</div>
                <div id="titleField" class="controls text">${course.coursesName}</div>
            </div>
            <!-- Course Owner -->
            <div class="control-group">
                <div class="control-label">Lecturer</div>
                <div id="ownerField" class="controls text">${course.coursesOwner}</div>
            </div>
            <!-- Course Category-->
            <#assign categoryName = categoryService.findCategoryNameByCourseID(course.getCoursesID())>
            <div class="control-group">
                <div class="control-label">Category</div>
                <div id="categoryField" class="controls text">${categoryName}</div>
            </div>
            <!--Course Description -->
            <div class="control-group">
                <div class="control-label">Description</div>
                <div id="descriptionField" class="controls text">${course.coursesDescription}</div>
            </div>
            <!-- Course Link -->
            <div class="control-group">
                <div class="control-label">Links</div>
                <div id="linksField" class="controls text">${course.coursesLinks}</div>
            </div>
            <!-- Count of Subscribers -->
            <#if countSubscribers != 0>
                <div class="control-group">
                    <div class="control-label">Subscribers</div>
                    <div id="subscribersField" class="controls text">
                        <a href="./${course.getCoursesID()}/participants">${countSubscribers}</a>
                    </div>
                </div>
            </#if>
            <!-- Count of Attendee -->
            <#if countAttend != 0>
                <div class="control-group">
                    <div class="control-label">Attendee</div>
                    <div id="attendeeField" class="controls text">
                        <a href="./${course.getCoursesID()}/participants">${countAttend}</a>
                    </div>
                </div>
            </#if>
            <!-- Average Mark-->
            <#if averageMark != 0>
                <div class="control-group">
                    <div class="control-label">Grade</div>
                    <div id="gradeField" class="controls text">
                                ${averageMark}
                            </div>
                </div>
            </#if>

            <div class="form-actions">
                <a class="btn" href="../courses">Back</a>
            </div>
        </#if>
    </div>
</#if>

</div>
</body>
</html>