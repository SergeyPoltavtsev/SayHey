<%-- 
    Document   : Registration
    Created on : Apr 8, 2015, 9:37:53 AM
    Author     : Sergey
--%>
<section>
    <article>
        <h1>Registration</h1>
        <div class="user-info">

            <c:if test="${notif ne null}">
                <div class="notif">
                    <span>${notif}</span> 
                </div>
            </c:if>

            <form method="POST" action="registration">
            <p>
                <label for="login">Login</label>
                <input type="text" name="login" id="login"/>
            </p>
            <p>
            <label for="email">E-Mail</label>
            <input type="email" name="email" id="email"/>
            </p>
            <p>
            <label for="password">Password</label>
            <input type="password" name="password" id="password"/>
            </p>
            <p>
            <label for="password2">Repeat password</label>
            <input type="password" name="password2" id="password2"/>
            </p>
            <p>
                <button type="submit">Sign up</button>
            </p>
            </form>
        </div>
    </article>
</section>

