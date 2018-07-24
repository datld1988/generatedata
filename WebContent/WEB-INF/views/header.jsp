<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<head>
<script src="<c:url value="/resources/js/onLoad.js" />"></script>
<link href="<c:url value="/resources/css/custom.css" />" rel="stylesheet">
<%-- 
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/lib/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/lib/bootstrap.min.js" />"></script>
 --%>

<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>


</head>
<title>GENERATE DATA</title>
</head>
<body>
	<div class="jumbotron text-center" id="text-header">
		<h1>GENERATE DATA TOOL</h1>
		<c:if test="${errCode == 1}">
			<div class="alert alert-danger" id="home-alert">
				<strong>${errInfo}</strong> 
			</div>
		</c:if>
	</div>

	<div id="text-container">
		<br>