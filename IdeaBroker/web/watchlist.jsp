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
                <a href="watchlist.jsp">Watchlist</a>
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
            
            <div>
            	<div class="right_ideia">
                    <fieldset>
                    <legend>Buy Shares</legend>
                    <div class="left_ideia">
                        <input type='submit' name='Buy Shares' value='Buy Shares' style="width:100px"/>
                    </div>
                    <div class="right_ideia">
                        <label id="text_ideia"> Nº Shares </label>
                        <input type='text' name='username' id='textfield_ideia' />
                        <br />
                        <label id="text_ideia"> Price </label>
                        <input type='text' name='username' id='textfield_ideia' />
                    </div>
                    </fieldset>
                </div>
                <div>
                	<br>
                    <label id="text"> ID Idea </label> XXX <br />
                    <label id="text"> Price </label> XXX <br>
                </div>
                
                <div style="clear:both"/>
                <br>Message: <br /><br />
                <div class="msg1">
                    dsagfgfadfadsfsdfasdcscaxcaSCVDAVZCADSVABDFVDDASFVDSSCASDVASXADSCAscASDCSDACADSDCSADCASDCSADCSDACASDCDSACSDACDSAVFSADVSADFVD
                    sdfsadf<br />
                    sadf<br />
                    saf<br />
                    sd<br />
                    f<br />
                    sadf<br />
                    a<br />
                </div>
                <br />
    
                <center>
                    <input type='submit' name='Attachments ' value='Attachments' style="width:100px"/>
                </center>
                <br><br>
        	</div>
			<hr>
            <div>
            	<div class="right_ideia">
                    <fieldset>
                    <legend>Buy Shares</legend>
                    <div class="left_ideia">
                        <input type='submit' name='Buy Shares' value='Buy Shares' style="width:100px"/>
                    </div>
                    <div class="right_ideia">
                        <label id="text_ideia"> Nº Shares </label>
                        <input type='text' name='username' id='textfield_ideia' />
                        <br />
                        <label id="text_ideia"> Price </label>
                        <input type='text' name='username' id='textfield_ideia' />
                    </div>
                    </fieldset>
                </div>
                <div>
                	<br>
                    <label id="text"> ID Idea </label> XXX <br />
                    <label id="text"> Price </label> XXX <br>
                </div>
                
                <div style="clear:both"/>
                <br>Message: <br /><br />
                <div class="msg1">
                    dsagfgfadfadsfsdfasdcscaxcaSCVDAVZCADSVABDFVDDASFVDSSCASDVASXADSCAscASDCSDACADSDCSADCASDCSADCSDACASDCDSACSDACDSAVFSADVSADFVD
                    sdfsadf<br />
                    sadf<br />
                    saf<br />
                    sd<br />
                    f<br />
                    sadf<br />
                    a<br />
                </div>
                <br />
    
                <center>
                    <input type='submit' name='Attachments ' value='Attachments' style="width:100px"/>
                </center>
                <br><br>
        	</div>
        </fieldset>
        <br><br><br>
        <div id="out">
            <s:a href="logout">Logout</s:a>
        </div>         
    </body>
</html>