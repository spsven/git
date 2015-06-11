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
            Update Course
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


<#if updateForbidden??>
    <div class="alert alert-error">
        <div>
            <strong>${mainErrorMess}</strong>
        </div>
        <ul>
            <li>${updateForbidden}</li>
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

            <form action="./update" method="post" class="form-horizontal">
                <fieldset>
                    <!-- Course Name -->
                    <div class="control-group">
                        <label class="control-label">Name</label>

                        <div class="controls">
                            <input name="courseNameForUpdate"
                                   id="titleField" class="span5" type="text" value="${course.coursesName}"/>
                        </div>
                    </div>
                    <!-- Course Category -->
                    <div class="control-group">
                        <label class="control-label">Category</label>

                        <div class="controls">
                            <select id="categoryField" class="span5" name="courseCategory">
                                <#if categoryList??>
                                    <#list categoryList as category>
                                        <option>${category.getCategoryName()}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>
                    </div>
                    <!-- Course Description -->
                    <div class="control-group">
                        <label class="control-label">Description</label>

                        <div class="controls">
                    <textarea name="courseDescrForUpdate"
                              id="descriptionField" class="span5" rows="3">${course.coursesDescription}</textarea>
                        </div>
                    </div>
                    <!-- Course Link -->
                    <div class="control-group">
                        <label class="control-label">Links</label>

                        <div class="controls">
                    <textarea name="courseLinkForUpdate"
                              id="linksField" class="span5" rows="3">${course.coursesLinks}</textarea>
                        </div>
                    </div>
                    <!-- Minimal Subscribers   -->
                    <div class="control-group">
                        <label class="control-label">Minimal Subscribers</label>

                        <div class="controls">
                            <input name="courseMinSubscriber"
                                   id="titleField" class="span5" type="text"
                                    <#if course.getCoursesMinSubscriber()!=0>
                                            value="${course.getCoursesMinSubscriber()}"
                            </#if>/>
                        </div>
                    </div>
                    <!-- Minimal Attend   -->
                    <div class="control-group">
                        <label class="control-label">Minimal Attendee</label>

                        <div class="controls">
                            <input name="courseMinAttend"
                                id="titleField" class="span5" type="text"
                                    <#if course.getCoursesMinAttend()!= 0>
                                            value="${course.getCoursesMinAttend()}"
                            </#if>/>
                        </div>
                    </div>
                    <!-- Buttons -->
                    <div class="form-actions">
                        <button name="updateButton" value="updateButton" id="updateButton"
                                class="btn btn-primary" type="submit">Update
                        </button>

                        <button name="reviewButton" value="reviewButton" id="reviewButton"
                                class="btn btn-warning" type="submit">Review
                        </button>

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