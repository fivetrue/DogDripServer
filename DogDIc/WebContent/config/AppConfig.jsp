<%@page import="fivetrue.restapi.config.ConfigApiHandler"%>
<%@ page language="java" contentType="json/application; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
 <%
	ConfigApiHandler handler = new ConfigApiHandler(getServletContext(), request, response);
 	handler.getAppConfig();
 %>