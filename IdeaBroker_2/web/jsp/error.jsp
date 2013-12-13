<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="auth_verification.jsp"></jsp:include>

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
        
        <br /><br /><br />
        <center>
            <label style="font-size: 30px">ERROR!</label>
        </center>
        <br><br><br>
        Stock exchange of ideas:
        <div id="ideia_hist"></div>
        <br><br><br>
        
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div> 
    </body>
</html>