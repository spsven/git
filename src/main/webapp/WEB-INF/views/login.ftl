<#-- @ftlvariable name="error" type="String" -->
<#-- @ftlvariable name="mainErrorMess" type="java.lang.String" -->
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>Login</h1>
    </header>

    <#if error??>


    <div class="alert alert-error">
        <div>
            <strong>${mainErrorMess}</strong>
        </div>
        <ul>
            <li>${error}</li>
        </ul>
    </div>
    </#if>

    <form name='loginForm'
          action="j_spring_security_check" method='POST' class="form-horizontal">

        <fieldset>
            <div class="control-group">
                <label class="control-label">Login</label>
                <div class="controls">
                    <div class="input-prepend">
                        <span class="add-on">@</span>
                        <input type='text' name='username' id="loginField" class="span3" />
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Password</label>
                <div class="controls">
                    <input name='password' id="passwordField" class="span3" type="password"/>
                </div>
            </div>
            <div class="form-actions">
                <button name="submit" id="loginButton" class="btn btn-primary" type="submit">Login</button>
            </div>
        </fieldset>
    </form>


</div>
</body>
</html>