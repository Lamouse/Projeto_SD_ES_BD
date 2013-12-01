<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="jsp/auth_verification.jsp"></jsp:include>

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
                <s:a href="watchlist">Watchlist</s:a>
                <s:a href="halloffame">Hall of Fame</s:a>
                <a href="creativezone.jsp">Creative Zone</a>
                <s:a href="userdetails">User details</s:a>
            </ul>
       	</div>
		<br />
        <marquee class="notificacao" behavior="SCROLL" direction="RIGHT">Não existem notificações</marquee>
        <br /><br />
        
        <fieldset >
            <legend>User details</legend>
            <br />
            
            <label  class="text1">Username:</label>    <c:out value="${userBean.user}"/>
            <br />
            <label  class="text1">ID User:</label>     <s:property value="id_user"/>
            <br />        
            <label  class="text1">Money:</label>       
            <c:choose>
                <c:when test="${id_user == 0}">
                    wow such deicoins!
		</c:when>
                <c:otherwise>
                    <s:property value="money"/> Deicoins
                </c:otherwise>
            </c:choose>
            <br /><br />
        </fieldset>
        <br />
        <fieldset >
            <legend>History</legend>
            <br>
            <textArea readonly style="width:100%; height:200px; text-align: left"><s:property value="hist"/></textarea>
            <br><br>
        </fieldset>
        <br><br><br>
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div>  
    </body>
</html>