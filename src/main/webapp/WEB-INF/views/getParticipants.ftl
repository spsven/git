<#-- @ftlvariable name="subscriberList" type="java.util.List<com.epam.edu.jtc.entity.User>" -->
<#-- @ftlvariable name="attendList" type="java.util.List<com.epam.edu.jtc.entity.User>" -->
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
            Course Participants
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
    <!-- no errors-->
<#else>
    <!-- Курс найден? -->
    <#if course??>

        <div class="form-horizontal">
            <div class="control-group">
                <div class="control-label">Name</div>
                <div id="titleField" class="controls text">${course.getCoursesName()}</div>
            </div>
            <div class="control-group">
                <div class="control-label">Lecturer</div>
                <div id="ownerField" class="controls text">${course.getCoursesOwner()}</div>
            </div>
            <#if subscriberList??>
                <div class="control-group">
                    <div class="control-label">Subscribers</div>
                    <div id="subscribersList" class="controls text">
                        <ul class="unstyled">
                            <#list subscriberList as subscribeUser>
                                <li>${subscribeUser.getUsername()}</li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if attendList??>
                <div class="control-group">
                    <div class="control-label">Attendee</div>
                    <div id="attendeeList" class="controls text">
                        <ul class="unstyled">
                            <#list attendList as attendUser>
                                <li>${attendUser.getUsername()}</li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </#if>
            <div class="form-actions">
                <a class="btn" href="../../courses">Back</a>
            </div>
        </div>

    </#if>
</#if>

</div>
</body>
</html>

