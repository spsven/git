<#-- @ftlvariable name="deleteForbidden" type="String" -->
<#-- @ftlvariable name="errors" type="String" -->
<#-- @ftlvariable name="mainErrorMess" type="java.lang.String" -->
<#-- @ftlvariable name="course" type="com.epam.edu.jtc.entity.Courses" -->
<#-- @ftlvariable name="userNameAuth" type="String" -->
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>
            Delete Course
            <div class="logout">
            <span id="currentUserLogin">
            <#if userNameAuth??>
                    ${userNameAuth}
                </#if>
            </span>
                <a href="../../logout">
                    <td><i class="icon-off"></i></td>
                </a>
            </div>
        </h1>
    </header>


<#if deleteForbidden??>
    <div class="alert alert-error">
        <div>
            <strong>${mainErrorMess}</strong>
        </div>
        <ul>
            <li>${deleteForbidden}</li>
        </ul>
    </div>

    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="../../courses">Back</a>
        </div>
    </div>


<#else>
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
                <a class="btn" href="../../courses">Back</a>
            </div>
        </div>
    <#else>

        <#if course??>

            <form action="./delete" method="post" class="form-horizontal">
                <fieldset>
                    <!-- Course Name -->
                    <div class="control-group">
                        <div class="control-label">Name</div>
                        <div id="titleField" class="controls text">${course.coursesName}</div>
                    </div>
                    <!-- Course Category -->
                    <#assign categoryName = categoryService.findCategoryNameByCourseID(course.getCoursesID())>
                    <div class="control-group">
                        <div class="control-label">Category</div>
                        <div id="categoryField" class="controls text">${categoryName}</div>
                    </div>
                    <div class="form-actions">
                        <button id="deleteButton" class="btn btn-danger" type="submit">Delete</button>
                        <a class="btn" href="../../courses">Cancel</a>
                    </div>







                </fieldset>
            </form>
        </#if>


    </#if>
</#if>


</div>
</body>
</html>
