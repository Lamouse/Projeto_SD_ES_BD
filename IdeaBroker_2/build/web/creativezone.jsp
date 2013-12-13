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
                if(text.indexOf('all:')===0){
                    text = text.replace('all:','');
                    var history = document.getElementById('ideia_hist');
                    var p = document.createElement('p');
                    p.style.wordWrap = 'break-word';
                    p.innerHTML = text;
                    history.appendChild(p);
                    while (history.childNodes.length > 25)
                        history.removeChild(console.firstChild);
                    history.scrollTop = history.scrollHeight;
                }
                else{
                    var markee = document.getElementById('history');
                    markee.textContent = text;
                }
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

        Stock exchange of ideas:
        <div id="ideia_hist"></div>
        <br><br><br>
        
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div> 
    </body>
</html>