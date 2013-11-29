<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="jsp/auth_verification.jsp"></jsp:include>

<%
    direstruts.model.UserBean user = (direstruts.model.UserBean) session.getAttribute("userBean");
    int user_id = user.getId();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>IdeaBroker</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="logo" align="center">
            IdeaBroker
        </div>
    	<div id="menu" align="center">
        	<ul>
                <a href="bysearch.jsp">By search</a>
                <s:a href="bytopic">By topic</s:a>
                <s:a href="portfolio">Portfolio</s:a>
                <a href="watchlist.jsp">Watchlist</a>
                <s:a href="halloffame">Hall of Fame</s:a>
                <a href="creativezone.jsp">Creative Zone</a>
                <s:a href="userdetails">User details</s:a>
            </ul>
       	</div>
		<br />
        <marquee class="notificacao" behavior="SCROLL" direction="RIGHT">Não existem notificações</marquee>
        <br /><br />
        
        <div class="left_ideia">
            <fieldset>
            <legend>Create Topic</legend>
            <br>
            <center>
                Type the name of the topic
                <br><br>
                <s:form action="createtopic">
                    <s:textfield name="topic"/>
                    <s:submit value='Create' cssClass="buttonlogin" align="center"/>
                </s:form>

                <br>
                <br>
            </center>
            </fieldset>
        </div>
        <div class="right_ideia">
            <fieldset>
                <legend>Create Idea</legend>
                <br>
                <s:form action="createidea" method="post" enctype="multipart/form-data">
                    <s:hidden name="iduser" value='%{#session.userBean.id}' />
                    <s:textfield name="topic" style="width:500px;" label="Type the ids of the topics that this idea will be associated" labelposition="top"/>
                    <s:textarea name="ideiaText" rows="4" style="width:500px;" label="Message" labelposition="top"/>
                    <s:textfield name="invest" style="width:50px;" label="Investment" labelposition="top"/>
                    <s:file name="fileUpload" cssStyle="margin:20px 0 20px 0" labelposition="top"/>
                    <s:submit value='Create' cssClass="buttonlogin" align="center"/>
                </s:form>
                
                <br><br>
                Note: the total amount of the idea will be decremented to your balance
            </fieldset>
        </div>
        <div style="clear:both">
            <br><br><br>
        </div>
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div> 
    </body>
</html>