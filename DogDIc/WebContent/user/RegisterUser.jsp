<%@page import="fivetrue.restapi.user.UserApiHandler"%>
<%@ page language="java" contentType="json/application; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
 <%
	UserApiHandler handler = new UserApiHandler(getServletContext(), request, response);
 	handler.registerUser();
 %>