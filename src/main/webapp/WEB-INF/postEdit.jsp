<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>	Enter Page Title Here </title>
</head>
<body>

	<header><h1>MILBO'S PAWS</h1></header>
	
	<p>Hi <c:out value="${creator.name}"></c:out></p> 
	<a href="/logout">Logout</a>
	//<a href="<c:url value="/logout" />">Logout</a>
	<h1><c:out value="${post.content}"></c:out></h1>
	<form:form action="/allposts/${post.id}/edit" method="POST" modelAttribute="post">
		<div>
			<form:errors path="post.*"></form:errors>
		</div>
		<div>
			<p>Content:</p>
<%-- 			<form:label path="content"></form:label> --%>
			
<%-- 			<form:errors path="content"></form:errors> --%>
			<form:input path="content"></form:input>
			<form:input path="creator.id" type="hidden"></form:input>
			<form:input path="id" type="hidden"></form:input>
		</div>
		<div>
			<button type="submit">Update</button>
		</div>	
	</form:form>
    <form action="/allposts/${post.id}" method="post">
 		<input type="hidden" name="_method" value="delete">
 		<input type="submit" value="Delete">
	</form>
</body>
</html>

<%-- <form:form action="/edit-team/${team.id}" method="POST" modelAttribute="team">
		<div>
			<form:errors path="team.*"></form:errors>
		</div>
		<div>
			<form:label path="name"></form:label>
			<form:input path="name"></form:input>
		</div>
		<div>
			<button type="submit">Edit team!</button>
		</div>
	
	</form:form> --%>