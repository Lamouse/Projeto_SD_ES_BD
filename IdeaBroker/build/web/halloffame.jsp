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
        <script type="text/javascript">
            var websocket;

            window.onload = function() { // execute once the page loads
                initialize();
            }

            function initialize() { // URI = ws://10.16.0.165:8080/chat/chat
                connect('ws://' + window.location.host + '/IdeaBroker/chat');
            }

            function connect(host) { // connect to the host websocket servlet
                if ('WebSocket' in window)
                    websocket = new WebSocket(host);
                else if ('MozWebSocket' in window)
                    websocket = new MozWebSocket(host);
                else {
                    writeToHistory('Get a real browser which supports WebSocket.');
                    return;
                }

                websocket.onopen    = onOpen; // set the event listeners below
                websocket.onclose   = onClose;
                websocket.onmessage = onMessage;
                websocket.onerror   = onError;
            }

            function onOpen(event) {
                writeToHistory('Connected to ' + window.location.host + '.');
                websocket.send('${userBean.id}');
            }

            function onClose(event) {
                writeToHistory('WebSocket closed.');
            }

            function onMessage(message) { // print the received message
                writeToHistory(message.data);
            }

            function onError(event) {
                writeToHistory('WebSocket error (' + event.data + ').');
            }
            
            function writeToHistory(text) {
                var markee = document.getElementById('history');
                markee.textContent = text;
            }
        </script>       
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
        <marquee id="history" class="notificacao" behavior="SCROLL" direction="RIGHT">${userBean.notif}</marquee>
        <br /><br />
        
        <fieldset >
            <legend>Hall of Fame</legend>
            
            <c:forEach items="${listBean.list}" var="value">
                <div>
                    <div>
                            <br>
                        <label id="text"> ID Idea </label>
                        <c:out value="${value.one}" /><br />
                        <div class="right_ideia">
                            <label id="text"> Initial Price </label>
                            <c:out value="${value.five}" /><br />
                            <label id="text"> Final Price </label>
                            <c:out value="${value.four}" /><br>
                        </div>
                        <br />
                        <label id="text"> Valuation </label>
                        <c:out value="${value.two}" /><br>
                    </div>

                    <div style="clear:both"/>
                    <br>Message: <br /><br />
                    <textArea readonly class="msg1"><c:out value="${value.three}" /></textarea>    
                    <br />

                    <center>
                        <s:form action="download">
                            <s:hidden name="ididea" value='%{#attr.value.one}'/>
                            <s:submit value='Attachments' cssClass="buttonlogin" align="center"/>
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

<% session.removeAttribute("listBean"); %>