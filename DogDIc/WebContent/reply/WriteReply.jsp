<%@page import="fivetrue.restapi.drip.ReplyApiHandler"%>
<%@ page language="java" contentType="json/application; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
 <%
	ReplyApiHandler handler = new ReplyApiHandler(getServletContext(), request, response);
 	handler.putReply();
 %>