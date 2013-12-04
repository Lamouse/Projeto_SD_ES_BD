<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="jsp/auth_verification.jsp"></jsp:include>

<% session.removeAttribute("updatepriceBean"); %>

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
            <legend>Portfolio</legend>
            <div>
                <c:forEach items="${listBean.list}" var="value">
                    <div class="right_ideia">
                        <fieldset>
                        <legend>Update Shares</legend>
                        <s:form action="changeprice" method="post">
                            <label class="text_ideia"> Nº Shares </label>
                            <c:out value="${value.five}" /><br /> 
                            <s:hidden name="updatepriceBean.id_idea" value='%{#attr.value.one}' />
                            <s:textfield name="updatepriceBean.price" label="Price"/>
                            <s:submit value='Update Shares' name="buttonLogin" align="center"/> 
                        </s:form>
                        </fieldset>
                    </div>
                    <div>
                            <br>
                        <label class="text"> ID Idea </label>
                        <c:out value="${value.one}" /><br />
                        <label class="text"> Price </label>
                        <c:out value="${value.four}" /><br />
                        <label class="text"> Topic </label>
                        <textArea readonly style="width:100px;"><c:out value="${value.six}" /></textarea>    
                    </div>

                    <div style="clear:both"/>
                    <br>Message: <br /><br />
                    <textArea readonly class="msg1"><c:out value="${value.two}" /></textarea>    
                    <br />
                    <br />

                    <center>
                        <s:form action="download">
                            <s:hidden name="ididea" value='%{#attr.value.one}' />
                            <s:submit value="Attachments" align="center" cssStyle="width:100px"/>
                        </s:form>
                        <s:form action="deleteidea">
                            <s:hidden name="ididea" value='%{#attr.value.one}' />
                            <s:hidden name="iduser" value='%{#session.userBean.id}' />
                            <s:submit value="Erase idea" style="width:100px"/>
                        </s:form>  
                    </center>
                    <br><br>
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
