<%-- 
    Document   : Main
    Created on : Apr 8, 2015, 9:38:21 AM
    Author     : Sergey
--%>
${param.name}
<section>
        <article>
            <h1>${user.name}</h1>
            <div class="user-info">
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
        <c:forEach var="friend" items="${user.friendsCollection}">
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
    <table style="width:50%">         
        <c:forEach var="message" items="${messages}">
            <tr>
                <td>
                    message from ${message.fromUsersLogin.login} to ${message.toUsersLogin.login} text: ${message.text}  ${message.date}
                </td>
            </tr>
        </c:forEach>
    <table>
        <c:if test="${not empty friend}">
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
        </c:if>
</section> 
            
            