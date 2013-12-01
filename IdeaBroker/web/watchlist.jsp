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
            <legend>Watchlist</legend>
            <c:forEach items="${lista}" var="value">
                <br>
                <div>
                    <div class="left_ideia">
                    <br>
                    <label id="text"> ID Idea: </label> 
                    <c:out value="${value.one}" />
                    <br><br>
                    </div>
                    <c:choose>
                        <c:when test="${value.three == 0}">
                            <div class="right_ideia">
                            <fieldset>
                                <legend>Buy Shares</legend>
                                <s:form action="buyshare" method="post">
                                    <s:hidden name="iduser" value='%{#session.userBean.id}' />
                                    <s:hidden name="idideia" value='%{#attr.value.one}' />
                                    <c:choose>
                                        <c:when test="${userBean.id != 0}">
                                            <s:textfield name="nr" label="Nr Share"/>
                                            <s:textfield name="price" label="Buy Price"/>
                                            <s:textfield name="newprice" label="New Price"/>
                                         </c:when>
                                    </c:choose>
                                    <s:submit value='Buy Shares' align="center"/> 
                                </s:form>
                                <!--<s:property value="idideia" />-->
                            </fieldset>
                            </div>
                        </c:when>
                    </c:choose>
                    <div style="clear:both"/> 
                    Message: <br />
                    <textArea readonly style="width:100%; height:150px; text-align: left"> <c:out value="${value.two}" /></textarea>    
                    <br />

                    <center>
                        <s:form>
                            <s:hidden name="idideia" value='%{#attr.value.one}' />
                            <s:submit value="Attachments" align="center" cssStyle="width:100px"/>
                        </s:form>
                        <s:form action="removewatchlist" method="post">
                            <s:hidden name="id_user" value='%{#session.userBean.id}' />
                            <s:hidden name="id_ideia" value='%{#attr.value.one}' />
                            <s:submit value="Remove" align="center" cssStyle="width:100px"/>
                        </s:form>
                    </center>


                    <br /><br />
                </div>

                <hr>
            </c:forEach>
            
        </fieldset>
        <br><br><br>
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div>         
    </body>
</html>