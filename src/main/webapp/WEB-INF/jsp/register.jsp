<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="lor" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  ~ Copyright 1998-2012 Linux.org.ru
  ~    Licensed under the Apache License, Version 2.0 (the "License");
  ~    you may not use this file except in compliance with the License.
  ~    You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~    Unless required by applicable law or agreed to in writing, software
  ~    distributed under the License is distributed on an "AS IS" BASIS,
  ~    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~    See the License for the specific language governing permissions and
  ~    limitations under the License.
  --%>
<jsp:include page="head.jsp"/>

<title>Регистрация пользователя</title>
<script src="/js/jquery.validate.pack.js" type="text/javascript"></script>
<script src="/js/jquery.validate.ru.js" type="text/javascript"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#registerForm").validate({
      rules : {
        password2: {
          equalTo: "#password"
        }
      }
    });
    $("#changeForm").validate();    
  });
</script>

<jsp:include page="header.jsp"/>
<%
     session.setAttribute("register-visited", Boolean.TRUE);
%>
<H1>Регистрация</H1>
Если вы уже регистрировались на нашем сайте и забыли пароль - вам
<a href="../../lostpwd.jsp">сюда</a>.

<form:form modelAttribute="form" method="POST" action="register.jsp" id="registerForm">
    <form:errors path="*" element="div" cssClass="error"/>
    <dl>
        <dt><label>Login:</label></dt>
        <dd><form:input path="nick" required="required" size="40"/></dd>

        <dt><label>E-mail:</label></dt>
        <dd><form:input path="email" type="email" required="required" cssClass="email" size="40"/></dd>

        <dt><label>Пароль:</label></dt>
        <dd><form:password path="password" size="40" required="required"/></dd>

        <dt><label>Подтвердите пароль:</label></dt>
        <dd><form:password path="password2" size="40" required="required"/></dd>

        <dt><label>Защита от робатов:</label></dt>
        <dd><lor:captcha/></dd>

        <dt class="button"><label>С <a href="/rules.jsp">правилами</a> ознакомился:</label><form:checkbox path="rules" value="okay" required="required"/></dt>
        <dd class="button"><input type=submit value="Зарегистрироваться"></dd>
    </dl>
</form:form>
<jsp:include page="footer.jsp"/>
