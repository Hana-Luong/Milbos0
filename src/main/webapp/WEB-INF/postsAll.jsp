
<!-- Formatting (dates) --> 
<!-- taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->
<!-- for rendering errors on PUT routes -->
<!-- <%@ page isErrorPage="true" %> -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>	All Posts </title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	
</head>
<body>

	<header><h1>MILBO'S PAWS</h1></header>


	<h2>All Posts</h2>
	<table>	
		<thead>
		   	<tr>
  				<th>Post</th>
  				<th>Created by</th>
  				<th>Number of Likes</th>
  				<th>Action</th>
		    </tr>	
		</thead>
		<tbody>
			<c:forEach items="${allPosts}" var="post"> 	
			
			<!-- Look for the following in the controller file:
				model.addAttribute("postsAll", posts); -->
			<!--  var = "onePost " make on the fly -->
			
			
				<tr>			
					<td><a href="/allposts/${post.id}"><c:out value="${post.content}"></c:out></a></td>
					<td><c:out value="${post.creator.name}"></c:out></td>	
					<!-- Review creator.name -->		
					<td><a href="/allposts/${post.id}/like">Like</a></td>						 
					<td>Reply</td>
				</tr>
			</c:forEach>			
         </tbody>
	</table>
	<p><a href="/allposts/new">Create a Post</a></p>
</body>
</html>

<%-- <c:forEach var="banana" items="${people}">
    <c:out value="${banana.name}"/>
</c:forEach> --%>
