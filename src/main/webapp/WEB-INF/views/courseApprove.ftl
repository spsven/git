<#-- @ftlvariable name="voteKM" type="com.epam.edu.jtc.entity.Vote" -->
<#-- @ftlvariable name="voteDM" type="com.epam.edu.jtc.entity.Vote" -->
<#-- @ftlvariable name="approveForbidden" type="String" -->
<#-- @ftlvariable name="course" type="com.epam.edu.jtc.entity.Courses" -->
<#-- @ftlvariable name="userNameAuth" type="String" -->
<#-- @ftlvariable name="mainErrorMess" type="java.lang.String" -->
<#-- @ftlvariable name="errors" type="String" -->
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
</head>

<body>
<div class="container">
    <header>
        <h1>
            Approve Course
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



<#if approveForbidden??>
    <div class="alert alert-error">
        <div>
            <strong>${mainErrorMess}</strong>
        </div>
        <ul>
            <li>${approveForbidden}</li>
        </ul>
    </div>

    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="../../courses">Back</a>
        </div>
    </div>


<#else>
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

    </#if>
    <form action="./approve" method="post" class="form-horizontal">
        <#if course??>
            <div class="control-group">
                <div class="control-label">Name</div>
                <div id="titleField" class="controls text">${course.coursesName}</div>
            </div>
            <div class="control-group">
                <div class="control-label">Lecturer</div>
                <div id="ownerField" class="controls text">${course.coursesOwner}</div>
            </div>
            <div class="control-group">
                <!-- Course Category-->
                <#assign categoryName = categoryService.findCategoryNameByCourseID(course.getCoursesID())>
                <div class="control-label">Category</div>
                <div id="categoryField" class="controls text">${categoryName}</div>
                <input type="hidden" name="categoryName" value="${categoryName}">
            </div>
            <div class="control-group">
                <div class="control-label">Description</div>
                <div id="descriptionField" class="controls text">${course.coursesDescription}</div>
            </div>
            <div class="control-group">
                <div class="control-label">Links</div>
                <div id="linksField" class="controls text">${course.coursesLinks}</div>
            </div>
            <!-- DM Manager -->
            <div class="control-group">
                <div class="control-label">Department Manager</div>
                <div id="dmField" class="controls text">Alex Ivanov</div>
            </div>
            <div class="control-group">
                <label class="control-label">Decision</label>

                <#if voteDM??>
                    <div id="dmField" class="controls text">${voteDM.getVoteDecision()}</div>

                <#else>
                    <div class="controls">
                        <select name="managerDecision" class="span5" <#if dmDisabled??>disabled="disabled"</#if> >
                            <option>Select one</option>
                            <option>Approve</option>
                            <option>Reject</option>
                        </select>
                    </div>
                </#if>
            </div>
            <div class="control-group">
                <label class="control-label">Reason</label>

                <#if voteDM??>
                    <div id="dmField" class="controls text">${voteDM.getVoteReason()}</div>

                <#else>
                    <div class="controls">
                            <textarea name="managerReason" class="span5" rows="3"
                                      <#if dmDisabled??>disabled="disabled"</#if>>

                            </textarea>
                    </div>
                </#if>
            </div>
            <!-- KM Manager -->
            <div class="control-group">
                <div class="control-label">Knowledge Manager</div>
                <div id="kmField" class="controls text">Xela Vonavi</div>
            </div>
            <div class="control-group">
                <label class="control-label">Decision</label>
                <#if voteKM??>
                    <div id="kmField" class="controls text">${voteKM.getVoteDecision()}</div>
                <#else>
                    <div class="controls">
                        <select name="managerDecision" class="span5" <#if kmDisabled??>disabled="disabled"</#if>>
                            <option>Select one</option>
                            <option>Approve</option>
                            <option>Reject</option>
                        </select>
                    </div>
                </#if>
            </div>
            <div class="control-group">
                <label class="control-label">Reason</label>
                <#if voteKM??>
                    <div id="kmField" class="controls text">${voteKM.getVoteReason()}</div>
                <#else>
                    <div class="controls">
                            <textarea name="managerReason" class="span5" rows="3"
                                      <#if kmDisabled??>disabled="disabled"</#if> >

                            </textarea>
                    </div>
                </#if>

            </div>
            <div class="form-actions actions-without-form">

                <button class="btn btn-primary" <#if disabledButton??> disabled = "disabled"</#if> >Save</button>
                <a class="btn" href="../../courses">Back</a>
            </div>
        </#if>
    </form>
</#if>
</div>
</body>
</html>
