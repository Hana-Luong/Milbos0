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
	
	<p>Welcome <c:out value="${creator.name}"></c:out></p> 

	<a href="/logout">Logout</a>
	<form:form action="/allposts/new" method="POST" modelAttribute="post">
		<div>
			<p>Content:</p>
			<form:label path="content"></form:label>
			<form:errors path="content"></form:errors>
			<form:input path="content"></form:input>
			<!-- "content" is one field (or column) in the database -->
		</div>
		<div>
			<form:button type="submit">Create</form:button>
		</div>	
	</form:form>
</body>
</html>

<!-- 
  
<h1>New Book</h1>
<form:form action="/books" method="post" modelAttribute="book"> 

/*
	 * a new use of @ModelAttribute: not accessing data sent with the request, but
	 * rather instantiating a new Book type and binding to our view model. Since
	 * there is no Book being passed, an empty book will be instantiated and bound
	 * to our view model. The binding to our model will allow us to create a form
	 * that is also bound to the Book type, which will allow us to validate the
	 * information against our Book type!
 */
    <p>
        <form:label path="title">Title</form:label>
        <form:errors path="title"/>
        <form:input path="title"/>
    </p>
    <p>
        <form:label path="description">Description</form:label>
        <form:errors path="description"/>
        <form:textarea path="description"/>
    </p>
    <p>
        <form:label path="language">Language</form:label>
        <form:errors path="language"/>
        <form:input path="language"/>
    </p>
    <p>
        <form:label path="numberOfPages">Pages</form:label>
        <form:errors path="numberOfPages"/>     
        <form:input type="number" path="numberOfPages"/>
    </p>    
    <input type="submit" value="Submit"/>
</form:form>    

 -->