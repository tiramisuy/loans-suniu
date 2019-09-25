<%@ page language="java" isErrorPage="true" import="java.util.*" pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%
response.setStatus(200);
String msg = exception.getMessage();
exception.printStackTrace();
%>
{"code": 400,"msg": "<%=msg %>"}