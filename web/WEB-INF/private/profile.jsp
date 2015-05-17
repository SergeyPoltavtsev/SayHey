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

<hr/>
        
<form method="POST" action="searchPeople">
    <p>
        <label for="loginPattern">Search for person:</label>
        <input type="text" name="loginPattern" id="loginPattern" />
    </p>
    <p>
        <button type="submit">Search</button>
    </p>
</form>
        
<hr/>

<div class="peopleChoise">
    <form method="POST" action="profile">
        <div>
            <input type="hidden" value="chatCollection" name="peopleCollection" id="peopleCollection" />
            <input value="Chats" type="submit">
        </div>
    </form>

    <form method="POST" action="profile">
        <div>
            <input type="hidden" value="friendsCollection" name="peopleCollection" id="peopleCollection" />
            <input value="Friends" type="submit">
        </div>
    </form>
</div>


<section > 
    <table style="width:25%" id="peopleTable">         
        <c:forEach var="friend" items="${chatsWith}">
            <tr>
                <td>
                    <a href="#"><img alt="user icon" id="user-icon" scr=""/></a>
                </td>
                <td>
                    <p>Login: <a href="profile?messfrom=${friend.login}&peopleCollection=${peopleCollection}">${friend.login}</a></p>
                </td>
            </tr>
        </c:forEach>
    <table>
</section>


<c:if test="${not empty friend}">
    <section> 
        <hr/>
        <p>
            <b>
            Chat with ${friend}
            </b>
        </p> 
        <table style="width:50%" id="messageTable">         
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td>
                        message from ${message.fromUsersLogin.login} to ${message.toUsersLogin.login} text: ${message.text}  ${message.date}
                    </td>
                </tr>
            </c:forEach>
        <table>
    
        <p>
            New message:<Br>
            <textarea name="newMessage" cols="40" rows="3" id="newMessage"></textarea>
        </p>
        <p>
            <input value="Send" onclick="sendMessage('${user.login}','${friend}');" type="button">
        </p>  
    </section> 
</c:if> 

<script type="text/javascript">
    var webSocket = new WebSocket("ws://localhost:8080/Messanger/websocket");
    var message=document.getElementById("newMessage");
    
    function sendMessage(userLogin, friendLogin){
        var msg = message.value;

        var MessageAction = {
            action: "add",
            from: userLogin,
            to: friendLogin,
            text: msg
        };
        webSocket.send(JSON.stringify(MessageAction));
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
            cell1.innerHTML = "message from " + msg.from + " to " + msg.to + " text: " + msg.text + " date: " + msg.date;
        }
    };

</script>