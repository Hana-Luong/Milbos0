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


 	<h1><c:out value="${post.content}"></c:out></h1>
	<p> Created by: <c:out value="${post.creator.name}"></c:out></p> 

	<h4>List of People who liked your post in the table below</h4>
	
	<table>
		<tr>
			<th>Name</th>
		</tr>
		<tr>
			<td></td>
		</tr>
		
	</table>
	<p><a href="/allposts/${post.id}/edit">Edit</a></p>
</body>
</html>
 