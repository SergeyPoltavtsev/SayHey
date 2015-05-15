<%-- 
    Document   : searchPeople
    Created on : May 6, 2015, 1:09:44 AM
    Author     : Sergey
--%>

<form method="POST" action="searchPeople">
    <p>
        <label for="loginPattern">Search for person:</label>
        <input type="text" name="loginPattern" id="loginPattern" value="${loginPattern}"/>
    </p>
    <p>
        <button type="submit">Search</button>
    </p>
</form>
<section> 
    <table style="width:25%"> 
        <c:forEach var="entry" items="${foundPeople}">
            <form method="POST" action="searchPeople">
                <tr>
                    <td>
                        <img alt="user icon" id="user-icon" scr=""/>
                    </td>
                    <td>
                        <h1>Login: ${entry.key.login}</h1>
                    </td>
                    <td>
                        <button type="submit"><c:out value ="${entry.value?'Add':'Remove'}"/></button>
                    </td>
                    <input type ="hidden" name="personLogin" value=${entry.key.login}>
                    <input type="hidden" name="loginPattern" value="${loginPattern}"/>
                </tr>
            </form>
        </c:forEach>
    <table>
</section>
