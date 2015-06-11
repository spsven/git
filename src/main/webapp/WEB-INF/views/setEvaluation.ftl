<#-- @ftlvariable name="course" type="com.epam.edu.jtc.entity.Courses" -->
<#-- @ftlvariable name="errors" type="String" -->
<#-- @ftlvariable name="mainErrorMess" type="java.lang.String" -->
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
            Evaluation
            <div class="logout">
            <span id="currentUserLogin">
                <!-- Залогинен или нет -->
            <#if userNameAuth??>
                <!-- Login -->
            ${userNameAuth}
            </#if>
            </span>
                <a href="../../logout">
                    <td><i class="icon-off"></i></td>
                </a>
            </div>
        </h1>
    </header>
    <!-- Есть ошибки?-->
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
        <#if errorsMark??>
            <div class="alert alert-error">
                <div>
                    <strong>${mainErrorMess}</strong>
                </div>
                <ul>
                    <li>${errorsMark}</li>
                </ul>
            </div>
        </#if>

        <form action="./evaluation" method="post" class="form-horizontal">
            <fieldset>
                <div class="control-group">
                    <div class="control-label">
                        Course
                    </div>
                    <div id="titleField" class="controls text">
                         ${course.coursesName}
                    </div>
                </div>
                <div class="control-group">
                    <div class="control-label">
                        Lecturer
                    </div>
                    <div id="ownerField" class="controls text">
                       ${course.coursesOwner}
                    </div>
                </div>
                <div class="control-group">
                    <label id="gradeField" class="control-label">Grade</label>

                    <div class="controls">
                        <input name="evaluation" class="span2" type="text"/>
                    </div>
                </div>
                <div class="form-actions">
                    <button id="evaluateButton" class="btn btn-success" type="submit">Evaluate</button>
                    <a class="btn" href="../../courses">Cancel</a>
                </div>
            </fieldset>
        </form>
    </#if>
</#if>


</div>
</body>
</html>

