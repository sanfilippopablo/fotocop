<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

Form
<c:if test="${ !isLogged }">
	<form class="" action="/login" method="post">
	
	  <input type="text" <c:if test='${validationManager.fieldHasError("username")}'>style="background-color: red"</c:if> name="username" value="" />
	  ${validationManager.errorForField("username")}
	  <input type="text" name="password" value="" />
	  ${validationManager.errorForField("password")}
	  <input type="submit" name="name" value="">
	
	
	</form>
</c:if>