<#-- @ftlvariable name="userNameAuth" type="String" -->
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>
            Logout
        </h1>
    </header>
    <form class="form-horizontal">
        <fieldset>
            <div class="control-group">
                <div class="controls text">
                    <#if userNameAuth??>
                        You currently logged as '<span id="currentUserLogin">${userNameAuth}</span>'.
                    </#if>
                </div>
                <div class="controls text">
                    Are you sure you want to logout?
                </div>
            </div>
            <div class="form-actions">
                <a  id="logoutButton"
                        class="btn btn-danger" href="./logoutl" type="submit"> Logout</a>
                <a class="btn" href="./courses">Back</a>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>