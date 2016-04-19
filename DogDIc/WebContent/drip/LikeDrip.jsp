<%@page import="fivetrue.restapi.drip.DripApiHandler"%>
<%@ page language="java" contentType="json/application; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
 <%
	DripApiHandler handler = new DripApiHandler(getServletContext(), request, response);
 	handler.likeDrip();
 %>