<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="com.google.appengine.api.blobstore 
 .BlobstoreServiceFactory" %> 
 <%@page import="com.google.appengine.api.blobstore 
 .BlobstoreService" %> 
 <%BlobstoreService blobstoreService = 
 BlobstoreServiceFactory.getBlobstoreService(); 
 %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传</title>
</head>
<body>
<form action=fileUpload method="post" enctype="multipart/form-data"> 
 <input type="file" name="myfile"> 
 <input type="submit" value="Submit">
 </form> 
</body>
</html>