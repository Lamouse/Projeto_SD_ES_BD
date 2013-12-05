<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="jsp/auth_verification.jsp"></jsp:include>

<html>
    <head>
        <!--começa o meu-->
          <script type="text/javascript">

        var websocket;

        window.onload = function() { // execute once the page loads
            initialize();
            document.getElementById("chat").focus();
            document.getElementById("username").focus();
        }

        function initialize() { // URI = ws://10.16.0.165:8080/chat/chat
            connect('ws://' + window.location.host + '/ProjectoMeta2Meu/chat');
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
            document.getElementById('chat').onkeydown = function(key) {
                if (key.keyCode == 13)
                    doSend(); // call doSend() on enter key
            };
            document.getElementById('username').onkeydown = function(key) {
                if (key.keyCode == 13)
                    doSend(); // call doSend() on enter key
            };
        }
        
        function onClose(event) {
            writeToHistory('WebSocket closed.');
            document.getElementById('chat').onkeydown = null;
            document.getElementById('username').onkeydown = null;
        }
        
        function onMessage(message) { // print the received message
            writeToHistory(message.data);
        }
        
        function onError(event) {
            writeToHistory('WebSocket error (' + event.data + ').');
            document.getElementById('chat').onkeydown = null;
            document.getElementById('username').onkeydown = null;
        }
        
        function doSend() {
            var message = document.getElementById('chat').value;
            document.getElementById('chat').value = '';
            if (message != '')
                websocket.send(message); // send the message
            
            var username = document.getElementById('username').value;
            document.getElementById('username').value = '';
            if (username != '')
                websocket.send("username " + username); // send the message
        }

        function writeToHistory(text) {
            var history = document.getElementById('history');
            var markee = document.getElementById('notificacao');
            markee.textContent = "LOL"
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = text;
            history.appendChild(p);
            while (history.childNodes.length > 25)
                history.removeChild(console.firstChild);
            history.scrollTop = history.scrollHeight;
        }
    </script>
        
        <!-- começa o projecto mesmo-->
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
        <marquee id="notificacao" behavior="SCROLL" direction="RIGHT">Não existem notificações</marquee>
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