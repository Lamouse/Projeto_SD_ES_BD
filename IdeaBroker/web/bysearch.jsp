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
        <marquee id="history" class="notificacao" behavior="SCROLL" direction="RIGHT"></marquee>
        <br /><br />
        
        <fieldset >
            <legend>Search</legend>
            <br> 
            <s:form action="search" cssStyle="margin-left:auto;margin-right:auto">
                <s:select name="type" list="{'Topic','Idea'}" label="Search" cssStyle="width:150px;"/>
                <s:textfield name="id" cssStyle="width:150px;" label="ID"/>
                <s:submit value="Search" align="center"/>
            </s:form>
        </fieldset>
            
    	<br />
        
        <fieldset >
            <legend>Outcomes</legend>
            <c:choose>
		<c:when test="${nome != null}">
                    <h3> Topic: <c:out value="${nome}"/> </h3>
		</c:when>
            </c:choose>
            
            <br>
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
                        <s:form action="download">
                            <s:hidden name="ididea" value='%{#attr.value.one}' />
                            <s:submit value="Attachments" align="center" cssStyle="width:100px"/>
                        </s:form>
                        <c:choose>
	                        <c:when test="${value.three == 0}">
		                        <s:form action="addwatchlist" method="post">
		                            <s:hidden name="id_user" value='%{#session.userBean.id}' />
		                            <s:hidden name="id_ideia" value='%{#attr.value.one}' />
		                            <s:submit value="Add Watchlist" align="center" cssStyle="width:100px"/>
		                        </s:form>
		                    </c:when>
		                </c:choose>
                    </center>


                    <br /><br />
                </div>

                <hr>
            </c:forEach>
        </fieldset>
        
    	<br><br><br>
        Stock exchange of ideas:
        <div id="ideia_hist"></div>
        <br><br><br>
        
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div> 
    </body>
</html>