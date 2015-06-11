<#-- @ftlvariable name="userNameAuth" type="String" -->
<#-- @ftlvariable name="categoryList" type="com.epam.edu.jtc.entity.Category" -->
<#-- @ftlvariable name="errors" type="java.lang.String[]" -->
<#-- @ftlvariable name="createForbidden" type="String" -->
<#-- @ftlvariable name="mainErrorMess" type="java.lang.String" -->
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../css/style.css"/>
</head>
<body>

<div class="container">
    <header>
        <h1>
            Create Course
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


<#if createForbidden??>
    <div class="alert alert-error">
        <div>
            <strong>${mainErrorMess}</strong>
        </div>
        <ul>
            <li>${createForbidden}</li>
        </ul>
    </div>
    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="../courses">Back</a>
        </div>
    </div>

<#else>
    <#if errors??>
        <div class="alert alert-error">
            <div>
                <strong>${mainErrorMess}</strong>
            </div>
            <ul>
                <#list errors as error>
                    <li>${error}</li>
                </#list>
            </ul>
        </div>
    </#if>

    <form action="./create" method="post" class="form-horizontal">
        <fieldset>
            <!-- Owner Name -->
            <input type="hidden" name="coursesOwner" value="${userNameAuth}">
            <input type="hidden" name="newCoursesState" value="Draft">
            <!-- Course Name -->
            <div class="control-group">
                <label class="control-label">Name</label>
                <div class="controls">
                    <input name="courseName" id="titleField" class="span5" type="text"/>
                </div>
            </div>
            <!-- Course Category -->
            <div class="control-group">
                <label class="control-label">Category</label>
                <div class="controls">
                    <select id="categoryField" class="span5" name="courseCategory">
                        <option></option>
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
                    <textarea name="courseDescr" id="descriptionField" class="span5" rows="3"></textarea>
                </div>
            </div>

            <!-- Course Link -->
            <div class="control-group">
                <label class="control-label">Links</label>
                <div class="controls">
                    <textarea name="courseLink" id="linksField" class="span5" rows="3"></textarea>
                </div>
            </div>
            <!-- Buttons -->
            <div class="form-actions">
                <button id="createButton" class="btn btn-primary" type="submit">Create</button>
                <a class="btn" href="../courses">Cancel</a>
            </div>
        </fieldset>
    </form>
</#if>

</div>
</body>
</html>
