<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>IdeaBroker</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="fb-root"></div>
        <script>
            window.fbAsyncInit = function() {
                FB.init({
                    appId: 1417726098459428, // App ID
                    channelUrl: '//' + window.location.hostname + '/channel', // Path to your   Channel File
                    status: true, // check login status
                    cookie: false, // enable cookies to allow the server to access the session
                    xfbml: true  // parse XFBML
                });

                FB.Event.subscribe('auth.authResponseChange', function(response) {
                    document.getElementById('token').value = response.authResponse.accessToken;
                });	
            };

            function Login() {
                if(document.getElementById("facebook").value === "Login Facebook"){
                    FB.login(function(response) {
                        if (response.authResponse)  {
                            getUserInfo();
                        }
                        else {
                            console.log('User cancelled login or did not fully authorize.');
                        }
                    },{scope: 'email,user_checkins,publish_actions,publish_stream,read_stream'});
                }
                else{
                    Logout();
                }
            }

            function getUserInfo() {
                FB.api('/me', function(response) {
                    var str="<b>Name</b> : "+response.name+"<br>";
                    str +="<b>Link: </b>"+response.link+"<br>";
                    str +="<b>Username:</b> "+response.username+"<br>";
                    str +="<b>id: </b>"+response.id+"<br>";
                    str +="<b>Email:</b> "+response.email+"<br>";
                    document.getElementById("facebook").value='Logout Facebook';
                    document.getElementById("status").innerHTML=str;
                    document.getElementById("fbid").value = response.id;
                });
            }

            function Logout() {
                FB.logout(function(){document.location.reload();});
            }

            // Load the SDK asynchronously
            (function(d){
               var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
               if (d.getElementById(id)) {return;}
               js = d.createElement('script'); js.id = id; js.async = true;
               js.src = "//connect.facebook.net/en_US/all.js";
               ref.parentNode.insertBefore(js, ref);
             }(document));
        </script>
        <br /><br /><br /><br /><br /><br /> 
        
        <fieldset style="width:40%; margin-left: auto; margin-right: auto">
            <legend>Login</legend>
            <s:form action="login" method="post" cssStyle="margin-left:auto; margin-right:auto; width:100%">
                <s:textfield id="name" name="userBean.user" label="Username*"/>
                <s:password id="pass" name="userBean.pass" label="Password*"/>
                <s:hidden id="fbid" name="fbid" value=""/>
                <s:hidden id="token" name="token" value=""/>
                <s:submit value='Login' cssClass="buttonlogin" align="center"/> 
            </s:form>
            <s:form action="openregister" cssStyle="margin-left:auto; margin-right:auto">
                <s:submit value='Register' cssClass="buttonlogin"/> 
            </s:form>
            <center>
                <input id="facebook" type="submit" value="Login Facebook" align="center" onclick="Login()">
            </center>
            <div id="status"></div>
        </fieldset>
    </body>
</html>