<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>IdeaBroker</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <br /><br /><br /><br /><br /><br /> 
        
        <fieldset style="width:40%; margin-left: auto; margin-right: auto">
            <legend>Register</legend>
            <s:form action="register" method="post" cssStyle="margin-left:auto; margin-right:auto; width:100%">
                <s:textfield name="user" label="Username*"/>
                <s:password name="pass" label="Password*"/>
                <s:submit value='Register' cssClass="buttonlogin" align="center"/> 
            </s:form>
        </fieldset>
    </body>
</html>