<%-- 
    Document   : Main
    Created on : Apr 8, 2015, 9:38:21 AM
    Author     : Sergey
--%>
${param.name}
<section>
        <article>
            <h1>${user.name}</h1>
            <div class="user-info" id="userLogin">
                User login: ${user.login}
            </div>
            <div class="user-info">
                User password: ${user.password}
            </div>
            <div class="user-info">
                User email: ${user.email}
            </div>
        </article>
</section>

<form method="POST" action="searchPeople">
    <p>
        <label for="loginPattern">Search for person:</label>
        <input type="text" name="loginPattern" id="loginPattern" />
    </p>
    <p>
        <button type="submit">Search</button>
    </p>
</form>
            
<section> 
    <table style="width:25%">         
        <c:forEach var="friend" items="${chatsWith}">
            <tr>
                <td>
                    <a href="#"><img alt="user icon" id="user-icon" scr=""/></a>
                </td>
                <td>
                    <h1>Login: <a href="profile?messfrom=${friend.login}">${friend.login}</a></h1>
                </td>
            </tr>
        </c:forEach>
    <table>
</section>   

<section> 
    <table style="width:50%" id="messageTable">         
        <c:forEach var="message" items="${messages}">
            <tr>
                <td>
                    message from ${message.fromUsersLogin.login} to ${message.toUsersLogin.login} text: ${message.text}  ${message.date}
                </td>
            </tr>
        </c:forEach>
    <table>
        
        
        <c:if test="${not empty friend}">
            <p>
                New message:<Br>
                <textarea name="newMessage" cols="40" rows="3" id="newMessage"></textarea>
            </p>
            <p>
                <input value="Send" onclick="sendMessage('${user.login}','${friend}');" type="button">
            </p>
            <hr/>
            <textarea readonly="readonly" id="messages" rows="15" cols="60"></textarea>
        </c:if>
        
        <!--<c:if test="${not empty friend}">
            <form method="POST" action="profile">
                <p>
                    New message:<Br>
                    <textarea name="newMessage" cols="40" rows="3" id="newMessage"></textarea>
                </p>
                <p>
                    <button type="submit">Send</button>
                </p>
                <input type ="hidden" name="messfrom" value=${friend}>
            </form>
        </c:if>-->
</section> 
   
<script type="text/javascript">
    var webSocket = new WebSocket("ws://localhost:8080/Messanger/websocket");
    var messages=document.getElementById("messages");
    var message=document.getElementById("newMessage");

    webSocket.onopen=function(message){
        messages.value+="Connection established \n";
    };

    webSocket.onclose=function(message){
        messages.value+="Connection closed \n";
    }

    webSocket.onerror=function (message){
        messages.value+="Error \n";
    }

    function sendMessage(userLogin, friendLogin){
        var msg = message.value;

        var MessageAction = {
            action: "add",
            from: userLogin,
            to: friendLogin,
            text: msg
        };
        webSocket.send(JSON.stringify(MessageAction));

        messages.value+="Send to server:"+message.value+" \n";
        message.value="";
    }

    webSocket.onmessage=function(message){
        var msg = JSON.parse(message.data);
        if (msg.action === "add") {
            var messageTable = document.getElementById("messageTable");
            var row = messageTable.insertRow(0);

            // Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
            var cell1 = row.insertCell(0);

            // Add some text to the new cells:
           //message from ${message.fromUsersLogin.login} to ${message.toUsersLogin.login} text: ${message.text}  ${message.date}
            cell1.innerHTML = "message from " + msg.from + " to " + msg.to + " text: " + msg.text;

        }
        messages.value+="Get from server: "+message.data+" \n";
    };
</script>