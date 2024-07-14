<%--
  Created by IntelliJ IDEA.
  User: T14 Gen 3
  Date: 2024/7/14
  Time: 下午 08:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.news.model.*" %>

<%
    NewsService newsSvc = new NewsService();
    List<NewsVO> list = newsSvc.getAllNews();
    pageContext.setAttribute("list", list);
%>
<html>
<head>
    <title>所有消息資料 - listAllNews.jsp</title>

    <style>
        table#table-1 {
            background-color: #CCCCFF;
            border: 2px solid black;
            text-align: center;
        }

        table#table-1 h4 {
            color: red;
            display: block;
            margin-bottom: 1px;
        }

        h4 {
            color: blue;
            display: inline;
        }
    </style>

    <style>
        table {
            width: 800px;
            background-color: white;
            margin-top: 5px;
            margin-bottom: 5px;
        }

        table, th, td {
            border: 1px solid #CCCCFF;
        }

        th, td {
            padding: 5px;
            text-align: center;
        }
    </style>

</head>
<body bgcolor="#ffe4c4">

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
    <tr><td>
        <h3>所有消息資料 - listAllNews.jsp</h3>
        <h4><a href="select_page.jsp"><img src="images/ananBack1.png" width="100" height="32" border="0">回首頁</a></h4>
    </td></tr>
</table>

<table>
    <tr>
        <th>消息編號</th>
        <th>管理員編號</th>
        <th>標題</th>
        <th>內容</th>
        <th>狀態</th>
        <th>發布時間</th>
        <th>修改</th>
        <th>刪除</th>
    </tr>
    <%@ include file="page1.file" %>
    <c:forEach var="newsVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

        <tr>
            <td>${newsVO.newsID}</td>
            <td>${newsVO.administratorID}</td>
            <td>${newsVO.newsTitle}</td>
            <td>${newsVO.newsContent}</td>
            <td>${newsVO.newsStatus}</td>
            <td>${newsVO.newsCreateTime}</td>
            <td>
                <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/news.do" style="margin-bottom: 0px;">
                    <input type="submit" value="修改">
                    <input type="hidden" name="newsID"  value="${newsVO.newsID}">
                    <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
            </td>
            <td>
                <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/news.do" style="margin-bottom: 0px;">
                    <input type="submit" value="刪除">
                    <input type="hidden" name="newsID"  value="${newsVO.newsID}">
                    <input type="hidden" name="action" value="delete"></FORM>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>
